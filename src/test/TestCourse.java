import application.Course;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCourse {

    @Test
    public void testBasicConstructor() {
        Course course = new Course("Linguistics");
        assertEquals(3, course.getCredits());
        assertEquals("Linguistics", course.getCourseName());
    }

    @Test
    public void testConstructor() {
        Course course = new Course("Models of Computation", 4);
        assertEquals(4, course.getCredits());
        assertEquals("Models of Computation", course.getCourseName());
    }

    @Test
    public void testGradeMethods() {
        Course course = new Course("Models of Computation", 4);
        course.addGrades(5, 5, 1);
        assertEquals(1, course.getCurrentGrade());
        assertEquals(99, course.getGradeRemaining());
        course.addGrades(0, 5, 9);
        assertEquals(0.1, course.getCurrentGrade());
        assertEquals(90, course.getGradeRemaining());

        Course course2 = new Course("Software Construction", 4);
        assertEquals(100, course2.getGradeRemaining());
        course2.addGrades(90, 100, 50);
        assertEquals(0.9, course2.getCurrentGrade());
        assertEquals(50, course2.getGradeRemaining());

    }

    @Test
    public void testHourMethods() {
        Course course = new Course("Models of Computation", 4);
        course.addHours(6);
        assertEquals(6, course.getHours());
        course.addHours(13);
        assertEquals(19, course.getHours());
    }

}
