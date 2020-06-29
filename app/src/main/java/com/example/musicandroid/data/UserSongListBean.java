package com.example.musicandroid.data;

import java.io.Serializable;
import java.util.List;


public class UserSongListBean  implements Serializable {

//    collectId:18,
//    userId:14,
//    type:1, //一般而言，为1是用户歌单，为0时为系统歌单，不过一般不需要甄别，因为请求到的数据不会混淆
//    createTime:???,
//    pic:"http://yuanzhihong.xyz/pic/ad5wa24a9.jpg",
//    style:"K-Pop", //流派
//    songs:
//            [
//    {
//        songId:1,
//                name:"City of Stars",
//            introduction:"blablabla",
//            url:"http://yuanzhihong.xyz/song/ad5a24a9.mp3"
//    },
//    {
//        songId:6,
//                name:"DADDY!DADDY!DO!",
//            introduction:"blablabla",
//            url:"http://yuanzhihong.xyz/song/ad7055a4.mp3"
//    }
//        ]

    private int collectId;
    private int userId;
    private int type;
    private String createTime;
    private String pic;
    private String style;
    private List<SongBean> songs;

    public UserSongListBean(String style, List<SongBean> songs) {
        this.style = style;
        this.songs = songs;
    }
    public UserSongListBean(){

    }

    public int getCollectId() {
        return collectId;
    }

    public void setCollectId(int collectId) {
        this.collectId = collectId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public List getSongs() {
        return songs;
    }

    public void setSongs(List songs) {
        this.songs = songs;
    }

    public class SongBean {
        private int songId;
        private String name;
        private String introduction;
        private String url;

        public int getSongId() {
            return songId;
        }

        public void setSongId(int songId) {
            this.songId = songId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
