package ru.alishev.springcourse.TwoProjectBoot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alishev.springcourse.TwoProjectBoot.models.Book;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findByBookNameStartingWith(String startingWith);//поиск по первым буквам названия
}
