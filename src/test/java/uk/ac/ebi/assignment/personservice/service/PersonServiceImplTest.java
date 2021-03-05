package uk.ac.ebi.assignment.personservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import uk.ac.ebi.assignment.personservice.entity.Person;
import uk.ac.ebi.assignment.personservice.repository.PersonRepository;
import uk.ac.ebi.assignment.personservice.service.impl.PersonServiceImpl;

@ExtendWith(SpringExtension.class)
public class PersonServiceImplTest {
    private PersonService personService;
    @MockBean
    private PersonRepository personRepository;

    @BeforeEach
    public void before() {
        personService = new PersonServiceImpl(personRepository);
    }

    @Test
    public void getAllPersons_whenCallAPI_thenReturnTwoRecords() throws Exception {
        when(personRepository.findAll()).thenReturn(Arrays.asList(getPersonOne(), getPersonTwo()));

        List<Person> personsResult = personService.getAllPersons();
        assertEquals(2, personsResult.size());
        assertTrue(getPersonOne().equals(personsResult.get(0)));
        assertTrue(getPersonTwo().equals(personsResult.get(1)));

    }

    @Test
    public void getPerson_whenCallAPI_thenReturnPersonRecord() throws Exception {
        when(personRepository.findById(getPersonOne().getId())).thenReturn(Optional.of(getPersonOne()));

        Person personsResult = personService.getPerson(getPersonOne().getId());
        assertTrue(getPersonOne().equals(personsResult));
    }

    @Test
    public void createPerson_whenCallAPI_thenReturnPersonCreatedRecord() throws Exception {
        when(personRepository.save(getPersonOne())).thenReturn(getPersonOne());

        Person personsResult = personService.createPerson(getPersonOne());
        assertTrue(getPersonOne().equals(personsResult));
    }

    @Test
    public void updatePerson_whenCallAPI_thenReturnPersonCreatedRecord() throws Exception {
        Person personOldRecord = getPersonOne();
        Person personUpdated = getPersonOne();
        personUpdated.setFirstName("personUpdated firstname");
        personUpdated.setFirstName("personUpdated lastname");
        personUpdated.setFavouriteColour("color3");
        personUpdated.setAge(25);

        when(personRepository.findById(personUpdated.getId())).thenReturn(Optional.of(personOldRecord));
        when(personRepository.save(personUpdated)).thenReturn(personUpdated);

        Person personsResult = personService.updatePerson(personUpdated, personUpdated.getId());
        assertTrue(personUpdated.equals(personsResult));
    }

    @Test
    public void deleteById_whenCallAPI_thenVerifyDeleteCalled() throws Exception {
        when(personRepository.findById(getPersonOne().getId())).thenReturn(Optional.of(getPersonOne()));

        personService.deleteById(getPersonOne().getId());
        verify(personRepository, times(1)).deleteById(getPersonOne().getId());
    }

    private Person getPersonOne() {
        Person p1 = new Person("p1 firstname", "p1 lastname", 20, "color1");
        p1.setId(1l);
        return p1;
    }

    private Person getPersonTwo() {
        Person p2 = new Person("p2 firstname", "p2 lastname", 30, "color2");
        p2.setId(2l);
        return p2;
    }
}
