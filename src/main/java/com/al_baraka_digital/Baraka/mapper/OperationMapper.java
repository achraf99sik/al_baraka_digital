package com.al_baraka_digital.Baraka.mapper;

import com.al_baraka_digital.Baraka.dto.OperationResponse;
import com.al_baraka_digital.Baraka.model.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OperationMapper {

    @Mapping(source = "accountSource.accountNumber", target = "sourceAccountNumber")
    @Mapping(source = "accountDestination.accountNumber", target = "destinationAccountNumber")
    OperationResponse toResponse(Operation operation);
}
