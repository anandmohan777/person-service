package uk.ac.ebi.assignment.personservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import uk.ac.ebi.assignment.personservice.repository.PersonRepository;
import uk.ac.ebi.assignment.personservice.service.PersonService;
import uk.ac.ebi.assignment.personservice.service.impl.PersonServiceImpl;

@Configuration
public class ApplicationConfig {
    @Bean
    public PersonService personService(PersonRepository personRepository) {
        return new PersonServiceImpl(personRepository);
    }
}
