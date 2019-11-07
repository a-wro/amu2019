package com.aleksy.springrest.library.repositories;

import com.aleksy.springrest.library.exceptions.AuthorNotFoundException;
import com.aleksy.springrest.library.model.AuthorId;
import com.aleksy.springrest.library.model.Book;
import com.aleksy.springrest.library.model.BookId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@Repository
public class BookRepository {

    @Autowired private AuthorRepository authorRepository;

    private List<Book> books = new ArrayList<>();

    private AtomicLong id = new AtomicLong();

    public Book create(Book book) throws AuthorNotFoundException {
        book.setBookId(new BookId(id.incrementAndGet()));

        if (!authorRepository.authorsExist(book.getAuthors())) {
            throw new AuthorNotFoundException();
        }

        books.add(book);

        BookId bookId = book.getBookId();

        // for all authors add the book to their book set
        for (AuthorId authorId : book.getAuthors()) {
            authorRepository.getById(authorId).get().addBook(bookId);
        }

        return book;
    }

    public List<Book> list() {
        return books;
    }

    public List<Book> findBooks(String title, Date before, Date after) {

        List<Book> booksCopy = new ArrayList<>(books);
        if (title != null && !title.isEmpty()) {
            booksCopy = findByTitle(booksCopy, title);
        }

        if (before != null) {
            booksCopy = findReleasedBefore(booksCopy, before);
        }

        if (after != null) {
            booksCopy = findReleasedAfter(booksCopy, after);
        }

        return booksCopy;
    }

    private List<Book> findByTitle(List<Book> booksSubList, String title) {
        return booksSubList.stream().filter(book ->
                book.getTitle().contains(title)).collect(Collectors.toList());
    }

    private List<Book> findReleasedBefore(List<Book> booksSubList, Date date) {
        return booksSubList.stream().filter(book ->
                book.getReleaseDate().before(date)).collect(Collectors.toList());
    }

    private List<Book> findReleasedAfter(List<Book> booksSubList, Date date) {
        return booksSubList.stream().filter(book ->
                book.getReleaseDate().after(date)).collect(Collectors.toList());
    }

    public Optional<Book> getById(BookId id) {
        Logger logger = Logger.getGlobal();

        logger.log(Level.INFO, id.getId().toString());

        for (Book book : books) {
            logger.log(Level.INFO, book.getBookId().getId().toString());
        }

        return books.stream().filter(book -> book.getBookId().equals(id)).findFirst();
    }

    public boolean booksExist(Set<BookId> bookIds) {
        for (BookId bookId: bookIds) {
            Optional<Book> optionalBook = books.stream().filter(book -> book.getBookId().equals(bookId)).findFirst();
            if (!optionalBook.isPresent()) {
                return false;
            }
        }
        return true;
    }

    public Book update(BookId id, Book newBook) {
        Book actualBook = getById(id).get();
        actualBook.setAuthors(newBook.getAuthors());
        actualBook.setTitle(newBook.getTitle());
        actualBook.setReleaseDate(newBook.getReleaseDate());
        return actualBook;
    }

    public void delete(BookId id) {
        books.remove(getById(id).get());
    }

}
