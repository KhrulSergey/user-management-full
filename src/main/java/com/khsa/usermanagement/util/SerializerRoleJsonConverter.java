package com.khsa.usermanagement.util;


import com.fasterxml.jackson.databind.util.StdConverter;
import com.khsa.usermanagement.domain.model.Role;


/**
 * Конвертер-обертка для сущности Роль
 */
public class SerializerRoleJsonConverter extends StdConverter<Role, Long> {

    @Override
    public Long convert(Role value) {
        return value.getId();
    }
}
