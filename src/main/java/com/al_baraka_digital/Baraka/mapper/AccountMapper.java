package com.al_baraka_digital.Baraka.mapper;

import com.al_baraka_digital.Baraka.dto.AccountDTO;
import com.al_baraka_digital.Baraka.model.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountDTO toDTO(Account account);
}
