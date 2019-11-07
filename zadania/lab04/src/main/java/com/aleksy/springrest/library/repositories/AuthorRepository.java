package com.aleksy.springrest.library.repositories;

import com.aleksy.springrest.library.exceptions.BookNotFoundException;
import com.aleksy.springrest.library.model.Author;
import com.aleksy.springrest.library.model.AuthorId;
import com.aleksy.springrest.library.model.BookId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class AuthorRepository {

    @Autowired
    private BookRepository bookRepository;

    private List<Author> authors = new ArrayList<>();
    private AtomicLong id = new AtomicLong();

    public List<Author> list() {
        return authors;
    }

    public Author create(Author author) throws BookNotFoundException {
        AuthorId authorId = new AuthorId(id.incrementAndGet());
        author.setId(authorId);

        if (author.getBooks() != null && !bookRepository.booksExist(author.getBooks())) {
            throw new BookNotFoundException();
        }

        authors.add(author);
        return author;
    }

    public Optional<Author> getById(Long id) {
        BookId bookId = new BookId(id);
        return authors.stream().filter(author -> author.getId().getId().equals(bookId.getId())).findFirst();
    }

    public Author update(Long id, Author newAuthor) {
        Author actualAuthor = getById(id).get();
        actualAuthor.setFirstName(newAuthor.getFirstName());
        actualAuthor.setLastName(newAuthor.getLastName());
        actualAuthor.setBooks(newAuthor.getBooks());
        return actualAuthor;
    }

    public void delete(Long id) {
        authors.remove(getById(id).get());
    }

    public List<Author> findAuthorsOfBook(Long id) {
        BookId bookId = new BookId(id);
        return authors.stream().filter(author -> author.getBooks().contains(bookId)).collect(Collectors.toList());
    }

    public boolean authorsExist(List<AuthorId> authorIds) {
        for (AuthorId authorId : authorIds) {
            Optional<Author> optionalAuthor = authors.stream().filter(author -> author.getId().equals(authorId)).findFirst();
            if (!optionalAuthor.isPresent()) {
                return false;
            }
        }
        return true;
    }

}


