package com.carnerero.agustin.ecommerceapplication.util.mapper;

import com.carnerero.agustin.ecommerceapplication.dtos.responses.PageResponse;
import org.springframework.data.domain.Page;

public class PageResponseMapper<T> {

    public static <T> PageResponse<T> mapToPageResponse(Page<T> page) {

        PageResponse<T> response = new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getSort()

        );

        return response;
    }
}
