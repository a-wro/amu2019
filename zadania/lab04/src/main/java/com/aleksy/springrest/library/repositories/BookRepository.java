package com.aleksy.springrest.library.repositories;

import com.aleksy.springrest.library.exceptions.AuthorNotFoundException;
import com.aleksy.springrest.library.model.Book;
import com.aleksy.springrest.library.model.BookId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;


@Repository
public class BookRepository {

    @Autowired AuthorRepository authorRepository;

    private List<Book> books = new ArrayList<>();

    private AtomicLong id = new AtomicLong();

    public Book create(Book book) throws AuthorNotFoundException {
        book.setId(new BookId(id.incrementAndGet()));

        if (!authorRepository.authorsExist(book.getAuthors())) {
            throw new AuthorNotFoundException();
        }

        books.add(book);
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
                book.getYearOfRelease().before(date)).collect(Collectors.toList());
    }

    private List<Book> findReleasedAfter(List<Book> booksSubList, Date date) {
        return booksSubList.stream().filter(book ->
                book.getYearOfRelease().after(date)).collect(Collectors.toList());
    }

    public Optional<Book> getById(Long id) {
        return books.stream().filter(book -> book.getId().equals(id)).findFirst();
    }

    public boolean booksExist(List<BookId> bookIds) {
        for (BookId bookId: bookIds) {
            Optional<Book> optionalBook = books.stream().filter(book -> book.getId().equals(bookId)).findFirst();
            if (!optionalBook.isPresent()) {
                return false;
            }
        }
        return true;
    }

    public Book update(Long id, Book newBook) {
        Book actualBook = getById(id).get();
        actualBook.setAuthors(newBook.getAuthors());
        actualBook.setTitle(newBook.getTitle());
        actualBook.setYearOfRelease(newBook.getYearOfRelease());
        return actualBook;
    }

    public void delete(Long id) {
        books.remove(getById(id).get());
    }

}
