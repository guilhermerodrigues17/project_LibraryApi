package study.guilhermerodrigues17.study_libraryapi.controller.mappers;

import org.mapstruct.Mapper;
import study.guilhermerodrigues17.study_libraryapi.controller.dto.ClientDTO;
import study.guilhermerodrigues17.study_libraryapi.model.Client;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client toEntity(ClientDTO dto);

    ClientDTO toDTO(Client client);
}
