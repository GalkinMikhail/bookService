package com.example.demo.repo;

import com.example.demo.models.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BookRepository extends PagingAndSortingRepository<Book, Long> {//extends - наследование. <> - указание модели, с которой работаем и типа данных
    List<Book> findAllByTitle(String title);
}