package com.mvc.model;

import java.sql.Date;

public class BbsDto {

    private int num;      // 게시글 번호
    private String title; // 게시글 제목
    private String id;    // 작성자 아이디
    private String content; // 게시글 내용
    private Date nalja;   // 게시글 작성일

    // Getter와 Setter
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getNalja() {
        return nalja;
    }

    public void setNalja(Date nalja) {
        this.nalja = nalja;
    }
}
