package uk.ac.ebi.assignment.personservice.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uk.ac.ebi.assignment.personservice.dto.PersonDTO;
import uk.ac.ebi.assignment.personservice.entity.Person;
import uk.ac.ebi.assignment.personservice.service.PersonService;

import java.util.Arrays;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PersonService personService;
    private static ObjectMapper objectMapper;

    @BeforeAll
    public static void before() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void getAllPersons_whenCallAPI_thenReturnTwoRecords() throws Exception {
        when(personService.getAllPersons()).thenReturn(Arrays.asList(getPersonOne(), getPersonTwo()));
        mvc.perform(get("/person"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getAllPersons_whenCallAPI_thenReturnNoRecord() throws Exception {
        when(personService.getAllPersons()).thenReturn(Arrays.asList());
        mvc.perform(get("/person"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void getPerson_whenCallAPI_thenReturnPersonRecord() throws Exception {
        when(personService.getPerson(getPersonOne().getId())).thenReturn(getPersonOne());
        mvc.perform(get("/person/" + getPersonOneDto().getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(getPersonOne().getId()))
                .andExpect(jsonPath("$.first_name").value(getPersonOne().getFirstName()))
                .andExpect(jsonPath("$.last_name").value(getPersonOne().getLastName()))
                .andExpect(jsonPath("$.age").value(getPersonOne().getAge()))
                .andExpect(jsonPath("$.favourite_colour").value(getPersonOne().getFavouriteColour()));
    }


    @Test
    public void createPerson_whenCallAPI_thenReturnPersonCreatedRecord() throws Exception {
        when(personService.createPerson(getPersonOneWithoutId())).thenReturn(getPersonOne());
        mvc.perform(post("/person")
                .content(objectMapper.writeValueAsBytes(getPersonOneDto()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.first_name").value(getPersonOneDto().getFirstName()))
                .andExpect(jsonPath("$.last_name").value(getPersonOneDto().getLastName()))
                .andExpect(jsonPath("$.age").value(getPersonOneDto().getAge()))
                .andExpect(jsonPath("$.favourite_colour").value(getPersonOneDto().getFavouriteColour()));
    }

    @Test
    public void createPerson_whenCallAPIWithoutPerson_thenReturnError() throws Exception {
        mvc.perform(post("/person"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updatePerson_whenCallAPI_thenReturnPersonUpdatedRecord() throws Exception {
        when(personService.updatePerson(getPersonOneWithoutId(), getPersonOneDto().getId())).thenReturn(getPersonOne());
        mvc.perform(put("/person/" + getPersonOneDto().getId())
                .content(objectMapper.writeValueAsBytes(getPersonOneDto()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(getPersonOneDto().getId()))
                .andExpect(jsonPath("$.first_name").value(getPersonOneDto().getFirstName()))
                .andExpect(jsonPath("$.last_name").value(getPersonOneDto().getLastName()))
                .andExpect(jsonPath("$.age").value(getPersonOneDto().getAge()))
                .andExpect(jsonPath("$.favourite_colour").value(getPersonOneDto().getFavouriteColour()));
    }

    @Test
    public void updatePerson_whenCallAPIWithoutPerson_thenReturnError() throws Exception {
        mvc.perform(put("/person/" + getPersonOne().getId()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deletePerson_whenCallAPI_thenReturnSuccess() throws Exception {
        doNothing().when(personService).deleteById(getPersonOne().getId());
        mvc.perform(delete("/person/" + getPersonOne().getId()))
                .andExpect(status().isOk());
    }

    private PersonDTO getPersonOneDto() {
        PersonDTO p1 = new PersonDTO(1234l, "p1 firstname", "p1 lastname", 20, "color1");
        return p1;
    }

    private Person getPersonOne() {
        Person p1 = new Person("p1 firstname", "p1 lastname", 20, "color1");
        p1.setId(1234l);
        return p1;
    }

    private Person getPersonOneWithoutId() {
        Person personOneWithoutId = getPersonOne();
        personOneWithoutId.setId(null);
        return personOneWithoutId;
    }

    private Person getPersonTwo() {
        Person p2 = new Person("p2 firstname", "p2 lastname", 30, "color2");
        p2.setId(5678l);
        return p2;
    }

}
