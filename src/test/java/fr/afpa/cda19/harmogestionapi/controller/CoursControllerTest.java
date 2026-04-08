package fr.afpa.cda19.harmogestionapi.controller;

import fr.afpa.cda19.harmogestionapi.controllers.CoursController;
import fr.afpa.cda19.harmogestionapi.models.Cours;
import fr.afpa.cda19.harmogestionapi.services.CoursService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

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

    private final Cours cours = new Cours();


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
    void createCoursTest() throws Exception {

        mockMvc.perform(post("/cours")
                                .content(new ObjectMapper().writeValueAsString(cours))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateCoursTest() throws Exception {

        mockMvc.perform(put("/cours/1")
                                .content(new ObjectMapper().writeValueAsString(cours))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCoursTest() throws Exception {

        mockMvc.perform(delete("/cours/1")
                                .content(new ObjectMapper().writeValueAsString(cours))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
