package ui;

import application.Course;
import application.User;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.exit;

public class Adjuster {



    //REQUIRES: User inputs have to be logical and error-free
    //MODIFIES: User instance.
    //EFFECTS: Instantiates new user if user does not exist. Since there are no means to save user info yet, must be
    //         run every instance.
    public static User newUser(List<String> lines, PrintWriter writer) {

        String courseName;
        int numCourses;
        int credits;
        User user;

        JFrame frame = new JFrame();
        frame.setSize(300, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        user = new User(JOptionPane.showInputDialog(frame, "Hello! What's your name? "));
        writer.println(user.getName());

        // https://stackoverflow.com/questions/7080205/popup-message-boxes
        JOptionPane.showMessageDialog(frame, "Hello " + user.getName(), "StudyTrack",
                JOptionPane.INFORMATION_MESSAGE);

        // https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
        Object[] possibilities = {1, 2, 3, 4, 5, 6, 7};
        numCourses = (int)JOptionPane.showInputDialog(frame, "How many courses are you taking?",
                "StudyTrack", JOptionPane.QUESTION_MESSAGE, null, possibilities, 3);

        for (int i = 0; i < numCourses; i++) {
            courseName = JOptionPane.showInputDialog(frame, "What's the name of the " + (i + 1) + " course?");
            Object[] possibleCredits = {1, 2, 3, 4, 5, 6};
            credits = (int)JOptionPane.showInputDialog(frame, "How many credits is " + courseName + "?",
                    "StudyTrack", JOptionPane.QUESTION_MESSAGE, null, possibleCredits, 3);
            user.addCourse(new Course(courseName, credits));
            //writer.println(courseName + " " + credits);
        }
        return user;
    }

    /**
     * Question: Does the main method have to follow 20-line maximum requirement?
     * ANSWER: YES, NO EXCEPTIONS
     * @param args args
     */
    public static void main(String[] args) {

        List<String> lines = null;
        PrintWriter writer = null;

        try {
            lines = Files.readAllLines(Paths.get("userInfo.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            writer = new PrintWriter("userInfo.txt","UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        User user = new User("dickhead");

        // http://www.java2s.com/Tutorial/Java/0240__Swing/Modaldialogwithyesnobutton.htm
        JFrame frame = new JFrame();
        frame.setSize(300, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String message = "Are you a new user?";
        int answer = JOptionPane.showConfirmDialog(frame, message);
        if (answer == JOptionPane.YES_OPTION) {
            user = newUser(lines, writer);
        } else if (answer == JOptionPane.NO_OPTION) {
            if (lines.get(0) == null) {
                JOptionPane.showMessageDialog(frame, "No user found! \nCreating new user.", "StudyTrack",
                        JOptionPane.WARNING_MESSAGE);
                user = newUser(lines, writer);
            } else {
                user = new User(lines.get(0));
                ArrayList<Course> courseList = new ArrayList<>();
                for (int i = 1; i < lines.size(); i++) {
                    ArrayList<String> partsOfLine = splitOnBar(lines.get(i));
                    courseList.add(new Course(partsOfLine.get(0), Integer.parseInt(partsOfLine.get(1)),
                            Double.parseDouble(partsOfLine.get(2)), Double.parseDouble(partsOfLine.get(3)),
                            Double.parseDouble(partsOfLine.get(4))));
                }
                user.setCourseList(courseList);
            }
             //TO-DO: change this
        } else {
            exit(1);

        }

        user = modifyCourses(user);

        for (int i = 0; i < user.getCourseList().size(); i++) {
            writer.println(user.getCourseList().get(i).toString());
        }

        exit(0);
    }

    public static User modifyCourses(User user) {

        ArrayList<Course> courseList = user.getCourseList();

        JFrame frame = new JFrame();
        frame.setSize(300, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        for (int i = 0; i < user.getCourseList().size(); i++) {

            double h = Double.parseDouble(JOptionPane.showInputDialog(frame, "How many hours did you study "
                    + courseList.get(i).getCourseName() + " today?"));

            courseList.get(i).addHours(h);
            String message = "Do you have grades to add to " + courseList.get(i).getCourseName() + " today?(Y/N)";

            int answer = JOptionPane.showConfirmDialog(frame, message);
            if (answer == JOptionPane.YES_OPTION) {

                double val = Double.parseDouble(JOptionPane.showInputDialog(frame,
                        "What was the item worth(1 - 100 out of final grade)?"));
                double max = Double.parseDouble(JOptionPane.showInputDialog(frame,
                        "What score constituted a 100 for the item?"));
                double score = Double.parseDouble(JOptionPane.showInputDialog(frame,
                        "What score did you earn?"));
                courseList.get(i).addGrades(score, max, val);


            }

        }
        user.setCourseList(courseList);
        return user;
    }

    // Taken from FileReaderWriter project given
    public static ArrayList<String> splitOnBar(String line) {
        String[] splits = line.split("|");
        return new ArrayList<>(Arrays.asList(splits));
    }


}
