package com.carnerero.agustin.tech_e_commerce.services.interfaces;


import com.carnerero.agustin.tech_e_commerce.dtos.responses.PageResponse;

public interface IReadAllService<T> {
    PageResponse<T> getAll(String field, Boolean desc, Integer page);
}
