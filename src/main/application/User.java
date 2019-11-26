package application;

import observer.UserMonitor;

import java.util.ArrayList;
import java.util.Observable;

public class User extends Observable implements Writeable, Named {
    private static String name;

    private ArrayList<Course> courseList;

    //EFFECTS: Creates new User instance
    public User(String name) {
        User.name = name;
        courseList = new ArrayList<>();
        addObserver(new UserMonitor());
    }

    //EFFECTS: Returns username
    public String getName() {
        return name;
    }

    //EFFECTS: Returns course list.
    public ArrayList<Course> getCourseList() {
        return courseList; //should I give a copy instead? Is this safe?
    }

    public void setCourseList(ArrayList<Course> courses) {
        courseList = courses;
        setChanged();
        notifyObservers(courses.get(courses.size() - 1).getCourseName());
    }

    //MODIFIES: this.
    //EFFECTS: Adds course to user's courseList
    public void addCourse(Course course) {
        courseList.add(course);
        setChanged();
        notifyObservers(course.getCourseName());
    }

    public String toString() {
        StringBuilder s = new StringBuilder(name);
        for (Course c : courseList) {
            s.append("\n");
            s.append(c.toString());
        }
        return s.toString();
    }



}
