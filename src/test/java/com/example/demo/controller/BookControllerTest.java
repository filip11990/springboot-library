package com.example.demo.controller;

import com.example.demo.entity.Author;
import com.example.demo.entity.Book;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setup() {
        bookRepository.deleteAll();
        authorRepository.deleteAll(); //czyszczenie bazy
    }

    @Test
    void shouldCreateBook() throws Exception {
        //zapisanie autora w bazie
        Author author = authorRepository.save(new Author(null, "John Doe", null));
    
        //tworzenie ksiazki przypisanej do autora
        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Spring Boot in Action\",\"author\":{\"id\":" + author.getId() + "}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.title", is("Spring Boot in Action")))
                .andExpect(jsonPath("$.author.name", is("John Doe")));
    }

    @Test
    void shouldGetAllBooks() throws Exception {
        Author author = authorRepository.save(new Author(null, "Jane Smith", null));
        bookRepository.save(new Book(null, "Java Basics", author));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("Java Basics")))
                .andExpect(jsonPath("$[0].author.name", is("Jane Smith")));
    }
}

