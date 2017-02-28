package ru.personalpro.service;


import org.springframework.transaction.annotation.Transactional;
import ru.personalpro.db.Person;
import ru.personalpro.repository.PersonsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Service
public class PersonService {

    @Autowired
    private PersonsRepository personsRepository;

    public Iterable<Person> all() {
        return personsRepository.findAll();
    }

    public Person create(Person person) {
        return personsRepository.save(person);
    }

    public Person find(int personId) {
        return personsRepository.findOne(personId);
    }

    @Transactional(propagation = REQUIRES_NEW)
    public Person update(Person updatedPerson) {
        return personsRepository.save(updatedPerson);
    }

    public void delete(int personId) {
        personsRepository.delete(personId);
    }

    public Integer findMaxId() {
        Iterable<Person> iterable = personsRepository.findAll();
        List<Integer> target = new ArrayList<>();
        for(Person in: iterable){
            target.add(in.getId());


    }
    return Collections.max(target);
}

    public boolean idInDb(Integer id) {
        Iterable<Person> iterable = personsRepository.findAll();
        List<Integer> target = new ArrayList<>();
        for(Person in: iterable){
            target.add(in.getId());
        }
        return target.contains(id);
    }



}
