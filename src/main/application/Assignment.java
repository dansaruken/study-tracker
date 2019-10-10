package application;

import java.time.LocalDate;

public class Assignment extends Item {

    public Assignment(String name, double value, double maxScore, double scoreEarned, LocalDate date) {
        title = name;
        this.value = value;
        this.maxScore = maxScore;
        this.scoreEarned = scoreEarned;
        this.date = date;
    }

    @Override
    public void setReminder(int days) {
        System.out.println("Your assignment is due in " + days + " days. Are you ready for it?");
        System.out.println("TODO: Add dialogue to set reminders, add ui checks on startup to check for reminders");
        System.out.println("TODO: Assignment Specific: Progress checklist, % finished, etc.");

    }
}
