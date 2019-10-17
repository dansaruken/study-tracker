package application;

public class GradeExceededException extends RuntimeException {

    GradeExceededException() {
        System.out.println("Max Grade exceeded, total worth of all items cannot exceed 100");
        fillInStackTrace();

    }

}
