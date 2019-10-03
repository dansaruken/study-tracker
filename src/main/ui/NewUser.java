package ui;

import application.Course;
import application.User;

import javax.swing.*;
import java.util.Scanner;

public class NewUser {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("Pizza Time");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(new PizzaPanel());
        frame.pack();
        frame.setVisible(true);

    }

}
