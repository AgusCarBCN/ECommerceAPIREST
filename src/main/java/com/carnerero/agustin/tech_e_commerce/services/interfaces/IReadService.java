package com.carnerero.agustin.tech_e_commerce.services.interfaces;


public interface IReadService<T,U> {
    T getOne(U id);

}
