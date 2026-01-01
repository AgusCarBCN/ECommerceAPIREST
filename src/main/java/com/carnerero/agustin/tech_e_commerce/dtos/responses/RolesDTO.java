package com.carnerero.agustin.tech_e_commerce.dtos.responses;

import com.carnerero.agustin.tech_e_commerce.entities.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RolesDTO {
    private Long id;
    private Roles role;
}
