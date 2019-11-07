package application;


import java.time.LocalDate;
import java.util.Objects;

public abstract class Item {

    protected String title;
    protected double value;
    protected double maxScore;
    protected double scoreEarned = -1;
    protected String thoughtsFeelings;
    protected LocalDate date;
    protected Course course;

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

    /*
    public boolean graded() {
        return scoreEarned >= 0;
    }
     */

    abstract void setReminder(int days);

    public void setCourse(Course course) {
        if (!(this.course == null) && !this.course.equals(course)) {
            this.course = course;
            course.addItem(this);
        } else {
            System.out.println("");
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return Double.compare(item.value, value) == 0
                && Double.compare(item.maxScore, maxScore) == 0
                && Double.compare(item.scoreEarned, scoreEarned) == 0
                && Objects.equals(title, item.title)
                && Objects.equals(thoughtsFeelings, item.thoughtsFeelings)
                && Objects.equals(date, item.date)
                && Objects.equals(course, item.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, value, maxScore, scoreEarned, thoughtsFeelings, date, course);
    }
}
