package ru.alishev.springcourse.TwoProjectBoot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alishev.springcourse.TwoProjectBoot.models.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer>{

}
