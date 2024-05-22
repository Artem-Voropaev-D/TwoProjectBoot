package ru.alishev.springcourse.TwoProjectBoot.dao;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonDAO {

    private final EntityManager entityManager;

    @Autowired
    public PersonDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}

//старый index
//    List<Person> people = new ArrayList<>();
//
//        try {
//                Statement statement =  connection.createStatement();// этот объект и содержит в себе sql - запрос
//                String SQL = "SELECT * FROM Person"; // это и есть наш sql запрос, он получит строки из нашей БД
//                ResultSet  resultSet = statement.executeQuery(SQL);//выполняем sql запрос, executeQuery - получает данные из БД
//
//                while (resultSet.next()) { // пробегаем по всей таблице по каждой строке пока она не закончится и вписываем вручную в лист
//                Person person = new Person();
//
//                person.setId(resultSet.getInt("id"));//получаем значение из колонки с названием id и вставляем в значение id person
//                person.setName(resultSet.getString("name"));
//                person.setAge(resultSet.getInt("age"));
//                person.setEmail(resultSet.getString("email"));
//
//                people.add(person);
//
//                }
//
//                } catch (SQLException e) {
//                throw new RuntimeException(e);
//                }
//
//

//старый show
//    Person person = null;
//
//        try {
//                PreparedStatement preparedStatement =
//                connection.prepareStatement("SELECT * FROM Person WHERE id = ?");// ну опять же формируем запрос
//
//                preparedStatement.setInt(1,id);// кладем в наш запрос id которое нам пришло в качестве аргумента
//
//                ResultSet resultSet = preparedStatement.executeQuery(); // делаем запрос к нашей БД и кладем полученное значение в resultSet
//
//                //эти манипуляции связаны с тем что у нас сейчас костыль в виде одинакового id = 1, поэтому может вернуться несколько строк и мы возьмем в связи с этим ток 1 строку
//                resultSet.next();
//
//                person = new Person();
//
//                person.setId(resultSet.getInt("id"));
//                person.setName(resultSet.getString("name"));
//                person.setAge(resultSet.getInt("age"));
//                person.setEmail(resultSet.getString("email"));
//                } catch (SQLException e) {
//                throw new RuntimeException(e);
//                }
//
//                return person;

//старый save
//        try {
//                PreparedStatement preparedStatement =
//                connection.prepareStatement("INSERT INTO Person VALUES(1, ?, ?, ?)");// защищеный и более быстрый стейтмент, сразу вбиваем наш sql - запрос, ? - параметры, которые ниже добавляем
//
//                preparedStatement.setString(1, person.getName());//добавляем в запрос имя введеное пользователем, а ниже возраст и емаил
//                preparedStatement.setInt(2,person.getAge());
//                preparedStatement.setString(3, person.getEmail());
//
//                preparedStatement.executeUpdate();//выполняем запрос
//
//                } catch (SQLException e) {
//                throw new RuntimeException(e);
//                }

//старый update
//         try {
//                 PreparedStatement preparedStatement =
//                 connection.prepareStatement("UPDATE Person SET name=?, age=?, email=? WHERE id=?");
//
//                 preparedStatement.setString(1, updatedPerson.getName());//добавляем в запрос имя введеное пользователем, а ниже возраст и емаил
//                 preparedStatement.setInt(2, updatedPerson.getAge());
//                 preparedStatement.setString(3, updatedPerson.getEmail());
//                 preparedStatement.setInt(4, id);
//
//                 preparedStatement.executeUpdate();
//                 } catch (SQLException e) {
//                 throw new RuntimeException(e);
//                 }

//старй delete
//         try {
//                 PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Person WHERE id=?");
//
//                 preparedStatement.setInt(1, id);
//
//                 preparedStatement.executeUpdate();
//                 } catch (SQLException e) {
//                 throw new RuntimeException(e);
//                 }