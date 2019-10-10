package application;


import java.time.LocalDate;

public abstract class Item {

    protected String title;
    protected double value;
    protected double maxScore;
    protected double scoreEarned = -1;
    protected String thoughtsFeelings;
    protected LocalDate date;

    public String getTitle() {
        return title;
    }

    public double getValue() {
        return value;
    }

    public double getMaxScore() {
        return maxScore;
    }

    public double getScoreEarned() {
        return scoreEarned;
    }

    /*
    public String getThoughtsFeelings() {
        return thoughtsFeelings;
    }
     */

    public LocalDate getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setMaxScore(double maxScore) {
        this.maxScore = maxScore;
    }

    public void setScoreEarned(double scoreEarned) {
        this.scoreEarned = scoreEarned;
    }

    public void setThoughtsFeelings(String thoughtsFeelings) {
        this.thoughtsFeelings = thoughtsFeelings;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean graded() {
        return scoreEarned >= 0;
    }

    abstract void setReminder(int days);


}
