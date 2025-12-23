package com.al_baraka_digital.Baraka.mapper;

import com.al_baraka_digital.Baraka.dto.UserResponse;
import com.al_baraka_digital.Baraka.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "fullName")
    UserResponse toResponse(User user);
}
