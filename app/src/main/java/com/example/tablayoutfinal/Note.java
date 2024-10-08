package com.example.tablayoutfinal;

import com.example.tablayoutfinal.FRAGMENTS.ClassesFragment;

import java.util.List;

public class Note extends ClassesFragment {


    String className;
    String location;
    String professor;
    String daysOfWeek;

    public Note(String className, String location, String professor, String daysOfWeek) {
        this.className = className;
        this.location = location;
        this.professor = professor;
        this.daysOfWeek = daysOfWeek;
    }

    // Getter methods for accessing the information
    public String getClassName() {
        return className;
    }

    public String getLocation() {
        return location;
    }

    public String getProfessor() {
        return professor;
    }

    public String getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public void setDaysOfWeek(String daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }
}

