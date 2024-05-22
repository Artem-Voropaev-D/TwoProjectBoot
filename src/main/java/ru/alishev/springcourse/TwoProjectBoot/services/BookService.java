package ru.alishev.springcourse.TwoProjectBoot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.springcourse.TwoProjectBoot.models.Book;
import ru.alishev.springcourse.TwoProjectBoot.models.Person;
import ru.alishev.springcourse.TwoProjectBoot.repositories.BooksRepository;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    @Autowired
    private final BooksRepository booksRepository;
    public BookService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }
    //ну сортировка по полю
    public List<Book> findAll(Boolean sort) {
        if (sort != null && sort)
            return booksRepository.findAll(Sort.by("yearIzd"));
        else
            return booksRepository.findAll();
    }

    //пагинация - то есть разбивка на страницы, имеем page - номер страницы, size - количество отображаемых элементов

    public List<Book> findAll(Integer page, Integer size, Boolean sort){
        if(sort != null && sort)
            return booksRepository.findAll(PageRequest.of(page, size , Sort.by("yearIzd"))).getContent();
        else
            return booksRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    public Book findOne(int id) {
        Optional<Book> book = booksRepository.findById(id);
        return book.orElse(null);
    }

    public List<Book> findByBookNameStartingWith(String StartingWith){
        return booksRepository.findByBookNameStartingWith(StartingWith);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id,Book book) {
        Optional<Book> fictedBook = booksRepository.findById(id);

        book.setId(id);
        book.setOwner(fictedBook.get().getOwner());
        booksRepository.save(book);

    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    //освободить
    @Transactional
    public void release(int id) {
        Optional<Book> book = booksRepository.findById(id);
        book.get().getOwner().getBooks().remove(book.get());
        book.get().setExpiration(false);
        book.get().setOwner(null);
    }

    //назначить
    @Transactional
    public void assign(int id, Person person) {
        Optional<Book> book = booksRepository.findById(id);
        book.get().setTakenAt(new Date());//кладем ему сами дату создания
        if( person.getBooks() == null)
        {
            person.setBooks(new ArrayList<>());
            person.getBooks().add(book.get());
        }
        book.get().setOwner(person);
    }

    //владелец книги
    public Person getBookOwner(int id){
        Optional<Book> book = booksRepository.findById(id);

        return book.get().getOwner();
    }

}
