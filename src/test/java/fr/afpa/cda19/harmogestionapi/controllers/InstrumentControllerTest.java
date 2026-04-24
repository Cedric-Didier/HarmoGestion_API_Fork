package fr.afpa.cda19.harmogestionapi.controllers;

import fr.afpa.cda19.harmogestionapi.models.Instrument;
import fr.afpa.cda19.harmogestionapi.services.InstrumentService;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = InstrumentController.class)
class InstrumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InstrumentService service;

    @Test
    @Epic("Test Unitaire du contrôleur des instruments")
    @Owner("Cédric DIDIER")
    @Description("Test de l'endpoint GET /instruments")
    void getInstrumentsTest() throws Exception {
        mockMvc.perform(get("/instruments")).andExpect(status().isOk());
    }

    @Test
    @Epic("Test Unitaire du contrôleur des instruments")
    @Owner("Cédric DIDIER")
    @Description("Test de l'endpoint GET /instrument/{id}")
    void getInstrumentTestKo() throws Exception {
        mockMvc.perform(get("/instrument/1")).andExpect(status().isNotFound());
    }

    @Test
    @Epic("Test Unitaire du contrôleur des instruments")
    @Owner("Cédric DIDIER")
    @Description("Test de l'endpoint DELETE /instrument/{id}")
    void deleteInstrumentTest() throws Exception {
        mockMvc.perform(delete("/instrument/1")).andExpect(status().isOk());
    }

    @Test
    @Epic("Test Unitaire du contrôleur des instruments")
    @Owner("Cédric DIDIER")
    @Description("Test de l'endpoint PUT /instrument/{id}")
    void updateInstrumentTest() throws Exception {
        mockMvc.perform(
                put("/instrument/1").contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(
                                new Instrument()
                        ))).andExpect(status().isNotFound());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"a", "br",
            "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz"})
    @Epic("Test Unitaire du contrôleur des instruments")
    @Owner("Cédric DIDIER")
    @Description("Test de l'endpoint POST /instrument/{id} "
                 + "avec un libelle d'instrument incorrect")
    void createInstrumentTestKo(final String libelle) throws Exception {
        Allure.parameter("libelleInstrument", libelle);
        Instrument instrument = new Instrument(null, libelle);
        String json = new ObjectMapper().writeValueAsString(instrument);
        mockMvc.perform(post("/instrument")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {"tria", "clarinette"})
    @Epic("Test Unitaire du contrôleur des instruments")
    @Owner("Cédric DIDIER")
    @Description("Test de l'endpoint POST /instrument/{id} "
                 + "avec un libelle d'instrument correct")
    void createInstrumentTestOk(String libelle) throws Exception {
        Allure.parameter("libelleInstrument", libelle);
        Instrument instrument = new Instrument(null, libelle);
        String json = new ObjectMapper().writeValueAsString(instrument);
        mockMvc.perform(post("/instrument")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}