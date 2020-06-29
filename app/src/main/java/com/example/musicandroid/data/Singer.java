package com.example.musicandroid.data;

public class Singer {

//    singerId=12,
//    name="唐沢美帆",
//    sex=0,  //性别请参照User类
//    birth=???, //请参照User类
//    pic="http://yuanzhihong.xyz/pic/1523664.jpg",
//    introduction:"唐沢美帆（からさわ みほ、1983年7月15日 - ）は、日本の歌手、作詞家。アニソン歌手としては「TRUE」名義。 所属事務所はSCOOP MUSIC、所属レーベルはLantis。東京都出身で、身長166cm・血液型はA型である。お茶の水女子大学附属中学校・高等学校を卒業した。",
//    location:"Japan"

    private int singerId;
    private String name;
    private int sex;
    private String birth;
    private String pic;
    private String introduction;
    private String location;

    public int getSingerId() {
        return singerId;
    }

    public void setSingerId(int singerId) {
        this.singerId = singerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
