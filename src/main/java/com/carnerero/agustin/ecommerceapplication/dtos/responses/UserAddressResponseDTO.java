package com.carnerero.agustin.ecommerceapplication.dtos.responses;

import com.carnerero.agustin.ecommerceapplication.model.enums.AddressType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAddressResponseDTO {

    private Long id;

    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private AddressType addressType;
    private Boolean isDefault;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}

