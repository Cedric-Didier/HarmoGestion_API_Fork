package fr.afpa.cda19.harmogestionapi.mappers;

import fr.afpa.cda19.harmogestionapi.dtos.request.InstrumentCreateRequestDTO;
import fr.afpa.cda19.harmogestionapi.dtos.request.InstrumentUpdateRequestDTO;
import fr.afpa.cda19.harmogestionapi.dtos.response.InstrumentResponseDTO;
import fr.afpa.cda19.harmogestionapi.models.Instrument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InstrumentMapper {
    @Mapping(source = "idInstrument", target = "id")
    @Mapping(source = "libelleInstrument", target = "nom")
    InstrumentResponseDTO toDto(Instrument instrument);

    @Mapping(target = "idInstrument", ignore = true)
    @Mapping(source = "nom", target = "libelleInstrument")
    Instrument toEntity(InstrumentCreateRequestDTO dto);

    @Mapping(source = "id", target = "idInstrument")
    @Mapping(source = "nom", target = "libelleInstrument")
    Instrument toEntity(InstrumentUpdateRequestDTO dto);
}
