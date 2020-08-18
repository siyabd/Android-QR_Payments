package com.example.academy_intern.clouddime.Objects;

import java.sql.Timestamp;

public class TransactionalHistory {
    //int id
    private String points;
    private String date;
    private String location;

    public TransactionalHistory(String points, String date, String location) {
        this.points = points;
        this.date = date;
        this.location = location;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
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
}

