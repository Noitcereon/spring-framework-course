package me.noitcereon.practice.spring6webapp.services;

import me.noitcereon.practice.spring6webapp.domain.Author;

public interface AuthorService {
    Iterable<Author> findAll();
}
