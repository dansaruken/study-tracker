import application.Course;

import application.Named;
import application.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TestUser {

    private User Dan;

    @BeforeEach
    void setUp() {
        Dan = new User("Daniel");
        Dan.addCourse(new Course("Software Construction", 4));
    }

    @Test
    void testName() {
        assertEquals("Daniel", Dan.getName());
        /* NOTE: Here is a declared Interface */
        Named Danimal = new User("The Danimal");
        assertEquals("The Danimal", Danimal.getName());
    }

    @Test
    void testCourseList() {
        assertEquals("Software Construction", Dan.getCourseList().get(0).getCourseName());
        Dan.addCourse(new Course("Calculus III", 3));
        assertEquals(3, Dan.getCourseList().get(1).getCredits());
        assertEquals(4, Dan.getCourseList().get(0).getCredits());
        assertEquals(2, Dan.getCourseList().size());
    }

    @Test
    void testToString() {

        assertEquals("Daniel\nSoftware Construction:4:0.0:0.0:100.0", Dan.toString());

    }


    static class MidtermTest {

        @BeforeEach
        void setUp() {
        }

        @Test
        void getTitle() {
        }

        @Test
        void getValue() {
        }

        @Test
        void getMaxScore() {
        }

        @Test
        void getScoreEarned() {
        }

        @Test
        void getDate() {
        }

        @Test
        void setTitle() {
        }

        @Test
        void setValue() {
        }

        @Test
        void setMaxScore() {
        }

        @Test
        void setScoreEarned() {
        }

        @Test
        void setThoughtsFeelings() {
        }

        @Test
        void setDate() {
        }

        @Test
        void setReminder() {
        }
    }
}
