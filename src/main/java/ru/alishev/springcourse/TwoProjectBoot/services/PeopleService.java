package ru.alishev.springcourse.TwoProjectBoot.services;

import jakarta.persistence.EntityManager;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.springcourse.TwoProjectBoot.models.Book;
import ru.alishev.springcourse.TwoProjectBoot.models.Person;
import ru.alishev.springcourse.TwoProjectBoot.repositories.PeopleRepository;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final EntityManager entityManager;


    private final PeopleRepository peopleRepository;
    @Autowired
    public PeopleService(PeopleRepository peopleRepository, EntityManager entityManager) {
        this.entityManager = entityManager;
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll(){//метод чтобы вернуть все сущности из таблицы БД (типо индекс как я понимаю)
        return peopleRepository.findAll();
    }



    public Person findOne(int id){ // ну опшинал это на нулл проверочка чтоб не было выбросов исключений, а сам метод типо как шоу то есть возваращем одного человека
        Optional<Person> founfperson = peopleRepository.findById(id);
        return founfperson.orElse(null);
    }

    @Transactional//пометили здесь потому что здесь уже не нужен общий ридонли тру
    public void save(Person person){
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedperson){
        updatedperson.setId(id);//тип обновляем айди человека на точно такой же и жпа видит что такой человек уже есть поэтому при методе сев будет именно обновлять данные, а не создавать новую сущность
        peopleRepository.save(updatedperson);
    }

    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }

    /////////////????????????????????????????????????????????????????????????????????
    @Transactional
    public List<Book> getBooksByPersonId(int id){
        Optional<Person> person = peopleRepository.findById(id);

        Hibernate.initialize(person.get().getBooks());//подгружаем книги человека так как у нас ленивая загрузка стоит
        //подгрузить книги человека и сделать проверку на просрочку
        Date currentDate = new Date();
        for (Book book : person.get().getBooks()) {
            long val =  currentDate.getTime() - book.getTakenAt().getTime();
            System.out.println("Book person: " + book.getTakenAt().getTime());
            System.out.println( currentDate.getTime());
            if(val > 864000000){
                book.setExpiration(true);System.out.println("true");
            }
        }



        return person.get().getBooks();
    }

}
