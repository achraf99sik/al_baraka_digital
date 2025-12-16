package com.al_baraka_digital.Baraka.mapper;

import com.al_baraka_digital.Baraka.dto.CreateOperationRequestDTO;
import com.al_baraka_digital.Baraka.dto.OperationDTO;
import com.al_baraka_digital.Baraka.model.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OperationMapper {

    @Mapping(source = "accountSource.id", target = "accountSourceId")
    @Mapping(source = "accountDestination.id", target = "accountDestinationId")
    OperationDTO toDTO(Operation operation);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "validatedAt", ignore = true)
    @Mapping(target = "executedAt", ignore = true)
    @Mapping(target = "accountSource", ignore = true)
    @Mapping(target = "accountDestination", ignore = true)
    @Mapping(target = "document", ignore = true)
    Operation fromCreateRequest(CreateOperationRequestDTO dto);
}
