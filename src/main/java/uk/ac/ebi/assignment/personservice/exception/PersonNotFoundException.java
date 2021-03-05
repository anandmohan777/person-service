package uk.ac.ebi.assignment.personservice.exception;

public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException(Long id) {
        super("Unable to find person id " + id);
    }
}
