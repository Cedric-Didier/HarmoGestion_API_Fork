package fr.afpa.cda19.harmogestionapi.repositories;

import fr.afpa.cda19.harmogestionapi.models.Instrument;
import fr.afpa.cda19.harmogestionapi.models.Membre;
import fr.afpa.cda19.harmogestionapi.models.Pratiquer;
import fr.afpa.cda19.harmogestionapi.models.PratiquerPK;
import org.springframework.data.repository.CrudRepository;

public interface PratiquerRepository
        extends CrudRepository<Pratiquer, PratiquerPK> {

    Iterable<Pratiquer> findByMembre(final Membre membre);

    Iterable<Pratiquer> findByInstrument(final Instrument instrument);

    void deleteAllByInstrument(final Instrument instrument);

    void deleteAllByMembre(final Membre membre);
}
