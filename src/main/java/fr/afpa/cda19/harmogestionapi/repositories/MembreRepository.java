package fr.afpa.cda19.harmogestionapi.repositories;

import fr.afpa.cda19.harmogestionapi.models.Membre;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository des membres.
 *
 * @author Rodolphe BRUCKER
 * @version 1.0.0
 * @since 09/04/2026
 */
public interface MembreRepository
        extends CrudRepository<Membre, Integer> {

}
