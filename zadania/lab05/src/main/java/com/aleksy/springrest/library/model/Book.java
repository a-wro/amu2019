package com.aleksy.springrest.library.model;

import com.aleksy.springrest.library.utils.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

public class Book {

    @NotEmpty
    private String title;

    @Size(min = 1, message = "Books must have at least one author")
    private Set<AuthorId> authors = new HashSet<>();

    private BookId bookId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_FORMAT)
    @NotNull
    private Date releaseDate;

    public Date getReleaseDate() {

        return releaseDate;
    }

    public void setReleaseDate(Date date) {
        this.releaseDate = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<AuthorId> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<AuthorId> authors) {
        this.authors = authors;
    }

    public BookId getBookId() {
        return bookId;
    }

    public void setBookId(BookId bookId) {
        this.bookId = bookId;
    }
}
