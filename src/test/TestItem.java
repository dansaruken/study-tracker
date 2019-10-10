
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
        assertEquals("assignmentOne", assignment.getTitle());
        assertEquals(20, midtermItem.getValue());
        assertEquals(50, midtermItem.getMaxScore());
        assertEquals(25, midtermItem.getScoreEarned());
        assertEquals(LocalDate.now(), midtermItem.getDate());
        assertEquals(6, assignment.getValue());
        assertEquals(10, assignment.getMaxScore());
        assertEquals(8, assignment.getScoreEarned());
        assertEquals(LocalDate.now(), assignment.getDate());
    }

    @Test
    void testSetters() {
        midtermItem.setTitle("Test Two");
        midtermItem.setMaxScore(99);
        midtermItem.setScoreEarned(69);
        midtermItem.setValue(45);

        LocalDate date = LocalDate.of(2019,10,9);
        midtermItem.setDate(date);
        assertEquals(date, midtermItem.getDate());

        assertEquals("Test Two", midtermItem.getTitle());
        assertEquals(45, midtermItem.getValue());
        assertEquals(99, midtermItem.getMaxScore());
        assertEquals(69, midtermItem.getScoreEarned());
    }

}
