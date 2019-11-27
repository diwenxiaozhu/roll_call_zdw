package com.app.sqlite.db.entity;

public class Studentinfo {
    private String id;    //学号
    private String name;    //姓名
    private String clazz;   //班级
    private String sex;     //性别
    private String headImage = "";    //头像地址

    public Studentinfo(String id, String name, String clazz, String sex, String headImage) {
        this.id = id;
        this.name = name;
        this.clazz = clazz;
        this.sex = sex;
        this.headImage = headImage;
    }

    public Studentinfo(String id, String name, String clazz, String sex) {
        this.id = id;
        this.name = name;
        this.clazz = clazz;
        this.sex = sex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }
}
