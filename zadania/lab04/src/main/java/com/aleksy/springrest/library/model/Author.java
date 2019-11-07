package com.aleksy.springrest.library.model;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class Author {
    private List<BookId> books;

    private AuthorId id;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<BookId> getBooks() {
        return books;
    }

    public void setBooks(List<BookId> books) {
        this.books = books;
    }

    public AuthorId getId() {
        return id;
    }

    public void setId(AuthorId id) {
        this.id = id;
    }

}
