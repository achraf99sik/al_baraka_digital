package com.al_baraka_digital.Baraka.mapper;

import com.al_baraka_digital.Baraka.dto.DocumentDTO;
import com.al_baraka_digital.Baraka.model.Document;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    DocumentDTO toDTO(Document document);
}
