package ohtu;

import java.util.List;
import java.util.stream.Collectors;

public class Submission {
    private int week;
    private int hours;
    private List<Integer> exercises;
    private String course;

    public void setWeek(int week) {
        this.week = week;
    }

    public int getWeek() {
        return week;
    }
    
    private String exercisesToString() {
        return exercises
                .stream()
                .map(i -> i.toString())
                .collect(Collectors.joining(", "));
    }

    @Override
    public String toString() {
        return "\t"
                + course
                + ", week " + week
                + ": excercises " + exercisesToString()
                + " completed in "
                + hours + " hours.";
    }
    
}