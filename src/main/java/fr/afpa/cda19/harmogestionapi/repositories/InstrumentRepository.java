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

}
