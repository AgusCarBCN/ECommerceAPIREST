package com.carnerero.agustin.tech_e_commerce.mapper;

import com.carnerero.agustin.tech_e_commerce.dtos.responses.BillResponseDTO;
import com.carnerero.agustin.tech_e_commerce.entities.BillEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BillMapper {

    BillResponseDTO toBillResponseDTO(BillEntity billEntity);

}
