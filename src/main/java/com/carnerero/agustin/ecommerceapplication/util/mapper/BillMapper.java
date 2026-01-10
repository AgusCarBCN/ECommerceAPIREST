package com.carnerero.agustin.ecommerceapplication.util.mapper;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.BillResponseDTO;
import com.carnerero.agustin.ecommerceapplication.dtos.responses.ProductResponseDTO;
import com.carnerero.agustin.ecommerceapplication.model.entities.BillEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class BillMapper {

    private ProductCatalogMapper productCatalogMapper;

    public  BillResponseDTO toBillResponseDTO(BillEntity billEntity) {


        if (billEntity == null) return null;

        BillResponseDTO dto = new BillResponseDTO();
        dto.setId(billEntity.getId());
        dto.setTotalAmount(billEntity.getTotalAmount());
        dto.setTaxAmount(billEntity.getTaxAmount());
        dto.setShippingAmount(billEntity.getShippingAmount());
        dto.setIssuedAt(billEntity.getIssuedAt());
        dto.setStatus(billEntity.getStatus());

        // Mapear productos
        if (billEntity.getOrder().getProducts() != null) {
            List<ProductResponseDTO> products = billEntity.getOrder().getProducts().stream()
                    .map(item -> ProductResponseDTO.builder()
                            .subtotalAmount(item.getSubtotalAmount())
                            .quantity(item.getQuantity())
                            .productCatalog(productCatalogMapper.toProductCatalogResponseDTO(item.getProductCatalog()))
                            .build())
                    .toList();
            dto.setProducts(products);
        } else {
            dto.setProducts(List.of());
        }
        return dto;
    }

}
