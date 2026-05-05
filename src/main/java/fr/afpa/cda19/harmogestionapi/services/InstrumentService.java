package fr.afpa.cda19.harmogestionapi.services;

import fr.afpa.cda19.harmogestionapi.dtos.request.InstrumentCreateRequestDTO;
import fr.afpa.cda19.harmogestionapi.dtos.request.InstrumentUpdateRequestDTO;
import fr.afpa.cda19.harmogestionapi.dtos.response.InstrumentResponseDTO;
import fr.afpa.cda19.harmogestionapi.exceptions.IdNonCorrespondantException;
import fr.afpa.cda19.harmogestionapi.exceptions.RessourceDupliqueeException;
import fr.afpa.cda19.harmogestionapi.exceptions.RessourceNonTrouveException;
import fr.afpa.cda19.harmogestionapi.mappers.InstrumentMapper;
import fr.afpa.cda19.harmogestionapi.models.Instrument;
import fr.afpa.cda19.harmogestionapi.repositories.InstrumentRepository;
import fr.afpa.cda19.harmogestionapi.repositories.PratiquerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
     * Repository des instruments.
     */
    private final InstrumentRepository instrumentRepository;

    /**
     * Repository de la pratique des instruments.
     */
    private final PratiquerRepository pratiquerRepository;

    /**
     * Mapper DTO<->entity des instruments.
     */
    private final InstrumentMapper instrumentMapper;

    /**
     * Récupèration d'un instrument par son identifiant.
     *
     * @param id l'identifiant de l'instrument recherché
     * @return ({@link InstrumentResponseDTO}) le dto correspondant à l'instrument recherché
     * @throws RessourceNonTrouveException levée si l'instrument n'existe pas
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
        listeDto.sort(Comparator.comparingInt(InstrumentResponseDTO::id));
        return listeDto;
    }

    /**
     * Suppression d'un instrument par son identifiant.
     *
     * @param id l'identifiant de l'instrument à supprimer
     * @throws RessourceNonTrouveException levée si l'instrument n'existe pas
     */
    @Transactional
    public void deleteInstrument(int id) throws RessourceNonTrouveException {
        if (!instrumentRepository.existsById(id)) {
            throw new RessourceNonTrouveException("Impossible de trouver l'instrument");
        }
        pratiquerRepository.deleteAllByIdInstrument(id);
        instrumentRepository.deleteById(id);
    }

    /**
     * Enregistrement d'un nouvel instrument.
     *
     * @param dto ({@link InstrumentCreateRequestDTO}) le DTO contenant le nom de l'instrument à créer
     * @return ({@link InstrumentResponseDTO}) le DTO correspondant à l'instrument créé
     * @throws RessourceDupliqueeException levée en cas de duplication du nom de l'instrument
     */
    @Transactional
    public InstrumentResponseDTO createInstrument(InstrumentCreateRequestDTO dto)
            throws RessourceDupliqueeException {
        if (instrumentRepository.existsByLibelleInstrument(dto.getNom())) {
            throw new RessourceDupliqueeException("Cet instrument existe déjà");
        }
        Instrument instrument = instrumentMapper.toEntity(dto);
        Instrument savedInstrument = instrumentRepository.save(instrument);
        return instrumentMapper.toDto(savedInstrument);
    }

    /**
     * Mise à jour d'un instrument.
     *
     * @param dto ({@link InstrumentUpdateRequestDTO}) le DTO contenant le nom de l'instrument
     *            à mettre à jour
     * @param id  identifiant de l'instrument à mettre à jour en provenance de l'URL
     * @return ({@link InstrumentResponseDTO}) le DTO correspondant à l'instrument mis à jour
     * @throws RessourceNonTrouveException levée si l'instrument n'existe pas
     * @throws IdNonCorrespondantException levée si l'id du DTO et le paramètre 'id' ne
     *                                     correspondent pas
     * @throws RessourceDupliqueeException levée en cas de duplication du nom de l'instrument
     */
    @Transactional
    public InstrumentResponseDTO updateInstrument(final InstrumentUpdateRequestDTO dto,
                                                  final Integer id)
            throws RessourceNonTrouveException, IdNonCorrespondantException,
            RessourceDupliqueeException {
        if (!dto.getId().equals(id)) {
            throw new IdNonCorrespondantException("Les identifiants ne correspondent pas");
        }
        if (!instrumentRepository.existsById(id)) {
            throw new RessourceNonTrouveException("Impossible de trouver l'instrument");
        }
        if (instrumentRepository.existsByLibelleInstrumentAndIdInstrumentNot(dto.getNom(), id)) {
            throw new RessourceDupliqueeException("Cet instrument existe déjà");
        }
        Instrument instrument = instrumentMapper.toEntity(dto);
        Instrument savedInstrument = instrumentRepository.save(instrument);
        return instrumentMapper.toDto(savedInstrument);
    }
}
