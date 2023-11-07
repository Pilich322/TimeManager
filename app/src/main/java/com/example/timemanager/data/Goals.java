package com.example.timemanager.data;

import java.time.LocalDate;
import java.util.Date;

public class Goals {
    private int id;
    private double count;
    private int countNow;
    private String name;
    private String description;
    private String unit;
    private LocalDate date;
    private String image;

    public Goals() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getCountNow() {
        return countNow;
    }

    public void setCountNow(int countNow) {
        this.countNow = countNow;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
