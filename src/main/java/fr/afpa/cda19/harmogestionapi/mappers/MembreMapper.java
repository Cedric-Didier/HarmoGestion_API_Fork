package fr.afpa.cda19.harmogestionapi.mappers;

import fr.afpa.cda19.harmogestionapi.dtos.request.MembreCreateRequestDTO;
import fr.afpa.cda19.harmogestionapi.dtos.request.MembreUpdateRequestDTO;
import fr.afpa.cda19.harmogestionapi.dtos.response.InstrumentPratiqueResponseDTO;
import fr.afpa.cda19.harmogestionapi.dtos.response.MembreResponseDTO;
import fr.afpa.cda19.harmogestionapi.dtos.response.MembreShortResponseDTO;
import fr.afpa.cda19.harmogestionapi.models.Membre;
import fr.afpa.cda19.harmogestionapi.models.Pratiquer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MembreMapper {

    @Mapping(source = "instrument.idInstrument", target = "id")
    @Mapping(source = "instrument.libelleInstrument", target = "nom")
    @Mapping(source = "apprentissageEnCours", target = "apprentissageEnCours")
    InstrumentPratiqueResponseDTO toInstrumentDto(Pratiquer pratiquer);

    @Mapping(source = "idMembre", target = "id")
    @Mapping(source = "nomMembre", target = "nom")
    @Mapping(source = "prenomMembre", target = "prenom")
    @Mapping(source = "dateInscriptionMembre", target = "dateInscription")
    @Mapping(source = "pratiques", target = "instruments")
    MembreResponseDTO toDto(Membre membre);

    @Mapping(source = "idMembre", target = "id")
    @Mapping(source = "nomMembre", target = "nom")
    @Mapping(source = "prenomMembre", target = "prenom")
    @Mapping(source = "dateInscriptionMembre", target = "dateInscription")
    MembreShortResponseDTO toShortDto(Membre membre);

    @Mapping(target = "idMembre", ignore = true)
    @Mapping(target = "pratiques", ignore = true)
    @Mapping(source = "nom", target = "nomMembre")
    @Mapping(source = "prenom", target = "prenomMembre")
    @Mapping(source = "dateInscription", target = "dateInscriptionMembre")
    Membre toEntity(MembreCreateRequestDTO dto);

    @Mapping(target = "pratiques", ignore = true)
    @Mapping(source = "id", target = "idMembre")
    @Mapping(source = "nom", target = "nomMembre")
    @Mapping(source = "prenom", target = "prenomMembre")
    @Mapping(source = "dateInscription", target = "dateInscriptionMembre")
    Membre toEntity(MembreUpdateRequestDTO dto);
}
