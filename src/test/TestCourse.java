import application.*;
import org.junit.jupiter.api.Test;
//import org.junit.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TestCourse {

    @Test
    void testBasicConstructor() {
        Course course = new Course("Linguistics");
        assertEquals(3, course.getCredits());
        assertEquals("Linguistics", course.getCourseName());
    }

    @Test
    void testConstructor() {
        Course course = new Course("Models of Computation", 4);
        assertEquals(4, course.getCredits());
        assertEquals("Models of Computation", course.getCourseName());
        Course otherCourse = new Course("Software Construction", 4, 60, 90,0);
        assertEquals(0.9, otherCourse.getCurrentGrade());
        assertEquals(0, otherCourse.getGradeRemaining());
        assertEquals(60,otherCourse.getHours());
        assertEquals(4, otherCourse.getCredits());
        Course cloneCourse = new Course("Software Construction", 4);
        assertFalse(otherCourse.equals(cloneCourse));
        cloneCourse = new Course("Software Construction", 4, 60, 90,0);
        assertTrue(cloneCourse.equals(otherCourse));
    }

    @Test
    void testGradeMethods() {
        Course course = new Course("Models of Computation", 4);
        course.addGrades(5, 5, 1);
        assertEquals(1, course.getCurrentGrade());
        assertEquals(99, course.getGradeRemaining());
        course.addGrades(0, 5, 9);
        assertEquals(0.1, course.getCurrentGrade());
        assertEquals(90, course.getGradeRemaining());

        Item midtermItem = new Midterm("testOne", 20, 50, 25, LocalDate.now());
        course.addItem(midtermItem);
        // Add course getItem

        Course course2 = new Course("Software Construction", 4);
        assertEquals(100, course2.getGradeRemaining());
        course2.addGrades(90, 100, 50);
        assertEquals(0.9, course2.getCurrentGrade());
        assertEquals(50, course2.getGradeRemaining());

        try {
            course2.addGrades(50,50,60);
            fail();
        } catch (GradeExceededException e){
            System.out.println("Exception caught");
        }

    }

    @Test
    void testHourMethods() {
        Course course = new Course("Models of Computation", 4);
        course.addHours(6);
        assertEquals(6, course.getHours());
        course.addHours(13);
        assertEquals(19, course.getHours());
    }

}
