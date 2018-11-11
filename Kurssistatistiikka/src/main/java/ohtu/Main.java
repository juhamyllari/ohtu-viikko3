package ohtu;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.Arrays;
import org.apache.http.client.fluent.Request;

public class Main {
    
    public static void main(String[] args) throws IOException {
        // ÄLÄ laita githubiin omaa opiskelijanumeroasi
        String studentNr = "012345678";
        if (args.length > 0) {
            studentNr = args[0];
        }
        
        String url = "https://studies.cs.helsinki.fi/courses/students/" + studentNr + "/submissions";
        
        String bodyText = Request.Get(url).execute().returnContent().asString();
        
        Gson mapper = new Gson();
        Submission[] subs = mapper.fromJson(bodyText, Submission[].class);
        
        printStudentData(subs, studentNr);
    }

    private static void printStudentData(Submission[] subs, String studentNr) {
        int totalHours = Arrays
                .stream(subs)
                .mapToInt(sub -> sub.getHours())
                .sum();
        
        int totalExercises = Arrays
                .stream(subs)
                .mapToInt(sub -> sub.getExercises().size())
                .sum();
        
        System.out.println("Student " + studentNr + ": ");
        Arrays.stream(subs).forEach(sub -> System.out.println(sub));
        
        System.out.println("Total: "
                + totalExercises
                + " exercises completed in "
                + totalHours + " hours.");
    }
}
