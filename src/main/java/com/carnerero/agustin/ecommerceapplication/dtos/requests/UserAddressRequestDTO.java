package com.carnerero.agustin.ecommerceapplication.dtos.requests;

import com.carnerero.agustin.ecommerceapplication.model.enums.AddressType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAddressRequestDTO {
    @NotBlank
    @Size(max = 150)
    private String street;

    @NotBlank
    @Size(max = 80)
    private String city;

    @Size(max = 80)
    private String state;

    @NotBlank
    @Size(max = 20)
    private String postalCode;

    @NotBlank
    @Size(max = 50)
    private String country;

    @NotNull
    private AddressType addressType;

    @NotNull
    @Builder.Default
    private Boolean isDefault = false;


}
