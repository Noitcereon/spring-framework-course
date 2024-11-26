package me.noitcereon.practice.spring6webapp.controllers;

import me.noitcereon.practice.spring6webapp.domain.Book;
import me.noitcereon.practice.spring6webapp.services.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Iterator;

@Controller
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping("/books")
    public String getBooks(Model model){
        model.addAttribute("books", bookService.findAll());
        return "booksView";
    }
}
