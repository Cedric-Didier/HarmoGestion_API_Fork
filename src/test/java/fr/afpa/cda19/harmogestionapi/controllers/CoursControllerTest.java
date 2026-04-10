package fr.afpa.cda19.harmogestionapi.controllers;

import fr.afpa.cda19.harmogestionapi.models.Cours;
import fr.afpa.cda19.harmogestionapi.models.Instrument;
import fr.afpa.cda19.harmogestionapi.models.Membre;
import fr.afpa.cda19.harmogestionapi.services.CoursService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests unitaires Mock du controller des cours.
 *
 * @author Seiwert Thomas
 * @version 0.0.1
 * @since 08/04/2026
 */
@WebMvcTest(controllers = CoursController.class)
class CoursControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final Cours cours =
            new Cours(null, LocalDateTime.now(), (byte) 45,
                      new Membre(1, "Seiwert",
                                 "Thomas", LocalDate.now()),
                      new Instrument(1, "guitare"),
                      new ArrayList<>());


    @MockitoBean
    private CoursService coursService;


    @Test
    void getAllCoursTest() throws Exception {

        mockMvc.perform(get("/cours")).andExpect(status().isOk());
    }

    @Test
    void getCoursTest() throws Exception {

        mockMvc.perform(get("/cours/1")).andExpect(status().isOk());
    }

    @Test
    void createCoursTestOk() throws Exception {

        cours.getParticipants().add(new Membre(2, "Hendrix",
                                               "Jimmi", LocalDate.now()));
        final String json = new ObjectMapper().writeValueAsString(cours);

        mockMvc.perform(post("/cours")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void createCoursTestKo() throws Exception {

        cours.setIdCours(1);
        final String json = new ObjectMapper().writeValueAsString(cours);

        mockMvc.perform(post("/cours")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateCoursTest() throws Exception {

        final String json = new ObjectMapper().writeValueAsString(cours);

        mockMvc.perform(put("/cours/1")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteCoursTest() throws Exception {

        final String json = new ObjectMapper().writeValueAsString(cours);

        mockMvc.perform(delete("/cours/1")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
