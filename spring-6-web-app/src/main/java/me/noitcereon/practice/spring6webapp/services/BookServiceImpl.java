
package me.noitcereon.practice.spring6webapp.services;

import me.noitcereon.practice.spring6webapp.domain.Book;
import me.noitcereon.practice.spring6webapp.repositories.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepo;

    public BookServiceImpl(BookRepository bookRepository) {
        bookRepo = bookRepository;
    }
    @Override
    public Iterable<Book> findAll() {
        return bookRepo.findAll();
    }
}
