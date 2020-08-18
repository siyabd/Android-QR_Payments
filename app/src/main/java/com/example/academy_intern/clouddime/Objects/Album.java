package com.example.academy_intern.clouddime.Objects;

/**
 * Created by Lincoln on 18/05/16.
 */
public class Album {
    private String id;
    private String name;
    private String date;
    private String location;
    private String month;
    private String points;
    private String eventCreator;
    public double eventScore;

    public Album() {
    }

    public Album(String name, int thumbnail,String date,String location,String month,String points,String id) {

        this.location=location;
        this.name = name;
        this.date = date;
        this.month = month;
        this.points =points;
        this.eventCreator=eventCreator;
        this.eventScore=eventScore;
        this.id=id;
    }


    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getName() { return this.name; }
    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getEventCreator() {
        return eventCreator;
    }

    public void setEventCreator(String eventCreator) {
        this.eventCreator = eventCreator;
    }

    public double getEventScore() {
        return eventScore;
    }

    public void setEventScore(double eventScore) {
        this.eventScore = eventScore;
    }
}
