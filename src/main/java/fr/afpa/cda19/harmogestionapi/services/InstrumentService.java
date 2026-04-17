package fr.afpa.cda19.harmogestionapi.services;

import fr.afpa.cda19.harmogestionapi.models.Instrument;
import fr.afpa.cda19.harmogestionapi.repositories.InstrumentRepository;
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
    private final InstrumentRepository repository;

    @Autowired
    public InstrumentService(InstrumentRepository repository) {
        this.repository = repository;
    }

    /**
     * Récupèration d'un instrument par son identifiant.
     *
     * @param id l'identifiant de l'instrument recherché
     * @return ({@link Optional}) l'éventuel instrument correspondant à
     * l'identifiant
     */
    public Optional<Instrument> getInstrument(int id) {
        return repository.findById(id);
    }

    /**
     * Récupèration de la liste des instruments.
     *
     * @return la liste des instruments
     */
    public Iterable<Instrument> getInstruments() {
        return repository.findAll();
    }

    /**
     * Suppression d'un instrument par son identifiant.
     *
     * @param id l'identifiant de l'instrument à supprimer
     */
    public void deleteInstrument(int id) {
        repository.deleteById(id);
    }

    /**
     * Enregistrement ou modification d'un instrument.
     *
     * @param instrument l'instrument à créer ou modifier
     * @return l'instrument après création ou modification
     */
    public Instrument saveInstrument(Instrument instrument) {
        return repository.save(instrument);
    }
}
