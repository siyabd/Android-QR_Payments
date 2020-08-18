package com.example.academy_intern.clouddime.classes;

import java.sql.Timestamp;


/*
    This fragment contains event object that set and get the attributes
 */
public class Events {

    //Declarations
    private int eventID;
    private String name;
    private String location;
    private Timestamp date;
    private double duration;
    private double points;
    private double branch;
    private int userID;
    private int mImageDrawable;

    public Events(String ux_design_tips, int cover) {
    }



    //public Events(int eventID, String name, String location, double duration, double points, double branch, int userID,int mImageDrawable) {
    public Events( String name, double points,Timestamp date) {
       // this.eventID = eventID;
        this.name = name;
//        this.location = location;
            this.date = date;
//        this.duration = duration;
        this.points = points;
      //  this.branch = branch;
       // this.userID = userID;
        //this.mImageDrawable = mImageDrawable;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public double getBranch() {
        return branch;
    }

    public void setBranch(double branch) {
        this.branch = branch;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getmImageDrawable() {
        return mImageDrawable;
    }

    public void setmImageDrawable(int mImageDrawable) {
        this.mImageDrawable = mImageDrawable;
    }
}
