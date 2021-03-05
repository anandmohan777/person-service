package uk.ac.ebi.assignment.personservice.service;

import java.util.List;

import uk.ac.ebi.assignment.personservice.entity.Person;

public interface PersonService {
    List<Person> getAllPersons();

    Person getPerson(Long personId);

    Person createPerson(Person person);

    Person updatePerson(Person person, Long personId);

    void deleteById(Long personId);
}
