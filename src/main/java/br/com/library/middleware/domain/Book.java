package br.com.library.middleware.domain;

import java.io.Serializable;

public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String title;

    private String isbn;

    private Long authorId;

    public Book() {
        this.isbn = "default";
    }

    public Book(String title, Long authorId) {
        this.title = title;
        this.authorId = authorId;
        this.isbn = "default";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}
