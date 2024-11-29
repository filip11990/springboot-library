package com.example.demo.controller;

import com.example.demo.entity.Author;
import com.example.demo.service.AuthorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    //zwracanie wszystkich autorow z ksiazkami
    @GetMapping
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    //zwracanie konkretnego autora z ksiazkami
    @GetMapping("/{id}")
    public Author getAuthorWithBooks(@PathVariable Long id) {
        return authorService.getAuthorById(id);
    }

    //zwracanie wszystkich autorow bez ksiazek
    @GetMapping("/simple")
    public List<Map<String, Object>> getAuthorsWithoutBooks() {
        return authorService.getAuthorsWithoutBooks();
    }

    //dodanie nowego autora
    @PostMapping
    public Author createAuthor(@RequestBody Author author) {
        return authorService.saveAuthor(author);
    }

    //aktualizacja autora
    @PutMapping("/{id}")
    public Author updateAuthor(@PathVariable Long id, @RequestBody Author authorDetails) {
        Author existingAuthor = authorService.getAuthorById(id);
        if (existingAuthor != null) {
            existingAuthor.setName(authorDetails.getName());
            return authorService.saveAuthor(existingAuthor);
        }
        return null;
    }

    //usuniecie autora
    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
    }
}