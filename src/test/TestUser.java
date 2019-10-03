import application.Course;

import application.User;
import java.util.Scanner;
import org.junit.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUser {

    User Dan;
    Scanner in;

    @Before
    public void setUp() {
        Dan = new User("Daniel");
        Dan.addCourse(new Course("Software Construction", 4));
        in = new Scanner(System.in);
    }

    @Test
    public void testName() {
        assertEquals("Daniel", Dan.getName());
    }

    @Test
    public void testCourseList() {
        assertEquals("Software Construction", Dan.getCourseList().get(0).getCourseName());
        Dan.addCourse(new Course("Calculus III", 3));
        assertEquals(3, Dan.getCourseList().get(1).getCredits());
        assertEquals(4, Dan.getCourseList().get(0).getCredits());
        assertEquals(2, Dan.getCourseList().size());
    }




}
