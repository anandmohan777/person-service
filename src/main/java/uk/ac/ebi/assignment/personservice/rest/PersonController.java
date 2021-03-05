package uk.ac.ebi.assignment.personservice.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uk.ac.ebi.assignment.personservice.dto.PersonDTO;
import uk.ac.ebi.assignment.personservice.entity.Person;
import uk.ac.ebi.assignment.personservice.service.PersonService;

@RequestMapping("/person")
@RestController
public class PersonController {
    private PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<PersonDTO> getAllPersons() {
        return personService.getAllPersons().stream().map(person -> convertToDto(person)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PersonDTO getPerson(@PathVariable Long id) {
        Person person = personService.getPerson(id);
        return convertToDto(person);
    }

    @PostMapping
    public PersonDTO createPerson(@RequestBody PersonDTO personDto) {
        Person person = convertToEntity(personDto);
        return convertToDto(personService.createPerson(person));
    }

    @PutMapping("/{id}")
    public PersonDTO updatePerson(@RequestBody PersonDTO personDto, @PathVariable Long id) {
        Person person = convertToEntity(personDto);
        return convertToDto(personService.updatePerson(person, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePerson(@PathVariable Long id) {
        personService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private PersonDTO convertToDto(final Person person) {
        return new PersonDTO(person.getId(), person.getFirstName(), person.getLastName(), person.getAge(),
                person.getFavouriteColour());
    }

    private Person convertToEntity(final PersonDTO personDto) {
        return new Person(personDto.getFirstName(), personDto.getLastName(), personDto.getAge(),
                personDto.getFavouriteColour());
    }
}
