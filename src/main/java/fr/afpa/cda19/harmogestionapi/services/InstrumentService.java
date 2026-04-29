package fr.afpa.cda19.harmogestionapi.services;

import fr.afpa.cda19.harmogestionapi.models.Instrument;
import fr.afpa.cda19.harmogestionapi.repositories.InstrumentRepository;
import fr.afpa.cda19.harmogestionapi.repositories.PratiquerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * Service de liaison entre les contrôleurs et le repository
 * {@link InstrumentRepository}.
 *
 * @author Cédric DIDIER
 * @version 1.0.0
 * @since 07/04/2026
 */
@Service
public class InstrumentService {

    /**
     * Repository des instruments
     */
    private final InstrumentRepository instrumentRepository;

    /**
     * Repository de la pratique des instruments
     */
    private final PratiquerRepository pratiquerRepository;

    /**
     * Initialisation du service des instruments.
     *
     * @param instrumentRepository repository des instruments
     * @param pratiquerRepository  repository de l'entité Pratiquer
     */
    @Autowired
    public InstrumentService(final InstrumentRepository instrumentRepository,
                             final PratiquerRepository pratiquerRepository) {
        this.instrumentRepository = instrumentRepository;
        this.pratiquerRepository = pratiquerRepository;
    }

    /**
     * Récupèration d'un instrument par son identifiant.
     *
     * @param id l'identifiant de l'instrument recherché
     * @return ({@link Optional}) l'éventuel instrument correspondant à
     * l'identifiant
     */
    public Optional<Instrument> getInstrument(int id) {
        return instrumentRepository.findById(id);
    }

    /**
     * Récupèration de la liste des instruments.
     *
     * @return la liste des instruments
     */
    public Iterable<Instrument> getInstruments() {
        return instrumentRepository.findAll();
    }

    /**
     * Suppression d'un instrument par son identifiant.
     *
     * @param id l'identifiant de l'instrument à supprimer
     */
    @Transactional
    public void deleteInstrument(int id) {
        pratiquerRepository.deleteAllByIdInstrument(id);
        instrumentRepository.deleteById(id);
    }

    /**
     * Enregistrement ou modification d'un instrument.
     *
     * @param instrument l'instrument à créer ou modifier
     * @return l'instrument après création ou modification
     */
    @Transactional
    public Instrument saveInstrument(Instrument instrument) {
        return instrumentRepository.save(instrument);
    }
}
