package application;

import java.util.ArrayList;

public class User {
    private static String name;

    private ArrayList<Course> courseList;

    //EFFECTS: Creates new User instance
    public User(String name) {
        User.name = name;
        courseList = new ArrayList<Course>();
    }

    //EFFECTS: Returns username
    public String getName() {
        return name;
    }

    //EFFECTS: Returns clone of course list.
    public ArrayList<Course> getCourseList() {
        return (ArrayList<Course>) courseList.clone();
    }

    public void setCourseList(ArrayList<Course> courses) {
        courseList = courses;
    }

    //MODIFIES: this.
    //EFFECTS: Adds course to user's courselist
    public void addCourse(Course course) {
        courseList.add(course);
    }



}
