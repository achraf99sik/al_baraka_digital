package com.al_baraka_digital.Baraka.mapper;

import com.al_baraka_digital.Baraka.dto.UserDTO;
import com.al_baraka_digital.Baraka.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(User user);
}
