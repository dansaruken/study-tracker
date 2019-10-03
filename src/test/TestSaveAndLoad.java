import application.Course;
import application.User;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class TestSaveAndLoad {

    private User Dan;


    private static void saveUserData(User user) {
        PrintWriter writer = null;

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

    // Taken from FileReaderWriter project given
    private static ArrayList<String> splitOnColon(String line) {
        String[] splits = line.split(":");
        return new ArrayList<>(Arrays.asList(splits));
    }

    @Test
    void testSave() {
        Dan = new User("Daniel");
        Course course = new Course("Models of Computation", 4);
        course.addGrades(5, 5, 1);
        Course course2 = new Course("Software Construction", 4);
        course2.addGrades(90, 100, 50);
        Dan.addCourse(course);
        Dan.addCourse(course2);

        saveUserData(Dan);

        File f = new File("C:\\Users\\dansaruken\\Documents\\CPSC210\\project_g8w2b\\data\\testUserInfo.txt");
        assertTrue(f.exists());
        assertFalse(f.isDirectory());

    }

    @Test
    void testLoad() {
        File f = new File("C:\\Users\\dansaruken\\Documents\\CPSC210\\project_g8w2b\\data\\testUserInfo.txt");
        assertTrue(f.exists());
        assertFalse(f.isDirectory());

        List<String> lines = linesSetup();
        Dan = loadUserData(lines);

        assertEquals("Daniel", lines.get(0));
        assertEquals("Models of Computation:4:0.0:1.0:99.0", lines.get(1));
        assertEquals("Software Construction:4:0.0:45.0:50.0", lines.get(2));
    }

}
