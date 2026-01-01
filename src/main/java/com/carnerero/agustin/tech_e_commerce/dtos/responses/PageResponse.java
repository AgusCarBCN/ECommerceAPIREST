package com.carnerero.agustin.tech_e_commerce.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Sort;


import java.util.List;

@Data
@AllArgsConstructor
public class PageResponse<T> {

    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private Sort sort;

}

