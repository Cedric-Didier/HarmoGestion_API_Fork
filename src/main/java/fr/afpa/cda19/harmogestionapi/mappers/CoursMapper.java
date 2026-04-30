package fr.afpa.cda19.harmogestionapi.mappers;

import fr.afpa.cda19.harmogestionapi.dtos.request.CoursCreateRequestDTO;
import fr.afpa.cda19.harmogestionapi.dtos.request.CoursUpdateRequestDTO;
import fr.afpa.cda19.harmogestionapi.dtos.response.CoursResponseDTO;
import fr.afpa.cda19.harmogestionapi.models.Cours;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {InstrumentMapper.class, MembreMapper.class})
public interface CoursMapper {
    @Mapping(source = "idCours", target = "id")
    @Mapping(source = "dateCours", target = "date")
    @Mapping(source = "dureeCours", target = "duree")
    @Mapping(source = "instrument", target = "instrument")
    @Mapping(source = "enseignant", target = "enseignant")
    @Mapping(source = "participants", target = "participants")
    CoursResponseDTO toDto(Cours cours);

    @Mapping(target = "idCours", ignore = true)
    @Mapping(source = "date", target = "dateCours")
    @Mapping(source = "duree", target = "dureeCours")
    @Mapping(target = "instrument", ignore = true)
    @Mapping(target = "enseignant", ignore = true)
    @Mapping(target = "participants", ignore = true)
    Cours toEntity(CoursCreateRequestDTO dto);

    @Mapping(source = "id", target = "idCours")
    @Mapping(source = "date", target = "dateCours")
    @Mapping(source = "duree", target = "dureeCours")
    @Mapping(target = "instrument", ignore = true)
    @Mapping(target = "enseignant", ignore = true)
    @Mapping(target = "participants", ignore = true)
    Cours toEntity(CoursUpdateRequestDTO dto);
}
