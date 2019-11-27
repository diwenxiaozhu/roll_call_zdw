package com.app.sqlite.db.entity;

public class Checkin {

    private int id;
    private String studentId;
    private String name;
    private String clazz;
    private String sex;
    private String headImage = "";
    private String time;
    private String type;

    public Checkin(String studentId, String name, String clazz, String sex, String headImage) {
        this.studentId = studentId;
        this.name = name;
        this.clazz = clazz;
        this.sex = sex;
        this.headImage = headImage;
    }

    public Checkin(int id, String studentId, String name, String clazz, String sex, String headImage, String time, String type) {
        this.id = id;
        this.studentId = studentId;
        this.name = name;
        this.clazz = clazz;
        this.sex = sex;
        this.headImage = headImage;
        this.time = time;
        this.type = type;
    }

    public Checkin(String studentId, String name, String clazz, String sex, String headImage, String time, String type) {
        this.studentId = studentId;
        this.name = name;
        this.clazz = clazz;
        this.sex = sex;
        this.headImage = headImage;
        this.time = time;
        this.type = type;
    }

    public Checkin(String studentId, String name, String clazz, String sex, String time, String type) {
        this.studentId = studentId;
        this.name = name;
        this.clazz = clazz;
        this.sex = sex;
        this.time = time;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }
}
