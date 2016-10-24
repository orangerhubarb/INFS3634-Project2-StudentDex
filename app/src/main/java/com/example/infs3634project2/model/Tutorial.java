package com.example.infs3634project2.model;

import java.util.ArrayList;

/**
 * Created by Davian on 6/10/16.
 */
public class Tutorial {

    //Perhaps uniquely identify by Day, and time? Because what happens if you put two of the same name in
    private String name;
    private ArrayList<Student> students;
    private int tutorialID;
    private String time;
    private String day;
    public int studentCount = 0;

    public Tutorial(String name, String day, String time) {
        this.name = name;
        this.day = day;
        this.time = time;
    }

    public Tutorial() {

    }

    public int getTutorialID() {
        return tutorialID;
    }

    public void setTutorialID(int tutorialID) {
        this.tutorialID = tutorialID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }
}
