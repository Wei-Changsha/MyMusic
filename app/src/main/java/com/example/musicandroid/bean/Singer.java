package com.example.musicandroid.bean;

import java.sql.Date;

public class Singer {
    private int id;                 //主键
    private int sex;                //性别
    private String name;            //姓名
    private Date birth;             //生日
    private String pic;             //指向歌手的图片资源
    private String location;        //地区
    private String introduction;    //

    private Singer(){}

    public Singer(String name, String introduction) {
        this.name = name;
        this.introduction = introduction;
    }

    public Singer(int id, int sex, String name, Date birth, String pic,
                  String location, String introduction) {
        this.id = id;
        this.sex = sex;
        this.name = name;
        this.birth = birth;
        this.pic = pic;
        this.location = location;
        this.introduction = introduction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
