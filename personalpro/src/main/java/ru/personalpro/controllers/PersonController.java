package ru.personalpro.controllers;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.personalpro.db.Person;
import ru.personalpro.service.PersonService;
import ru.personalpro.service.PersonValidator;



import javax.validation.Valid;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;




/**
 * Created by andrey-goa on 22.02.17.
 */
@Controller
public class PersonController {

    @Autowired
    private PersonValidator personValidator;

    @Autowired
    private PersonService personService;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(personValidator);
    }


    @RequestMapping(value = "/persons", method = RequestMethod.GET)
    public String getPersons(Model model) {
        model.addAttribute("persons", personService.all());
        return "persons";
    }

    @RequestMapping(value = "/persons/new", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
    public @ResponseBody String createPerson(@Valid Person person, BindingResult result) throws ParseException {
        if(personService.idInDb(person.getId())){
            return new JSONObject().put("id", "Id уже существует!").put("success", "false").toString();
        }
        if (result.hasErrors()) {
            JSONObject jsonObject = new JSONObject();
            for (FieldError error : result.getFieldErrors()) {
                String k = error.getField();
                String v = error.getDefaultMessage();
                jsonObject.put(k,v);
            }
            jsonObject.put("success", "false");
            return jsonObject.toString();
        }
        Integer id = person.getId();
        String last_name = person.getLast_name();
        String first_name = person.getFirst_name();
        String middle_name = person.getMiddle_name();
        Date birth_date = person.getBirth_date();
        personService.create(person);
        return new JSONObject().put("method", "create").put("success", "true").toString();


    }

    @RequestMapping(value = "/persons/del", method = RequestMethod.POST)
    public @ResponseBody
    String deletePerson(Person person) {
        Integer id = person.getId();
        personService.delete(id);
        return new JSONObject().put("status", "ok").toString();

    }

    @RequestMapping(value = "/persons/edit", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
    public @ResponseBody
    String updatePerson(@Valid Person person, BindingResult result ) throws ParseException{
        Integer id = person.getId();
        person.setId(id);
        if (result.hasErrors()) {
            JSONObject jsonObject = new JSONObject();
            for (FieldError error : result.getFieldErrors()) {
                String k = error.getField();
                String v = error.getDefaultMessage();
                jsonObject.put(k,v);
            }
            jsonObject.put("success", "false");
            return jsonObject.toString();
        }
        String last_name = person.getLast_name();
        String first_name = person.getFirst_name();
        String middle_name = person.getMiddle_name();
        Date birth_date = person.getBirth_date();
        Person person2 = personService.find(id);
        String comment = person2.getComment();
        Timestamp update_date = person2.getUpdate_date();
        person.setComment(comment);
        person.setUpdate_date(update_date);
        personService.update(person);
        return new JSONObject().put("method", "update").put("success", "true").toString();

    }

    @RequestMapping(value = "/persons/getperson", method = RequestMethod.POST)
    public @ResponseBody
    Person getPerson(Person person) throws ParseException {
        if(!(personService.idInDb(person.getId()))){
            person.setId(0);
            return person;
        }
        Integer id = person.getId();
        person = personService.find(id);
        SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy");
        Date dd = person.getBirth_date();
        person.setBirth_date(f.parse(f.format(dd)));
        return person;

    }

}


