package com.carnerero.agustin.tech_e_commerce.services.interfaces;

public interface IUpdateService <T,U,S>{
    U update(T request,S id);
}
