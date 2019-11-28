package ui;

import application.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;


import static java.lang.System.exit;
import static javax.swing.JOptionPane.NO_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;

public class Adjuster {

    static boolean saved = true;

    /**
     * Question: Does the main method have to follow 20-line maximum requirement?
     * ANSWER: YES, NO EXCEPTIONS
     * @param args args
     */
    public static void main(String[] args) {

        try {
            JOptionPane.showMessageDialog(frameSetUp(), funFact(), "Today's Hot Tip",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }



        //User user = setUpUser();

        //user.setCourseList(addHoursAndGrades(user.getCourseList()));

        //saveUserData(user);

        JFrame frame = frameSetUp();

        frame.add(new UIPanel());
        frame.setVisible(true);

        //exit(0);
    }

    private static void saveUserData(User user) {
        PrintWriter writer = null;
        //final String USER_FILE = ".data\\userInfo.txt";

        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

            File selectedFile = chooser.getSelectedFile();
            try {
                writer = new PrintWriter(selectedFile,"UTF-8");
                writer.println(user.getName());
                for (int i = 0; i < user.getCourseList().size(); i++) {
                    writer.println(user.getCourseList().get(i).toString());
                }
            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                e.printStackTrace();
            } finally {
                assert writer != null;
                writer.close();
            }
        }
    }

    private static User setUpUser() {

        User user = null;

        // http://www.java2s.com/Tutorial/Java/0240__Swing/Modaldialogwithyesnobutton.htm
        JFrame frame = frameSetUp();

        int answer = JOptionPane.showConfirmDialog(frame, "Are you a new user?");
        if (answer == YES_OPTION) {

            user = newUser();
        } else if (answer == NO_OPTION) {

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
        frame.setSize(new Dimension(736,414));
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setTitle("StudyTracker");

        // https://stackoverflow.com/questions/15449022/show-prompt-before-closing-jframe
        // modified, but taken from above
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent we) {
                closeWindow();
            }
        });

        return frame;


    }

    private static void closeWindow() {
        String[] objButtons = {"Yes","No"};
        if (!saved) {
            int promptResult = JOptionPane.showOptionDialog(null,
                    "You seem to have unsaved data. Are you sure you want to exit?", "StudyTracker",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
                    objButtons,objButtons[1]);
            if (promptResult == 0) {
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
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
            String message = "Do you have a graded test or assignment to add to " + course.getCourseName()
                    + " today?(Y/N)";

            int answer = JOptionPane.showConfirmDialog(frame, message);
            if (answer == YES_OPTION) {
                //
                course = adjustGrades(course);
                //
            }
        }
        return courseList;
    }

    // Taken from FileReaderWriter project given
    private static ArrayList<String> splitOnColon(String line) {
        String[] splits = line.split(":");
        return new ArrayList<>(Arrays.asList(splits));
    }

    // https://stackoverflow.com/questions/21737071/java-can-you-get-multiple-variables-using-one-joptionpane-message
    private static LocalDate calendarDialogue() {
        Object[] months = {1,2,3,4,5,6,7,8,9,10,11,12};
        Object[] days = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31};
        JComboBox<Object> monthSelect = new JComboBox<>(months);
        JComboBox<Object> daySelect = new JComboBox<>(days);

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new GridLayout(2,1));
        myPanel.add(new JLabel("Month:"));
        myPanel.add(monthSelect);
        myPanel.add(new JLabel("Day:"));
        myPanel.add(daySelect);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "When is this due?", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            return LocalDate.of(Year.now().getValue(), (int)monthSelect.getSelectedItem(),
                    (int)daySelect.getSelectedItem());
        }
        return LocalDate.now();

    }

    private static Course adjustGrades(Course course) {

        Item item;
        boolean assignment = true;
        Object[] options = {"Assignment", "Test"};
        int n = JOptionPane.showOptionDialog(null, "Test or Assignment?", "StudyTracker",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (n == NO_OPTION) {
            assignment = false;
        }
        item = getItem(course, assignment);

        course.addItem(item);

        return course;


    }

    private static final String ITEM_VAL_MSG = "What was the item worth(1 - 100 out of final grade)?";
    private static final String ITEM_MAX_MSG = "What score constituted a 100 for the item?";
    private static final String ITEM_SCR_MSG = "What score did you earn?";
    private static final String ITEM_NME_MSG = "Give this item a name (like Test 1, Assignment 2, etc";

    private static Item getItem(Course course, boolean assignment) {
        Item item;
        String itemName = JOptionPane.showInputDialog(null, ITEM_NME_MSG);
        LocalDate itemDate = calendarDialogue();
        double val = Double.parseDouble(JOptionPane.showInputDialog(null, ITEM_VAL_MSG));
        double max = Double.parseDouble(JOptionPane.showInputDialog(null, ITEM_MAX_MSG));
        double score = Double.parseDouble(JOptionPane.showInputDialog(null, ITEM_SCR_MSG));
        try {
            course.addGrades(score, max, val);
        } catch (GradeExceededException e) {
            e.printStackTrace();
        }


        if (assignment) {
            item = new Assignment(itemName, val, max, score, itemDate);
        } else {
            item = new Midterm(itemName, val, max, score, itemDate);
        }
        return item;
    }


    // Big thanks to https://api.adviceslip.com/
    // Modified from given 210 resources
    public static String funFact() throws IOException {

        BufferedReader br = null;

        try {
            URL url = new URL("https://api.adviceslip.com/advice");
            br = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;

            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }

            return (new JSONObject(new JSONObject(sb.toString()).get("slip").toString())).get("advice").toString();

        } catch (MalformedURLException | JSONException e) {
            return "Fun fact: The fun fact URL is broken!";
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    //https://stackoverflow.com/questions/5107629/how-to-redirect-console-content-to-a-textarea-in-java
    private static class UIPanel extends JPanel {

        private final JButton saveData = new JButton("Save Data");
        private final JButton addGrades = new JButton("Add Grades");
        private final JButton showData = new JButton("Show Data");
        User user;

        UIPanel() {
            JPanel organizer = new JPanel();
            organizer.setLayout(new BorderLayout());

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(0,1));

            JButton newSemester = new JButton("New Semester");
            newSemester.addActionListener((e) -> {
                user = newUser();
                saveData.setEnabled(true);
                addGrades.setEnabled(true);
                showData.setEnabled(true);
                saved = false;
            });
            buttonPanel.add(newSemester);

            JButton loadData = new JButton("Load Data");
            loadData.addActionListener((e) -> {
                List<String> lines = linesSetup();
                if (lines.size() == 0) {
                    JOptionPane.showMessageDialog(null, "No user found! \nCreating new user.", "StudyTrack",
                            JOptionPane.WARNING_MESSAGE);
                    user = newUser();
                } else {
                    user = loadUserData(lines);
                    System.out.println("User loaded. Hello, " + user.getName());
                    saveData.setEnabled(true);
                    addGrades.setEnabled(true);
                    showData.setEnabled(true);
                }
            });
            buttonPanel.add(loadData);

            //JButton saveData = new JButton("Save Data");
            saveData.addActionListener((e) -> {
                saveUserData(user);
                System.out.println("Data saved!");
                saved = true;
            });
            buttonPanel.add(saveData);

            //JButton addGrades = new JButton("Add Hours and Grades");
            addGrades.addActionListener((e -> {
                addHoursAndGrades(user.getCourseList());
                saved = false;
                System.out.println("You may wish to save your data now.");
            }));
            buttonPanel.add(addGrades);

            //JButton showData = new JButton("Show data");
            showData.addActionListener(e -> {
                for (Course c : user.getCourseList()) {
                    c.printData();
                }
            });
            buttonPanel.add(showData);

            saveData.setEnabled(false);
            addGrades.setEnabled(false);
            showData.setEnabled(false);

            organizer.add(buttonPanel, BorderLayout.WEST);


            JPanel display = new JPanel();
            JTextArea output = new JTextArea(20, 60);
            JScrollPane scrollPane = new JScrollPane(output);
            PrintStream printStream = new PrintStream(new CustomOutputStream(output));
            System.setOut(printStream);
            System.setErr(printStream);
            display.add(scrollPane);

            organizer.add(display, BorderLayout.EAST);

            add(organizer);
        }

    }


}
