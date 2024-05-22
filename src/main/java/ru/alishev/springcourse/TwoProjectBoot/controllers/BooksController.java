package ru.alishev.springcourse.TwoProjectBoot.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.TwoProjectBoot.models.Book;
import ru.alishev.springcourse.TwoProjectBoot.models.Person;
import ru.alishev.springcourse.TwoProjectBoot.services.BookService;
import ru.alishev.springcourse.TwoProjectBoot.services.PeopleService;


@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookService bookService;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model, @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "size",required = false) Integer size,
                        @RequestParam(value = "sort",required = false) Boolean sort) {
        if (page == null || size == null) {
            model.addAttribute("books", bookService.findAll(sort));
        }
        else {
            model.addAttribute("books", bookService.findAll(page, size, sort));
        }
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        if (bookService.getBookOwner(id) != null) {
            model.addAttribute("owner", bookService.getBookOwner(id));
        } else {
            model.addAttribute("people", peopleService.findAll());
        }

        model.addAttribute("book", bookService.findOne(id));

        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) { // @ModelAttribute - сама создаст объект класса персон и положит его в модель
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) // смотрим есть ли в bindingResult ошибки
            return "books/new";

        bookService.save(book);// как раз таки save и добавляет в БД, а @ModelAttribute заполняет сам конструктор данными полученными с формы
        return "redirect:/books";// redirect - мы говорим браузеру на какую страницу в итоге перейти, в данном случае /people
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model,
                       @PathVariable("id") int id) { // @PathVariable - извлекаем из юрл значение id
        model.addAttribute("book", bookService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult, @PathVariable("id") int id) {

        if (bindingResult.hasErrors())
            return "books/edit";

        bookService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookService.release(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        bookService.assign(id, person);
        return "redirect:/books/" + id;
    }

    @GetMapping("/search")
    public String searchBook(@ModelAttribute("book") Book book){ // @ModelAttribute - сама создаст объект класса персон и положит его в модель
        return "books/search";
    }

    @PostMapping("/search")
    public String search(Model model,
                         @RequestParam(value = "startingWith", required = false) String startingWith){

        model.addAttribute("books", bookService.findByBookNameStartingWith(startingWith));
        return "books/search";
    }

}
