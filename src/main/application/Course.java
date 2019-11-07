package application;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

/**
 * CHANGELOG: Shifted "currentGrade" which was supposed to be a percentage (between 0 and 1), to "marksEarned",
 * a private variable that represents number of percentage 'points' as a number between 0 and 100.
 *
 */

public class Course {
    private String courseName;
    private int credits;
    private double hours = 0;
    private double marksEarned = 0;
    private double gradeRemaining = 100;
    private ArrayList<Item> testsAndAssignments = new ArrayList<>();

    //EFFECTS: Creates new instance of a Course, with a default number of credits
    public Course(String title) {
        courseName = title;
        credits = 3;
    }

    //EFFECTS: Creates new instance of a Course
    public Course(String title, int credits) {
        courseName = title;
        this.credits = credits;
    }

    public Course(String title, int credits, double hours, double marksEarned, double gradeRemaining) {
        courseName = title;
        this.credits = credits;
        this.hours = hours;
        this.marksEarned = marksEarned;
        this.gradeRemaining = gradeRemaining;
    }

    //EFFECTS: Returns course name
    public String getCourseName() {
        return courseName;
    }

    //EFFECTS: Returns number of credits
    public int getCredits() {
        return credits;
    }

    //EFFECTS: Returns number of hours studied
    public double getHours() {
        return hours;
    }

    //EFFECTS: Returns current grade
    public double getCurrentGrade() {
        return marksEarned / (100 - gradeRemaining);
    }

    //EFFECTS: Returns percentage of course ungraded
    public double getGradeRemaining() {
        return gradeRemaining;
    }

    //REQUIRES: hours parameter must be positive
    //MODIFIES: this
    //EFFECTS: Adds hours studied into the course
    public void addHours(double hours) {
        this.hours += hours;
        System.out.println(hours + " hours studying " + courseName + " today. "
                + this.hours + " hours total this semester.");
    }

    //REQUIRES: parameters must be positive, maxScore must not be zero. PercentMax must not be greater than
    //          grade remaining.
    //MODIFIES: this
    //EFFECTS: Adds an item's grade to the course
    public void addGrades(double score, double maxScore, double percentMax) throws GradeExceededException {
        marksEarned += score / maxScore * percentMax;
        gradeRemaining -= percentMax;
        if (gradeRemaining < 0) {
            throw new GradeExceededException();
        }
        System.out.println("Current grade is " + marksEarned + " out of "
                + (100 - gradeRemaining) + " or " + marksEarned / (100 - gradeRemaining));
    }

    //EFFECTS: Prints course name and credits
    /*
    public void print() {
        System.out.println(courseName + ", " + credits + " credits. ");
    }
     */

    public String toString() {
        return courseName + ":" + credits  + ":" + hours  + ":" + marksEarned  + ":" + gradeRemaining;
    }

    public void addItem(Item item) {
        if (!testsAndAssignments.contains(item)) {
            testsAndAssignments.add(item);
            item.setCourse(this);
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
        Course course = (Course) o;
        return credits == course.credits
                && Double.compare(course.hours, hours) == 0
                && Double.compare(course.marksEarned, marksEarned) == 0
                && Double.compare(course.gradeRemaining, gradeRemaining) == 0
                && Objects.equals(courseName, course.courseName)
                && Objects.equals(testsAndAssignments, course.testsAndAssignments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseName, credits, hours, marksEarned, gradeRemaining, testsAndAssignments);
    }
}
