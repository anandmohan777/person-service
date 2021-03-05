package uk.ac.ebi.assignment.personservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.assignment.personservice.entity.Person;
import uk.ac.ebi.assignment.personservice.exception.PersonNotFoundException;
import uk.ac.ebi.assignment.personservice.repository.PersonRepository;
import uk.ac.ebi.assignment.personservice.service.PersonService;

public class PersonServiceImpl implements PersonService {

    private PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<Person> getAllPersons() {
        List<Person> persons = new ArrayList<Person>();
        personRepository.findAll().forEach(person -> persons.add(person));
        return persons;
    }

    @Override
    public Person getPerson(Long id) {
        return personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
    }

    @Override
    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    @Override
    public Person updatePerson(Person person, Long id) {
        return personRepository.findById(id).map(personEntity -> {
            personEntity.setFirstName(person.getFirstName());
            personEntity.setLastName(person.getLastName());
            personEntity.setAge(person.getAge());
            personEntity.setFavouriteColour(person.getFavouriteColour());
            return personRepository.save(personEntity);
        }).orElseThrow(() -> new PersonNotFoundException(id));
    }

    @Override
    public void deleteById(Long personId) {
        if (personRepository.findById(personId).isPresent())
            personRepository.deleteById(personId);
        else
            throw new PersonNotFoundException(personId);
    }

}
