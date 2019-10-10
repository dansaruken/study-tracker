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
    void setReminder(int days) {


    }
}
