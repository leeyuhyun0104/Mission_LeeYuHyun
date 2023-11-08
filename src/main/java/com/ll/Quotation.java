package com.ll;

import java.io.Serializable;

public class Quotation implements Serializable {
    private int id;
    private String content;
    private String author;

    // 생성자
    Quotation(int id, String content, String author){
        this.id = id;
        this.content = content;
        this.author = author;
    }

    // id에 접근하는 메소드
    int getId(){
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    // content에 접근하는 메소드
    String getContent(){
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    // author에 접근하는 메소드
    String getAuthor(){
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
}

