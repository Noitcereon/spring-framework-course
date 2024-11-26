/*
 * Copyright (c) 2024. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package me.noitcereon.practice.spring6webapp.bootstrap;

import me.noitcereon.practice.spring6webapp.domain.Author;
import me.noitcereon.practice.spring6webapp.domain.Book;
import me.noitcereon.practice.spring6webapp.domain.Publisher;
import me.noitcereon.practice.spring6webapp.repositories.AuthorRepository;
import me.noitcereon.practice.spring6webapp.repositories.BookRepository;
import me.noitcereon.practice.spring6webapp.repositories.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Spring Framework picks up any {@link CommandLineRunner} and runs them on startup.
 */
@Component // Spring creates a "spring bean" for any Pojo's marked with Component annotation.
public class BootstrapData implements CommandLineRunner {

    private final AuthorRepository authorRepo;
    private final BookRepository bookRepo;
    private final PublisherRepository publisherRepo;

    public BootstrapData(AuthorRepository authorRepo, BookRepository bookRepo, PublisherRepository publisherRepo) {
        this.authorRepo = authorRepo;
        this.bookRepo = bookRepo;
        this.publisherRepo = publisherRepo;
    }

    @Override
    public void run(String... args) {
        Publisher shirePub = new Publisher();
        shirePub.setPublisherName("Shire");
        shirePub.setCity("Fantasia");
        shirePub.setAddress("Cloud Street 21");
        shirePub.setState("Heaven");
        shirePub.setZip("9999");

        Publisher shirePubSaved = publisherRepo.save(shirePub);
        Author eric = new Author();
        eric.setFirstName("Eric");
        eric.setLastName("Evans");

        Book ddd = new Book();
        ddd.setTitle("Domain Driven Design");
        ddd.setIsbn("123456");
        ddd.setPublisher(shirePubSaved);

        Author ericSaved = authorRepo.save(eric);
        Book dddSaved = bookRepo.save(ddd);

        Author rod = new Author();
        rod.setFirstName("Rod");
        rod.setLastName("Johnson");

        Book anotherBook = new Book();
        anotherBook.setTitle("Some Book Title");
        anotherBook.setIsbn("532122");

        Author rodSaved = authorRepo.save(rod);
        Book anotherBookSaved = bookRepo.save(anotherBook);

        // Add the ManyToMany relationship to both sides of the relation for it to be persisted on save.
        ericSaved.getBooks().add(dddSaved);
        rodSaved.getBooks().add(anotherBookSaved);
        dddSaved.getAuthors().add(ericSaved);
        anotherBookSaved.getAuthors().add(rodSaved);

        authorRepo.save(ericSaved);
        authorRepo.save(rodSaved);
        bookRepo.save(dddSaved);
        bookRepo.save(anotherBookSaved);

        System.out.println("Bootstrap complete.");
        System.out.println("Author count: " + authorRepo.count());
        System.out.println("Book count: " + bookRepo.count());
        System.out.println("Publisher count: " + publisherRepo.count());
        System.out.println("ShirePub saved id is = " + shirePubSaved.getId());
        System.out.println(dddSaved);
    }
}
