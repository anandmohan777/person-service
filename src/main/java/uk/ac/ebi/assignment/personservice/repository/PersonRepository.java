package uk.ac.ebi.assignment.personservice.repository;

import org.springframework.data.repository.CrudRepository;

import uk.ac.ebi.assignment.personservice.entity.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {
}
