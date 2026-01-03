package com.carnerero.agustin.ecommerceapplication.util.helper;


import java.util.Set;

public final class Sort {

    private Sort() {} // evita instanciación

    public static final int PAGE_SIZE = 5;

    private static Set<String> allowedUserFields = Set.of(
            "userName",
            "email",
            "createdAt",
            "updatedAt",
            "status"
    );

    public static org.springframework.data.domain.Sort getSort(String field,
                                                               Boolean desc,
                                                               Set<String> allowedFields) {
        if (allowedFields.contains(field)) {
            return desc? org.springframework.data.domain.Sort.by(field).descending(): org.springframework.data.domain.Sort.by(field).ascending();
        }else{
             throw new IllegalArgumentException("Invalid field");
        }
    }
}
