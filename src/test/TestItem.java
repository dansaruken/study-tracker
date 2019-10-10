
import application.Assignment;
import application.Item;

import application.Midterm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestItem {

    Item midtermItem;
    Item assignment;


    @BeforeEach
    void setUp() {
        midtermItem = new Midterm("testOne", 20, 50, 25, LocalDate.now());
        assignment = new Assignment("assignmentOne", 6, 10, 8, LocalDate.now());
    }

    @Test
    void testItems() {
        assertEquals("testOne", midtermItem.getTitle());

    }

}
