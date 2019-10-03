package ui;

import application.Course;
import application.User;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.exit;

public class Adjuster {

    /**
     * Question: Does the main method have to follow 20-line maximum requirement?
     * ANSWER: YES, NO EXCEPTIONS
     * @param args args
     */
    public static void main(String[] args) {

        User user = setUpUser();

        user.setCourseList(addHoursAndGrades(user.getCourseList()));

        saveUserData(user);

        exit(0);
    }

    private static void saveUserData(User user) {
        PrintWriter writer = null;
        //final String USER_FILE = ".data\\userInfo.txt";

        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

            File selectedFile = chooser.getSelectedFile();
            try {
                writer = new PrintWriter(selectedFile,"UTF-8");
            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        assert writer != null;
        writer.println(user.getName());


        for (int i = 0; i < user.getCourseList().size(); i++) {
            writer.println(user.getCourseList().get(i).toString());
        }

        writer.close();
    }

    private static User setUpUser() {

        User user = null;

        // http://www.java2s.com/Tutorial/Java/0240__Swing/Modaldialogwithyesnobutton.htm
        JFrame frame = frameSetUp();

        int answer = JOptionPane.showConfirmDialog(frame, "Are you a new user?");
        if (answer == JOptionPane.YES_OPTION) {

            user = newUser();
        } else if (answer == JOptionPane.NO_OPTION) {

            List<String> lines = linesSetup();
            if (lines.size() == 0) {

                JOptionPane.showMessageDialog(frame, "No user found! \nCreating new user.", "StudyTrack",
                        JOptionPane.WARNING_MESSAGE);
                user = newUser();
            } else {
                user = loadUserData(lines);
            }
        } else {
            exit(1);
        }
        return user;
    }

    private static List<String> linesSetup() {

        List<String> lines = new ArrayList<>();

        JFileChooser chooser = new JFileChooser();

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

            File selectedFile = chooser.getSelectedFile();

            try {
                lines = Files.readAllLines(Paths.get(selectedFile.getAbsolutePath()));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return lines;
    }

    private static JFrame frameSetUp() {
        JFrame frame = new JFrame();
        frame.setSize(300, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return frame;
    }

    //REQUIRES: User inputs have to be logical and error-free
    //MODIFIES: User instance.
    //EFFECTS: Instantiates new user if user does not exist. Since there are no means to save user info yet, must be
    //         run every instance.
    private static User newUser() {

        JFrame frame = frameSetUp();
        User user = new User(JOptionPane.showInputDialog(frame, "Hello! What's your name? "));


        // https://stackoverflow.com/questions/7080205/popup-message-boxes
        JOptionPane.showMessageDialog(frame, "Hello " + user.getName(), "StudyTrack",
                JOptionPane.INFORMATION_MESSAGE);

        // https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
        Object[] possibilities = {1, 2, 3, 4, 5, 6, 7};
        int numCourses = (int)JOptionPane.showInputDialog(frame, "How many courses are you taking?",
                "StudyTrack", JOptionPane.QUESTION_MESSAGE, null, possibilities, 3);

        for (int i = 0; i < numCourses; i++) {
            String courseName = JOptionPane.showInputDialog(frame, "What's the name of course #" + (i + 1) + "?");
            Object[] possibleCredits = {1, 2, 3, 4, 5, 6};
            int credits = (int)JOptionPane.showInputDialog(frame, "How many credits is " + courseName + "?",
                    "StudyTrack", JOptionPane.QUESTION_MESSAGE, null, possibleCredits, 3);
            user.addCourse(new Course(courseName, credits));
        }
        return user;
    }

    private static User loadUserData(List<String> lines) {
        User user = new User(lines.get(0));
        ArrayList<Course> courseList = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            ArrayList<String> partsOfLine = splitOnColon(lines.get(i));
            courseList.add(new Course(partsOfLine.get(0), Integer.parseInt(partsOfLine.get(1)),
                    Double.parseDouble(partsOfLine.get(2)), Double.parseDouble(partsOfLine.get(3)),
                    Double.parseDouble(partsOfLine.get(4))));
        }
        user.setCourseList(courseList);
        return user;
    }

    private static ArrayList<Course> addHoursAndGrades(ArrayList<Course> courseList) {
        JFrame frame = frameSetUp();

        for (Course course : courseList) {

            double h = Double.parseDouble(JOptionPane.showInputDialog(frame, "How many hours did you study "
                    + course.getCourseName() + " today?"));

            course.addHours(h);
            String message = "Do you have grades to add to " + course.getCourseName() + " today?(Y/N)";

            int answer = JOptionPane.showConfirmDialog(frame, message);
            if (answer == JOptionPane.YES_OPTION) {

                double val = Double.parseDouble(JOptionPane.showInputDialog(frame,
                        "What was the item worth(1 - 100 out of final grade)?"));
                double max = Double.parseDouble(JOptionPane.showInputDialog(frame,
                        "What score constituted a 100 for the item?"));
                double score = Double.parseDouble(JOptionPane.showInputDialog(frame,
                        "What score did you earn?"));
                course.addGrades(score, max, val);

            }
        }
        return courseList;
    }

    // Taken from FileReaderWriter project given
    private static ArrayList<String> splitOnColon(String line) {
        String[] splits = line.split(":");
        return new ArrayList<>(Arrays.asList(splits));
    }

}
