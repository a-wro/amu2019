package com.aleksy.springrest.library.controllers;

import com.aleksy.springrest.library.exceptions.InvalidDataException;
import com.aleksy.springrest.library.model.Author;
import com.aleksy.springrest.library.model.AuthorId;
import com.aleksy.springrest.library.model.Book;
import com.aleksy.springrest.library.model.BookId;
import com.aleksy.springrest.library.repositories.AuthorRepository;
import com.aleksy.springrest.library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("author")

public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public List<Author> list(@RequestParam(value = "book", required = false) Long id) {
        if (id != null) {
            BookId bookId = new BookId(id);
            Optional<Book> book = bookRepository.getById(bookId);

            if (book.isPresent()) {
                return authorRepository.findAuthorsOfBook(bookId);
            }

            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return authorRepository.list();
    }

    @GetMapping("{id}")
    public Author detail(@PathVariable("id") Long id) {
        Optional<Author> author = authorRepository.getById(new AuthorId(id));

        if (author.isPresent()) {
            return author.get();
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public Author create(@RequestBody @Valid Author author) throws InvalidDataException {
        return authorRepository.create(author);
    }

    @PutMapping
    public Author createWithPut(@RequestBody @Valid Author author) throws InvalidDataException {
        return authorRepository.create(author);
    }


    @PutMapping("{id}")
    public Author update(@RequestBody @Valid Author newAuthor, @PathVariable("id") Long id) {
        AuthorId authorId = new AuthorId(id);
        Optional<Author> author = authorRepository.getById(authorId);
        if (author.isPresent()) {
            return authorRepository.update(authorId, newAuthor);
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) throws ResponseStatusException {
        AuthorId authorId = new AuthorId(id);
        if (authorRepository.getById(authorId).isPresent()) {
            authorRepository.delete(authorId);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
