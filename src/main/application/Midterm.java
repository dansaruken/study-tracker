package application;

import java.time.LocalDate;
import java.util.ArrayList;

public class Midterm extends Item {


    //private ArrayList<Topic> topicList;

    public Midterm(String name, double value, double maxScore, double scoreEarned, LocalDate date) {
        title = name;
        this.value = value;
        this.maxScore = maxScore;
        this.scoreEarned = scoreEarned;
        this.date = date;
    }

//    public void setReminder(int days) {
//        System.out.println("You have a test in " + days + " days. Are you ready for it?");
//        System.out.println("TODO: Add dialogue to set reminders, add ui checks on startup to check for reminders");
//        System.out.println("TODO: Midterm Specific: Go through list of topics, see preparedness for each topic");
//
//    }


}
