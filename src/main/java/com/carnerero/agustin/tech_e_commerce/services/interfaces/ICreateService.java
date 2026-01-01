package com.carnerero.agustin.tech_e_commerce.services.interfaces;

public interface ICreateService <T,U>{
    U create(T request);
}
