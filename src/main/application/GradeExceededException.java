package application;

import javax.swing.*;

public class GradeExceededException extends RuntimeException {

    GradeExceededException() {
        System.out.println("Max Grade exceeded, total worth of all items cannot exceed 100");
        fillInStackTrace();

        JOptionPane.showMessageDialog(null,"Total grade cannot exceed 100, item not entered.","ERROR",
                JOptionPane.ERROR_MESSAGE);

    }


}
