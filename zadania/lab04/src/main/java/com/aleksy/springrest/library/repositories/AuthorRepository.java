package com.aleksy.springrest.library.repositories;

import com.aleksy.springrest.library.exceptions.BookNotFoundException;
import com.aleksy.springrest.library.model.Author;
import com.aleksy.springrest.library.model.AuthorId;
import com.aleksy.springrest.library.model.Book;
import com.aleksy.springrest.library.model.BookId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
        author.setAuthorId(authorId);

        if (author.getBooks() != null && !bookRepository.booksExist(author.getBooks())) {
            throw new BookNotFoundException();
        }

        authors.add(author);
        return author;
    }

    public Optional<Author> getById(AuthorId id) {
        return authors.stream().filter(author -> author.getAuthorId().equals(id)).findFirst();
    }

    public Author update(AuthorId id, Author newAuthor) {
        Author actualAuthor = getById(id).get();
        actualAuthor.setFirstName(newAuthor.getFirstName());
        actualAuthor.setLastName(newAuthor.getLastName());
        actualAuthor.setBooks(newAuthor.getBooks());
        return actualAuthor;
    }

    public void delete(AuthorId id) {
        authors.remove(getById(id).get());
    }

    public List<Author> findAuthorsOfBook(BookId id) {
        return authors.stream().filter(author -> author.getBooks().contains(id)).collect(Collectors.toList());
    }

    public boolean authorsExist(Set<AuthorId> authorIds) {
        for (AuthorId authorId : authorIds) {
            Optional<Author> optionalAuthor = authors.stream().filter(author ->
                    author.getAuthorId().equals(authorId)).findFirst();
            if (!optionalAuthor.isPresent()) {
                return false;
            }
        }
        return true;
    }
}


