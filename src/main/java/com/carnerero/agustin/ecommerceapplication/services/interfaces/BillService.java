package com.carnerero.agustin.ecommerceapplication.services.interfaces;

import com.carnerero.agustin.ecommerceapplication.dtos.responses.BillResponseDTO;

import java.util.UUID;

public interface BillService {



    /**
     * Retrieve the bill associated with a specific order.
     * @param email the email of the
     * @return BillResponseDTO
     */
    BillResponseDTO getBillByOrderId(String email,Long orderId);



}

