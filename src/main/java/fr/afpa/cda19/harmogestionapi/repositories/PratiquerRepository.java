package fr.afpa.cda19.harmogestionapi.repositories;

import fr.afpa.cda19.harmogestionapi.models.Pratiquer;
import fr.afpa.cda19.harmogestionapi.models.PratiquerPK;
import org.springframework.data.repository.CrudRepository;

public interface PratiquerRepository
        extends CrudRepository<Pratiquer, PratiquerPK> {
    Iterable<Pratiquer> findByIdMembre(final Integer idMembre);

    Iterable<Pratiquer> findByIdInstrument(final Integer idInstrument);

    void deleteAllByIdInstrument(final Integer idInstrument);

    void deleteAllByIdMembre(final Integer idMembre);
}
