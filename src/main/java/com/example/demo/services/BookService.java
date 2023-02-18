package com.example.demo.services;

import com.example.demo.models.Book;
import com.example.demo.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired private BookRepository bookRepository;

    public List<Book> findAll() {
        ArrayList<Book> activities = new ArrayList<>();
        bookRepository.findAll().forEach(activities::add);
        return activities;
    }

    @Transactional
    public Page<Book> findAll(int page, int size) {
        return bookRepository.findAll(PageRequest.of(page, size, Sort.by("id")));
    }

    @Transactional
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public Optional<Book> findById(long id) {
        return  bookRepository.findById(id);
    }

    @Transactional
    public boolean existsById(long id) {
        return bookRepository.existsById(id);
    }

    @Transactional
    public void delete(Book book) {
        bookRepository.delete(book);
    }

}

