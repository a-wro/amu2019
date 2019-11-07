package com.aleksy.springrest.library.model;

import com.aleksy.springrest.library.utils.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

public class Book {
    @NotEmpty
    private String title;

    @Size(min = 1)
    private List<AuthorId> authors;

    private BookId id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_FORMAT)
    @NotNull
    private Date yearOfRelease;

    public Date getYearOfRelease() {

        return yearOfRelease;
    }

    public void setYearOfRelease(Date yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<AuthorId> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorId> authors) {
        this.authors = authors;
    }

    public BookId getId() {
        return id;
    }

    public void setId(BookId id) {
        this.id = id;
    }
}
