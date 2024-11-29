package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    //wszystkie ksiazki z autorami
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    //jedna ksiazka z autorem
    @GetMapping("/{id}")
    public Book getBookWithAuthor(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    //ksiazki bez autorow
    @GetMapping("/simple")
    public List<Map<String, Object>> getBooksWithoutAuthor() {
        return bookService.getBooksWithoutAuthor();
    }

    //dodanie nowej ksiazki
    @PostMapping
    public Book createBook(@RequestBody Book book) {
        System.out.println("Received book: " + book.getTitle() + ", Author ID: " + 
            (book.getAuthor() != null ? book.getAuthor().getId() : "null"));
    
        if (book.getAuthor() == null || book.getAuthor().getId() == null) {
            throw new IllegalArgumentException("Author must not be null and must have a valid ID");
        }
    
        return bookService.saveBook(book);
    }

    //aktualizacja ksiazki
    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        Book existingBook = bookService.getBookById(id);
        if (existingBook != null) {
            existingBook.setTitle(bookDetails.getTitle());
            existingBook.setAuthor(bookDetails.getAuthor());
            return bookService.saveBook(existingBook);
        }
        return null;
    }

    //usuniecie ksiazki
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}