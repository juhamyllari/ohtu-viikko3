package ohtu;

import java.util.List;

public class Course {
    
    private String _id;
    private String name;
    private String url;
    private int week;
    private boolean enabled;
    private String term;
    private int year;
    private String __v;
    private String fullName;
    private int[] exercises;

    public String getId() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public int getWeek() {
        return week;
    }

    public String getTerm() {
        return term;
    }

    public int getYear() {
        return year;
    }

    public String getFullname() {
        return fullName;
    }

    public int[] getExercises() {
        return exercises;
    }
    
    public int exercisesUpToNow() {
        int sum = 0;
        for (int i = 0; i <= week; i++) {
            sum += exercises[i];
        }
        return sum;
    }

    @Override
    public String toString() {
        return fullName + " " + term + " " + year;
    }
    
    
    
}
