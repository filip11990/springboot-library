package com.example.demo.service;

import com.example.demo.entity.Author;
import com.example.demo.entity.Book;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    //pobranie wszystkich ksiazek z autorami
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    //pobranie ksiazki z autorem
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    //dodanie ksiazki
    public Book saveBook(Book book) {
        if (book.getAuthor() == null || book.getAuthor().getId() == null) {
            throw new IllegalArgumentException("Author must not be null and must have a valid ID");
        }

        //pobranie autora z bazy
        Author existingAuthor = authorRepository.findById(book.getAuthor().getId()).orElseThrow(() ->
                new IllegalArgumentException("Author with ID " + book.getAuthor().getId() + " does not exist"));

        //ustawienie autora w ksiazce
        book.setAuthor(existingAuthor);

        System.out.println("Saving book: " + book.getTitle() + " by author: " + existingAuthor.getName());
        return bookRepository.save(book);
    }

    //usuwanie ksiazki
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    //pobranie ksiazek bez autorow
    public List<Map<String, Object>> getBooksWithoutAuthor() {
    return bookRepository.findAll().stream()
            .map(book -> {
                Map<String, Object> map = new HashMap<>();
                map.put("id", book.getId());
                map.put("title", book.getTitle());
                return map;
            })
            .collect(Collectors.toList());
    }
}