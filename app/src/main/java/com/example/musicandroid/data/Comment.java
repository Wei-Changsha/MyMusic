package com.example.musicandroid.data;

public class Comment {
//    commentId:125,
//    userId:14,
//    songId:143,
//    content:"6666"

    private int commentId;
    private int userId;
    private int songId;
    private String content;


    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
