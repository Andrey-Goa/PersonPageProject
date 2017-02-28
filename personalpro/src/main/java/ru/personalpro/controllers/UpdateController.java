package ru.personalpro.controllers;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.personalpro.db.Person;
import ru.personalpro.service.PersonService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by andrey-goa on 28.02.17.
 */
@Controller
public class UpdateController {

    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/persons/change", method = RequestMethod.POST)
    public @ResponseBody
    String changePerson(@RequestBody Person[] persons) {
        ArrayList<Person> personList = new ArrayList<>();
        ExecutorService service = Executors.newCachedThreadPool();
        for (Person per : persons) {
            Integer id = per.getId();
            personList.add(personService.find(id));

        }

        Iterator<Person> personIterator = personList.iterator();

        while(personIterator.hasNext()){
            service.submit(new Runnable() {
                public void run() {
                    Person person = personIterator.next();
                    java.util.Date utilDate = new java.util.Date();
                    java.sql.Timestamp sq = new java.sql.Timestamp(utilDate.getTime());
                    person.setComment("Обработано [ " +  sq.toLocalDateTime() + " ]");
                    person.setUpdate_date(sq);
                    personService.update(person);
                }
            });
        }
        return new JSONObject().put("status", "ok").toString();

    }

}
