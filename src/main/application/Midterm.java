package application;

import java.time.LocalDate;
import java.util.ArrayList;

public class Midterm extends Item {


    private ArrayList<Topic> topicList;

    public Midterm(String name, double value, double maxScore, double scoreEarned, LocalDate date) {
        title = name;
        this.value = value;
        this.maxScore = maxScore;
        this.scoreEarned = scoreEarned;
        this.date = date;
    }

    public void setReminder(int days) {


    }


}
