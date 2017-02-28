package ru.personalpro.repository;



import ru.personalpro.db.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonsRepository extends CrudRepository<Person, Integer> {

}
