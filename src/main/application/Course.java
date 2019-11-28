package application;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

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
    private Set<Item> testsAndAssignments = new HashSet<>();

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
        System.out.print(hours + " hours studying " + courseName + " today. ");
        //printData();
    }

    //EFFECTS: Prints course data; how many hours have been input, and what the current grade is.
    public void printData() {
        System.out.println(courseName + ": " + hours + " hours studied this semester.");
        if (gradeRemaining < 100) {
            System.out.print("Current grade is " + round(marksEarned, 2) + " out of " + (100 - gradeRemaining));
            System.out.print(" or " + round(100 * (marksEarned / (100.00 - gradeRemaining)), 2));
            System.out.println("%");
        } else {
            System.out.println("No grades entered for this course yet.");
        }

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
        printData();
    }

    //EFFECTS: Prints course name and credits
    /*
    public void print() {
        System.out.println(courseName + ", " + credits + " credits. ");
    }
     */

    //EFFECTS: Converts the credits, hours, and current grade of the course into a String formatted in such a way that
    //         the adjuster class can save and load it.
    //         As of this version of the program, Exams and Assignments cannot yet be saved.
    public String toString() {
        return courseName + ":" + credits  + ":" + hours  + ":" + marksEarned  + ":" + gradeRemaining;
    }

    //MODIFIES: this
    //EFFECTS: adds Item to Course, and adds Course to Item.
    public void addItem(Item item) {
        if (!testsAndAssignments.contains(item)) {
            testsAndAssignments.add(item);
            item.setCourse(this);
        }
    }

    //EFFECTS: returns true if 'this' and 'o' are both Courses with the same name, number of credits, and marks earned.
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
                && Objects.equals(courseName, course.courseName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseName, credits, hours, marksEarned, gradeRemaining);
    }

    // https://www.baeldung.com/java-round-decimal-number
    // #4 Rounding Doubles with BigDecimal
    private static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
