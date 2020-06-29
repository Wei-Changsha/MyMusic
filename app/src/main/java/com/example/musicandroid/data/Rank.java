package com.example.musicandroid.data;

public class Rank {

//    rankId:1, //RankId仅仅是一个主键，它和在列表中的位置不能反映实际排名，虽然会按照score来排序，请自己对score排序后使用
//    songId:14,
//    score:7.9 //排行榜分数
    private int rankId;
    private int songId;
    private int score;

    public int getRankId() {
        return rankId;
    }

    public void setRankId(int rankId) {
        this.rankId = rankId;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
