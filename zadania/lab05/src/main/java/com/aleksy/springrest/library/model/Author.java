package com.aleksy.springrest.library.model;

import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

public class Author {

    private Set<BookId> books = new HashSet<>();

    private AuthorId authorId;

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

    public Set<BookId> getBooks() {
        return books;
    }

    public void setBooks(Set<BookId> books) {
        this.books = books;
    }

    public AuthorId getAuthorId() {
        return authorId;
    }

    public void setAuthorId(AuthorId authorId) {
        this.authorId = authorId;
    }

    public void addBook(BookId bookId) {
        books.add(bookId);
    }

}
