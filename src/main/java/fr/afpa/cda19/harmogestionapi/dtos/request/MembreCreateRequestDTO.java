package fr.afpa.cda19.harmogestionapi.dtos.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDate;
import java.util.Set;

/**
 * Classe DTO des membres avec la liste des instruments pratiqués.
 *
 * @author Cédric DIDIER
 * @version 1.0.0
 * @since 27/04/2026
 */
@Getter
@AllArgsConstructor
public class MembreCreateRequestDTO {

    /**
     * Nom du membre.
     */
    @NotBlank(message = "Un membre doit avoir un nom.")
    @Size(min = 3, max = 30, message = "Le nom du membre doit faire entre "
                                       + "trois et trente caractères de long.")
    private String nom;

    /**
     * Prénom du membre.
     */
    @NotBlank(message = "Un membre doit avoir un prénom.")
    @Size(min = 3, max = 30, message = "Le prénom du membre doit faire entre "
                                       + "trois et trente caractères de long.")
    private String prenom;

    /**
     * Date d'inscription du membre.
     */
    @NotNull(message = "Un membre doit avoir une date d'inscription.")
    @PastOrPresent(message = "La date d'inscription ne peut pas être "
                             + "dans le futur.")
    private LocalDate dateInscription;

    @Size(max = 10, message = "La liste des instruments pratiqués ne doit pas"
                              + " excéder 10 instruments.")
    private Set<@Valid InstrumentPratiqueRequestDTO> instruments;
}
