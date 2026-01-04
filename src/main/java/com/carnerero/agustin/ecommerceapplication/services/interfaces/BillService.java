package com.carnerero.agustin.ecommerceapplication.services.interfaces;

import com.carnerero.agustin.ecommerceapplication.dtos.responses.BillResponseDTO;

import java.util.UUID;

public interface BillService {

    /**
     * Retrieve a bill by its ID.
     * @param billId UUID of the bill
     * @return BillResponseDTO with details
     */
    BillResponseDTO getBillById(UUID billId);

    /**
     * Retrieve the bill associated with a specific order.
     * @param orderId ID of the order
     * @return BillResponseDTO
     */
    BillResponseDTO getBillByOrderId(Long orderId);

    /**
     * Optionally generate a bill for a completed order.
     * @param orderId ID of the order
     * @return created BillDTO
     */
    BillResponseDTO generateBillForOrder(Long orderId);

}

