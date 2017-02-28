package ru.personalpro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.personalpro.service.PersonService;

import static javax.swing.JOptionPane.ERROR_MESSAGE;

/**
 * Created by andrey-goa on 20.02.17.
 */

@Controller
public class LoginController {

    @Autowired
    private PersonService personService;

    @RequestMapping(value="/", method = RequestMethod.GET)
    public String getStartPage() {
        return "formlogin";

    }



}
