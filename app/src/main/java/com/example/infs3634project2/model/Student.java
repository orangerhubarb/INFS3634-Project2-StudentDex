package com.example.infs3634project2.model;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Davian on 6/10/16.
 */
public class Student implements Serializable {
    private int tutorialID;
    private String firstName;
    private String lastName;
    private int studentID;
    private String zID;
    private int yearOfDegree;
    private String degree;
    private String githubUsername;
    private String strengths;
    private String weaknesses;
    private Bitmap studentPicture;
    private String phoneNumber;
    private String email;

    private List<String> todoList;

    public Student() {

    }

    public Student(String firstName, String lastName, int tutorialID, String zID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.tutorialID = tutorialID;
        this.zID = zID;
    }

    public Student(String firstName, String lastName, int tutorialID, String zID, int yearOfDegree, String degree, String githubUsername, String strengths, String weaknesses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.tutorialID = tutorialID;
        this.zID = zID;
        this.yearOfDegree = yearOfDegree;
        this.degree = degree;
        this.githubUsername = githubUsername;
        this.strengths = strengths;
        this.weaknesses = weaknesses;
    }

    public List<String> getTodoList() {
        return todoList;
    }

    public void setTodoList(List<String> todoList) {
        this.todoList = todoList;
    }

    public String getGithubUsername() {
        return githubUsername;
    }

    public void setGithubUsername(String githubUsername) {
        this.githubUsername = githubUsername;
    }

    public String getzID() {
        return zID;
    }

    public void setzID(String zID) {
        this.zID = zID;
    }

    public int getYearOfDegree() {
        return yearOfDegree;
    }

    public void setYearOfDegree(int yearOfDegree) {
        this.yearOfDegree = yearOfDegree;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getStrengths() {
        return strengths;
    }

    public void setStrengths(String strengths) {
        this.strengths = strengths;
    }

    public String getWeaknesses() {
        return weaknesses;
    }

    public void setWeaknesses(String weaknesses) {
        this.weaknesses = weaknesses;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getTutorialID() { return tutorialID; };

    public void setTutorialID(int tutorialID) { this.tutorialID = tutorialID; }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Bitmap getStudentPicture() {
        return studentPicture;
    }

    public void setStudentPicture(Bitmap studentPicture) {
        this.studentPicture = studentPicture;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
