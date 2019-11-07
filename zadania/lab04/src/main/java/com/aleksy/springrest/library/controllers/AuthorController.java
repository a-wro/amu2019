package com.aleksy.springrest.library.controllers;

import com.aleksy.springrest.library.exceptions.InvalidDataException;
import com.aleksy.springrest.library.model.Author;
import com.aleksy.springrest.library.model.Book;
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
    public List<Author> list(@RequestParam(value = "book", required = false) Long bookId) {
        if (bookId != null) {
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
        Optional<Author> author = authorRepository.getById(id);

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
    public Author update(@RequestBody Author newAuthor, @PathVariable("id") Long id) {
        Optional<Author> author = authorRepository.getById(id);
        if (author.isPresent()) {
            return authorRepository.update(id, newAuthor);
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) throws ResponseStatusException {
        if (authorRepository.getById(id).isPresent()) {
            authorRepository.delete(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
