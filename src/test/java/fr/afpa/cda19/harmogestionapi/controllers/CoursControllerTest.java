package fr.afpa.cda19.harmogestionapi.controllers;

import fr.afpa.cda19.harmogestionapi.models.Cours;
import fr.afpa.cda19.harmogestionapi.models.Instrument;
import fr.afpa.cda19.harmogestionapi.models.Membre;
import fr.afpa.cda19.harmogestionapi.services.CoursService;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
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

    private final Cours cours;


    @MockitoBean
    private CoursService coursService;

    public CoursControllerTest() {
        Membre membre = new Membre();
        membre.setIdMembre(1);
        membre.setNomMembre("Seiwert");
        membre.setPrenomMembre("Thomas");
        membre.setDateInscriptionMembre(LocalDate.now());
        cours = new Cours(null, LocalDateTime.now(), (byte) 45,
                          membre,
                          new Instrument(1, "guitare"),
                          new ArrayList<>());
    }


    @Test
    @Epic("Test Unitaire du contrôleur des cours")
    @Owner("Thomas Seiwert")
    @Description("Test de l'endpoint GET /cours")
    void getAllCoursTest() throws Exception {

        mockMvc.perform(get("/cours")).andExpect(status().isOk());
    }

    @Test
    @Epic("Test Unitaire du contrôleur des cours")
    @Owner("Thomas Seiwert")
    @Description("Test de l'endpoint GET /cours/{id}")
    void getCoursTest() throws Exception {

        mockMvc.perform(get("/cours/1")).andExpect(status().isOk());
    }

    @Test
    @Epic("Test Unitaire du contrôleur des cours")
    @Owner("Thomas Seiwert")
    @Description("Test de l'endpoint POST /cours. Avec un cours sans identifiant")
    void createCoursTestOk() throws Exception {
        Membre membre = new Membre();
        membre.setIdMembre(2);
        membre.setNomMembre("Hendrix");
        membre.setPrenomMembre("Jimmi");
        membre.setDateInscriptionMembre(LocalDate.now());
        cours.getParticipants().add(membre);
        final String json = new ObjectMapper().writeValueAsString(cours);

        mockMvc.perform(post("/cours")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @Epic("Test Unitaire du contrôleur des cours")
    @Owner("Thomas Seiwert")
    @Description("Test de l'endpoint POST /cours. Avec un cours avec identifiant")
    void createCoursTestKo() throws Exception {

        cours.setIdCours(1);
        final String json = new ObjectMapper().writeValueAsString(cours);

        mockMvc.perform(post("/cours")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Epic("Test Unitaire du contrôleur des cours")
    @Owner("Thomas Seiwert")
    @Description("Test de l'endpoint PUT /cours/{id}")
    void updateCoursTest() throws Exception {

        final String json = new ObjectMapper().writeValueAsString(cours);

        mockMvc.perform(put("/cours/1")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Epic("Test Unitaire du contrôleur des cours")
    @Owner("Thomas Seiwert")
    @Description("Test de l'endpoint DELETE /cours/{id}")
    void deleteCoursTest() throws Exception {

        final String json = new ObjectMapper().writeValueAsString(cours);

        mockMvc.perform(delete("/cours/1")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
