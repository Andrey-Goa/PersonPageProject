package ru.personalpro.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.personalpro.db.Person;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PersonValidator implements Validator {
    @Autowired
    private PersonService personService;

    public static boolean onlyStr(String regStr){
        Pattern p = Pattern.compile("^[А-Яа-я]+$");
        Matcher m = p.matcher(regStr);
        return m.matches();
    }



    public static boolean regDate(Date regStr) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        try {
            if (!regStr.equals(format.parse(format.format(regStr)))) {
                return true;
            } else {
                return false;
            }
        }catch (ParseException p){
            return true;
        }
    }

    @Override
    public boolean supports(Class<?> classz) {
        return Person.class.isAssignableFrom(classz);
    }


    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        Format parser = NumberFormat.getInstance();
        if (person.getId() == null) {
            person.setId(personService.findMaxId() + 1);
        } else {
            try {
                if (!(person.getId() instanceof Integer)) {
                    errors.rejectValue("id", null, "Id должно быть целым числом!");
                }

            } catch (Exception e) {
                errors.rejectValue("id", null, "Id должно быть целым числом!");
            }
        }

        if (person.getId() <= 0) {
            errors.rejectValue("id", null, "Id не может быть отрицательным или равняться нулю!");
        }

        if (!onlyStr(person.getLast_name())) {
            errors.rejectValue("last_name", null, "Фамилия должна  содержать только буквы кириллицы!");
        }

        if (!onlyStr(person.getFirst_name())) {
            errors.rejectValue("first_name", null, "Имя должно содержать только буквы кириллицы!");
        }

        if (!onlyStr(person.getMiddle_name())) {
            errors.rejectValue("middle_name", null, "Отчество должно содержать только буквы кириллицы!");
        }

        try {
            if (person.getBirth_date() == null || !(person.getBirth_date() instanceof Date)) {
                errors.rejectValue("birth_date", null, "Формат даты рождения должен быть: ДД.ММ.ГГГГ");
            } else if (regDate(person.getBirth_date())) {
                errors.rejectValue("birth_date", null, "Формат даты рождения должен быть: ДД.ММ.ГГГГ");
            }
        } catch (ParseException p) {
            errors.rejectValue("birth_date", null, "Формат даты рождения должен быть: ДД.ММ.ГГГГ");
        }

    }

}

