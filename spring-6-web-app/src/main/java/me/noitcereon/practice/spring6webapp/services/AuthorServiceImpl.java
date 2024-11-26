package me.noitcereon.practice.spring6webapp.services;

import me.noitcereon.practice.spring6webapp.domain.Author;
import me.noitcereon.practice.spring6webapp.repositories.AuthorRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepo;

    public AuthorServiceImpl(AuthorRepository authorRepo) {
        this.authorRepo = authorRepo;
    }

    @Override
    public Iterable<Author> findAll() {
        return authorRepo.findAll();
    }
}
