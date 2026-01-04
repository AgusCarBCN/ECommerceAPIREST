package com.carnerero.agustin.ecommerceapplication.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;


import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageResponse<T> {

    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private Sort sort;

}

