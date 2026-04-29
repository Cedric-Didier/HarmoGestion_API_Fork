package fr.afpa.cda19.harmogestionapi.repositories;

import fr.afpa.cda19.harmogestionapi.models.Instrument;
import fr.afpa.cda19.harmogestionapi.models.Membre;
import fr.afpa.cda19.harmogestionapi.models.Pratiquer;
import fr.afpa.cda19.harmogestionapi.models.PratiquerPK;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PratiquerRepository
        extends CrudRepository<Pratiquer, PratiquerPK> {
    Iterable<Pratiquer> findByMembre(final Membre membre);

    Iterable<Pratiquer> findByInstrument(final Instrument instrument);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Pratiquer p WHERE p.id.idInstrument=?1")
    void deleteAllByIdInstrument(final Integer idInstrument);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Pratiquer p WHERE p.id.idMembre=?1")
    void deleteAllByIdMembre(final Integer idMembre);
}
