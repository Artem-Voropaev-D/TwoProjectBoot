package ru.alishev.springcourse.TwoProjectBoot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "У книги должно быть название")
    @Column(name = "book_name")
    private String bookName;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    @Column(name = "year_izd")
    private int yearIzd;

    @NotEmpty(message = "Name should not be empty") // валидация, @NotEmpty чтобы поле не было пустым и меседж сообщение какое вывдем
    @Size(min = 2, max = 30, message =  "Name should be between 2 and 30 characters")// ну длина поля
    @Column(name = "author")
    private String author;

    //ДОБАВИТЬ В БД И ЕЩЕ АННОТАЦИЮ ГДЕ-ТО ДОБАВИТЬ ТИП ТРАНЗИЕНТ
    @org.springframework.data.annotation.Transient
    private boolean expiration;

    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takenAt;

    public Book(){

    }

    public Book(String bookName, Person owner, int yearIzd) {
        this.bookName = bookName;
        this.owner = owner;//??????????
        this.yearIzd = yearIzd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getYearIzd() {
        return yearIzd;
    }

    public void setYearIzd(int yearIzd) {
        this.yearIzd = yearIzd;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isExpiration() {
        return expiration;
    }

    public void setExpiration(boolean expiration) {
        this.expiration = expiration;
    }

    public Date getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(Date takenAt) {
        this.takenAt = takenAt;
    }
}
