package ohtu;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.http.client.fluent.Request;

public class Main {

    private static final String COURSES_URL = "https://studies.cs.helsinki.fi/courses/courseinfo";

    public static void main(String[] args) throws IOException {
        // ÄLÄ laita githubiin omaa opiskelijanumeroasi
        String studentNr = "012345678";
        if (args.length > 0) {
            studentNr = args[0];
        }

        printStudentSubmissions(studentNr);

    }

    private static Submission[] getStudentSubmissions(String studentId) throws IOException, JsonSyntaxException {
        String url = "https://studies.cs.helsinki.fi/courses/students/" + studentId + "/submissions";
        String bodyText = Request.Get(url).execute().returnContent().asString();
        Gson mapper = new Gson();
        Submission[] subs = mapper.fromJson(bodyText, Submission[].class);
        return subs;
    }

    private static Course[] getCourses(String coursesUrl) throws IOException {
        String bodyText = Request.Get(coursesUrl).execute().returnContent().asString();
        Gson mapper = new Gson();
        Course[] courses = mapper.fromJson(bodyText, Course[].class);
        for (Course course : courses) {
            course.fetchCourseStats();
        }
        return courses;
    }

    private static void printStudentSubmissions(String studentId) throws IOException {
        Submission[] subs = getStudentSubmissions(studentId);
        Course[] allCourses = getCourses(COURSES_URL);

        Set<String> studentsCourseNames = Arrays.stream(subs)
                .map(sub -> sub.getCourse())
                .collect(Collectors.toSet());

        List<Course> studentsCourses = studentsCourseNames
                .stream()
                .map(courseName -> getCourseByName(allCourses, courseName))
                .collect(Collectors.toList());

        System.out.println("\nStudent " + studentId + "\n");

        for (Course studentsCourse : studentsCourses) {
            List<Submission> courseSubmissions = Arrays.stream(subs)
                    .filter(sub -> sub.getCourse().equals(studentsCourse.getName()))
                    .collect(Collectors.toList());
            printStudentsCourseSubmissions(courseSubmissions, studentsCourse);
            System.out.println("");
        }

    }

    private static Course getCourseByName(Course[] courses, String name) {
        Course course = Arrays.stream(courses)
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .orElse(null);
        return course;
    }

    private static void printSubmission(Submission sub, Course course) {
        String str = "Week " + sub.getWeek() + ":\n "
                + sub.getExercises().size() + "/" + course.getExercises()[sub.getWeek()]
                + " exercises (" + sub.exercisesToString() + ")"
                + " completed in " + sub.getHours() + " hours";
        System.out.println(str);
    }

    private static void printStudentsCourseSubmissions(List<Submission> subs, Course course) {
        int totalHours = subs.stream().mapToInt(sub -> sub.getHours()).sum();
        int totalExercisesDone = subs.stream().mapToInt(sub -> sub.getExercises().size()).sum();
        int maxExercises = course.exercisesUpToNow();
        CourseStats stats = course.getStats();

        System.out.println(course.toString());
        subs.forEach(sub -> printSubmission(sub, course));
        System.out.println("In total: "
                + totalExercisesDone + "/" + maxExercises + " exercises in "
                + totalHours
                + " hours ");
        
        System.out.println("The course has a total of "
                + stats.totalSubmissions() + " submissions comprising "
                + stats.totalExercises() + " exercises completed in "
                + stats.totalHours() + " hours.");
    }

}
