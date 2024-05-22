package ru.alishev.springcourse.TwoProjectBoot.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.TwoProjectBoot.models.Person;
import ru.alishev.springcourse.TwoProjectBoot.services.PeopleService;


@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;


    @Autowired // внедрение зависимости
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("people", peopleService.findAll()); //берем из дао список людей чтоб вывести его на отображение
//        personDAO.testNplus1();
//        itemService.findByItemName("Airpods");
//        itemService.findByOwner(peopleService.findAll().getFirst());
//
//        peopleService.test();
        return "people/index";
    }
    @GetMapping("/{id}") // такой синтаксис значит что когда мы запустим приложение туда можно вставить любое число
    public String show(@PathVariable("id") int id, Model model){ //с помощью PathVariable мы извлечем id из url и получим к нему доступ
        model.addAttribute("person", peopleService.findOne(id)); //берем из дао одного человека чтоб вывести его на отображение
        model.addAttribute("books", peopleService.getBooksByPersonId(id));////????????
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){ // @ModelAttribute - сама создаст объект класса персон и положит его в модель
        return "people/new";
    }

    //@Valid - ну валидируем что раньше прописали аннотациями в Person, bindingResult - объект, который будет хранить все ошибки
    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "people/new";

        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model,
                       @PathVariable("id") int id){ // @PathVariable - извлекаем из юрл значение id
        model.addAttribute("person", peopleService.findOne(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "people/edit";

        peopleService.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        peopleService.delete(id);
        return "redirect:/people";
    }
}
