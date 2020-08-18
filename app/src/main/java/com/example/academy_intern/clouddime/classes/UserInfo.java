package com.example.academy_intern.clouddime.classes;


/*
    This fragment stores is created to store the json objects from the server to java objects, so the can be accessed through shared preferences
 */
public class UserInfo {

    //Declarations
    private int userId;
    private String name;
    private String surname;
    private double pointsBalance;
    private double pointsSpent;
    public UserInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

    //Parameterised constructor
    public UserInfo(String name, String surname, double pointsBalance, double pointsSpent,int userId) {
        super();
        this.name = name;
        this.surname = surname;
        this.pointsBalance = pointsBalance;
        this.pointsSpent = pointsSpent;
        this.userId = userId;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public double getPointsBalance() {
        return pointsBalance;
    }

    public void setUserId(int userId) { this.userId = userId; }
    public int getUserId(){ return userId; }

    public void setPointsBalance(double pointsBalance) {
        this.pointsBalance = pointsBalance;
    }
    public double getPointsSpent() {
        return pointsSpent;
    }
    public void setPointsSpent(double pointsSpent) {
        this.pointsSpent = pointsSpent;
    }





}
