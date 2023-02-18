package com.example.demo.controllers;

import com.example.demo.models.Book;
import com.example.demo.models.User;
import com.example.demo.services.BookService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class BookController {


    @Autowired //анотация для создания переменной, ссылающейся на репозиторий
    private BookService bookService; //указание репозитория, к которому обращаемся и название пееременной
    @Autowired
    private UserService userService;

    @Configuration
    public class MvcConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/css/**")
                    .addResourceLocations("classpath:/css/");
        }
    }

    // Запрос на авторизацию
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Запрос на регистрацию
    @GetMapping("/registration")
    public String registration(Model model) {
        return "registration";
    }

    // Метод, реализующий запрос на регистрацию
    @PostMapping("/registration")
    public String createUser(User user, Model model) {
        if(!userService.registration(user)) {
            model.addAttribute("errorMessage", "Пользователь с таким логином уже существует");
            return "registration";
        }
        return "redirect:/log";
    }

    @GetMapping("/books")
    public String books(Model model) {
        model.addAttribute("books", bookService.findAll()); //передача значений
        model.addAttribute("title", "Книги");
        return "books";
    }

    @GetMapping("/books/add") //GetMapping - пользователь переходит по определённому адресу
    public String booksAdd(Model model) {
        return "books-add";
    }

    @RequestMapping(value = "/books/add",method = RequestMethod.POST) //получение данных из формы
    public String booksBooksAdd(@RequestParam String img, @RequestParam String title, @RequestParam String author, @RequestParam String fullText) { //@RequestParam - получение значений из формы. title - получение значений из данного поля
        Book book = new Book(img, title, fullText,author); //объект на основе модели book с названием book. (title, author, fullText) - передача параметров
        bookService.save(book); //сохранение объекта и добавление в бд -> обращение к репозиторию -> обращение к функции save и передача в него объекта, который необходимо сохранить => добавление в таблицу book, полученных от пользователя
        return "redirect:/books"; //переадресация пользователя на указанную страницу после добавления книги
    }

    @GetMapping("/books/{id}") //{id} - динамическое значение url-адреса
    public String booksDetails(@PathVariable(value = "id") long id, Model model) { //@PathVariable - анотация, принимающая динамический параметр из url-адреса (в определённый параметр (long id) помещается значение, полученное из url-адреса
        Optional<Book> book= bookService.findById(id); //нахождение записи по id и помещение в объект book на основе класса Optional и модели <Book>
        if(book.isPresent()) {
            ArrayList<Book> res = new ArrayList<>();
            book.ifPresent(res::add); //из класса Optional переводим в класс ArrayList
            model.addAttribute("books", res);
            return "books-details";
        } else {
            return "redirect:/books"; //перенаправление на указанную страницу
        }
    }

    @GetMapping("/books/{id}/edit") //редактирование блюда
    public String booksEdit(@PathVariable(value = "id") long id, Model model) { //@PathVariable - анотация, принимающая динамический параметр из url-адреса (в определённый параметр (long id) помещается значение, полученное из url-адреса
        if(!bookService.existsById(id)){ //try - если определённая запись по определённому id не была найдена. иначе false
            return "redirect:/books"; //перенаправление на указанную страницу
        }
        Optional<Book> book= bookService.findById(id); //нахождение записи по id и помещение в объект book на основе класса Optional и модели <Book>
        ArrayList<Book> res = new ArrayList<>();
        book.ifPresent(res::add); //из класса Optional переводим в класс ArrayList
        model.addAttribute("books", res);
        return "books-edit";
    }

    @PostMapping("/books/{id}/edit") //получение данных из формы
    public String booksBooksUpdate(@PathVariable(value = "id") long id, @RequestParam String img, @RequestParam String title, @RequestParam String author, @RequestParam String fullText, Model model) { //@RequestParam - получение значений из формы. title - получение значений из данного поля
        Book book = bookService.findById(id).orElseThrow(
                () -> new RuntimeException()
        ); //orElseTrow() - исключительная ситуация в случае не нахождения записи
        book.setImg(img);
        book.setTitle(title); //установка введеного заголовка
        book.setAuthor(author);
        book.setFullText(fullText);
        bookService.save(book); //сохранение обновлённого объекта
        return "redirect:/books/{id}"; //переадресация пользователя на указанную страницу после добавления книги
    }

    @PostMapping("/books/{id}/remove") //получение данных из формы
    public String booksDelete(@PathVariable(value = "id") long id, Model model) { //@RequestParam - получение значений из формы. title - получение значений из данного поля
        Book book = bookService.findById(id).orElseThrow(
                () -> new RuntimeException()
        ); //orElseTrow() - исключительная ситуация в случае не нахождения записи
        bookService.delete(book); //удаление определенной книги
        return "redirect:/books"; //переадресация пользователя на указанную страницу после удаления книги
    }
}
