package com.example.demo.models;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Book {
    @Id //аннотация - уникальный идентификатор
    @GeneratedValue(strategy = GenerationType.AUTO) //аннотация - при добавлении новой записи позволит генерировать новые значения внутри данного поля (автоматически)
    private Long id; //поле - уникальные идентификатор (Long - тип данных)
    private String img, title; // поле - название турнира, анонс, полный текст описания, дата
    private String author;

    @Column(length = 10000)
    private String fullText;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String calories) {
        this.author = calories;
    }

    public Book(String img, String title, String fullText, String author) {
        this.img = img;
        this.title = title;
        this.fullText = fullText;
        this.author = author;
    }

    public Book(String title){
        this.title = title;
    }
}
