package com.app.sqlite.db.entity;

public class Clazz {

    private int id;   //班级id 自增长
    private String name;   //班级名称

    public Clazz(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Clazz(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
