package ohtu;

import java.util.List;

public class CourseWeekStats {
    
    private int students;
    private int hour_total;
    private int exercise_total;
    private List<Integer> hours;
    private List<Integer> exercises;

    @Override
    public String toString() {
        return "students: " + students + ", hour total: " + hour_total;
    }

    public int getStudents() {
        return students;
    }

    public int getHour_total() {
        return hour_total;
    }

    public int getExercise_total() {
        return exercise_total;
    }

    public List<Integer> getHours() {
        return hours;
    }

    public List<Integer> getExercises() {
        return exercises;
    }
    
    
    
}
