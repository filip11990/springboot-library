package com.example.demo.service;

import com.example.demo.entity.Author;
import com.example.demo.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    //pobranie wszystkich autorow z ksiazkami
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    //pobranie autora z ksiazkami by id
    public Author getAuthorById(Long id) {
        return authorRepository.findById(id).orElse(null);
    }

    //dodanie nowego autora
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    //usuwanie autora
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    //pobranie autorow bez ksiazek
    public List<Map<String, Object>> getAuthorsWithoutBooks() {
        return authorRepository.findAll().stream()
                .map(author -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", author.getId());
                    map.put("name", author.getName());
                    return map;
                })
                .collect(Collectors.toList());
    }
}