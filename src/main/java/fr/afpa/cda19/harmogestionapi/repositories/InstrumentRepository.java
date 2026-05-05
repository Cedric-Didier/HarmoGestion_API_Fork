package fr.afpa.cda19.harmogestionapi.repositories;

import fr.afpa.cda19.harmogestionapi.models.Instrument;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository des instruments.
 *
 * @author Cédric DIDIER
 * @version 1.0.0
 * @since 07/04/2026
 */
public interface InstrumentRepository
        extends CrudRepository<Instrument, Integer> {

    /**
     * Vérifie l'existence d'un instrument avec ce libellé.
     * @param libelleInstrument libellé à tester
     * @return true si un instrument possède ce libellé
     */
    boolean existsByLibelleInstrument(final String libelleInstrument);

    /**
     * Vérifie l'existence d'un autre instrument avec ce libellé.
     * @param libelleInstrument libellé à tester
     * @param idInstrument identifiant de l'instrument à exclure de la vérification
     * @return true si un autre instrument possède ce libellé
     */
    boolean existsByLibelleInstrumentAndIdInstrumentNot(final String libelleInstrument,
                                                        final Integer idInstrument);
}
