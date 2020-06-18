package com.timur.itlab.newsapp.items;

public class News {
    private String image;
    private String content;
    private String author;
    private String date;

    public News() {
    }


    public String getImage() {
        return image;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
