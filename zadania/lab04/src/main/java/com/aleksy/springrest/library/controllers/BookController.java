package com.aleksy.springrest.library.controllers;

import com.aleksy.springrest.library.exceptions.BookPreviewDisabledException;
import com.aleksy.springrest.library.exceptions.InvalidDataException;
import com.aleksy.springrest.library.model.Book;
import com.aleksy.springrest.library.repositories.AuthorRepository;
import com.aleksy.springrest.library.repositories.BookRepository;
import com.aleksy.springrest.library.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("book")
public class BookController {
    
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping
    public List<Book> list() {
        return bookRepository.list();
    }

    @GetMapping("{id}")
    public Book detail(@PathVariable("id") Long id) throws ResponseStatusException {
        Optional<Book> book = bookRepository.getById(id);
        if (book.isPresent()) {
            return book.get();
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public Book create(@RequestBody @Valid Book book) throws InvalidDataException {
        return bookRepository.create(book);
    }

    @PutMapping
    public Book createWithPut(@RequestBody @Valid Book book) throws InvalidDataException {
        return bookRepository.create(book);
    }

    @PutMapping("{id}")
    public Book update(@RequestBody Book newBook,
                       @PathVariable("id") Long id) throws ResponseStatusException {

        if (bookRepository.getById(id).isPresent()) {
            return bookRepository.update(id, newBook);
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) throws ResponseStatusException {
        if (bookRepository.getById(id).isPresent()) {
            bookRepository.delete(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("search")
    public List<Book> search(@RequestParam(value = "title", required = false) String title,
                             @RequestParam(value = "before", required = false)
                             @DateTimeFormat(pattern = Constants.DATE_FORMAT) Date before,
                             @RequestParam(value = "after", required = false)
                             @DateTimeFormat(pattern = Constants.DATE_FORMAT) Date after) {
        boolean valid = validateDateQueryParams(before, after);
        if (!valid) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Please make sure before date query param is less than after date query param");
        }

        return bookRepository.findBooks(title, before, after);
    }

    private boolean validateDateQueryParams(Date beforeDate, Date afterDate) {
        if (beforeDate != null && afterDate != null) {
            return afterDate.after(beforeDate);
        }

        return true;
    }

    @GetMapping("preview/{id}")
    public String readBookPreview(@PathVariable("id") Long id) throws BookPreviewDisabledException {
        throw new BookPreviewDisabledException();
    }
}
