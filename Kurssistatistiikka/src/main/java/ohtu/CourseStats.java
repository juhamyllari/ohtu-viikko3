package ohtu;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.apache.http.client.fluent.Request;

public class CourseStats {

    private List<CourseWeekStats> weeks;

    public CourseStats(List<CourseWeekStats> weeks) {
        this.weeks = weeks;
    }

    public CourseStats() {
        this.weeks = new ArrayList<>();
    }

    public int totalSubmissions() {
        return weeks.stream().mapToInt(week -> week.getStudents()).sum();
    }

    public int totalHours() {
        return weeks.stream().mapToInt(week -> week.getHour_total()).sum();
    }
    
    public int totalExercises() {
        return weeks.stream().mapToInt(week -> week.getExercise_total()).sum();
    }

    public static CourseStats getCourseStats(String url) throws IOException {
        String response = Request.Get(url).execute().returnContent().asString();
        JsonObject parsedData = new JsonParser().parse(response).getAsJsonObject();
        List<CourseWeekStats> weeks = new ArrayList<>();
        
        Gson mapper = new Gson();
        
        for (Map.Entry<String, JsonElement> entry : parsedData.entrySet()) {
            CourseWeekStats week = mapper.fromJson(entry.getValue(), CourseWeekStats.class);
            weeks.add(week);
        }
        
        return new CourseStats(weeks);
    }

}
