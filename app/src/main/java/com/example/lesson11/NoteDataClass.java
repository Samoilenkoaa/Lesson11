package com.example.lesson11;

import java.io.Serializable;

public class NoteDataClass implements Serializable {

    String name;
    String description;
    String date;
    String id;

    public NoteDataClass() {

    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setId(String id) {
        this.id = id;
    }
}
