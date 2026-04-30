package fr.afpa.cda19.harmogestionapi.services;

import fr.afpa.cda19.harmogestionapi.dtos.request.InstrumentCreateRequestDTO;
import fr.afpa.cda19.harmogestionapi.dtos.response.InstrumentResponseDTO;
import fr.afpa.cda19.harmogestionapi.exceptions.RessourceDupliqueeException;
import fr.afpa.cda19.harmogestionapi.exceptions.RessourceNonTrouveException;
import fr.afpa.cda19.harmogestionapi.mappers.InstrumentMapper;
import fr.afpa.cda19.harmogestionapi.models.Instrument;
import fr.afpa.cda19.harmogestionapi.repositories.InstrumentRepository;
import fr.afpa.cda19.harmogestionapi.repositories.PratiquerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
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
@RequiredArgsConstructor
public class InstrumentService {

    /**
     * Repository des instruments
     */
    private final InstrumentRepository instrumentRepository;

    /**
     * Repository de la pratique des instruments
     */
    private final PratiquerRepository pratiquerRepository;

    private final InstrumentMapper instrumentMapper;

    /**
     * Récupèration d'un instrument par son identifiant.
     *
     * @param id l'identifiant de l'instrument recherché
     * @return ({@link InstrumentResponseDTO}) le dto correspondant à l'instrument recherché
     */
    public InstrumentResponseDTO getInstrument(int id) throws RessourceNonTrouveException {
        Instrument instrument = instrumentRepository.findById(id).orElseThrow(
                () -> new RessourceNonTrouveException("Impossible de trouver l'instrument"));
        return instrumentMapper.toDto(instrument);
    }

    /**
     * Récupèration de la liste des instruments.
     *
     * @return la liste des instruments
     */
    public List<InstrumentResponseDTO> getInstruments() {
        Iterable<Instrument> instruments = instrumentRepository.findAll();
        List<InstrumentResponseDTO> listeDto = new ArrayList<>();
        instruments.forEach(instrument -> listeDto.add(instrumentMapper.toDto(instrument)));
        return listeDto;
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

    @Transactional
    public InstrumentResponseDTO createInstrument(InstrumentCreateRequestDTO dto)
            throws RessourceDupliqueeException {
        if(instrumentRepository.existsByLibelleInstrument(dto.getNom())){
            throw new RessourceDupliqueeException("Cet instrument existe déjà");
        }
        Instrument instrument = instrumentMapper.toEntity(dto);
        Instrument savedInstrument = instrumentRepository.save(instrument);
        return instrumentMapper.toDto(savedInstrument);
    }
}
