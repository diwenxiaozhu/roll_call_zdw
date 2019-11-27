package com.app.sqlite.db.entity;

public class Grades {
    private int id;   //id
    private int truancy;    //逃课
    private int leave_early;      //早退
    private int late;       //迟到
    private int excuse;     //请假

    public Grades(int id, int truancy, int l_early, int late, int excuse) {
        this.id = id;
        this.truancy = truancy;
        this.leave_early = l_early;
        this.late = late;
        this.excuse = excuse;
    }

    public Grades(int truancy, int early, int late, int excuse) {
        this.truancy = truancy;
        this.leave_early = early;
        this.late = late;
        this.excuse = excuse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTruancy() {
        return truancy;
    }

    public void setTruancy(int truancy) {
        this.truancy = truancy;
    }

    public int getEarly() {
        return leave_early;
    }

    public void setEarly(int early) {
        this.leave_early = early;
    }

    public int getLate() {
        return late;
    }

    public void setLate(int late) {
        this.late = late;
    }

    public int getExcuse() {
        return excuse;
    }

    public void setExcuse(int excuse) {
        this.excuse = excuse;
    }

    @Override
    public String toString() {
        return "Grades{" +
                "id=" + id +
                ", truancy=" + truancy +
                ", early=" + leave_early +
                ", late=" + late +
                ", excuse=" + excuse +
                '}';
    }
}
