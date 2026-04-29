package fr.afpa.cda19.harmogestionapi;

import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HarmoGestionApiApplicationTests {

    @Test
    @DisplayName("Test chargement du context spring boot")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Cédric DIDIER")
    void contextLoads() {
    }

}
