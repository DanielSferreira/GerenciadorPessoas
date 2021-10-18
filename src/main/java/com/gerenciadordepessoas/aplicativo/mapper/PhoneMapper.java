package com.gerenciadordepessoas.aplicativo.mapper;

import com.gerenciadordepessoas.aplicativo.dto.request.PersonDTO;
import com.gerenciadordepessoas.aplicativo.dto.request.PhoneDTO;
import com.gerenciadordepessoas.aplicativo.entity.Person;
import com.gerenciadordepessoas.aplicativo.entity.Phone;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PhoneMapper {

    PhoneMapper INSTANCE = Mappers.getMapper(PhoneMapper.class);

    Phone toModel(PhoneDTO pDTO);

    PhoneDTO toDTO(Phone p);
}
