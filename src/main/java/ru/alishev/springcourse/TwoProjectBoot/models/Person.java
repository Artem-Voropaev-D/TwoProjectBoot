package ru.alishev.springcourse.TwoProjectBoot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Name should not be empty") // валидация, @NotEmpty чтобы поле не было пустым и меседж сообщение какое вывдем
    @Size(min = 2, max = 30, message =  "Name should be between 2 and 30 characters")// ну длина поля
    @Column(name = "name")
    private String name;

    @Column(name = "year_birth")
    private int yearBirth;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public Person(){

    }

    public Person(String name, int yearBirth) {
        this.name = name;
        this.yearBirth = yearBirth;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public int getYearBirth() {
        return yearBirth;
    }

    public void setYearBirth(int yearBirth) {
        this.yearBirth = yearBirth;
    }
}
