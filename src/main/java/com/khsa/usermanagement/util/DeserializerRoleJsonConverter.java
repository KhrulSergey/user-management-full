package com.khsa.usermanagement.util;

import com.fasterxml.jackson.databind.util.StdConverter;
import com.khsa.usermanagement.domain.model.Role;
import com.khsa.usermanagement.repository.RoleRepository;


/**
 * Конвертер для получения списка идентификаторов сущности "Роль"
 */
public class DeserializerRoleJsonConverter extends StdConverter<Long, Role> {

    private RoleRepository roleRepository;

    public DeserializerRoleJsonConverter(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role convert(Long value) {
        return roleRepository.findById(value).orElse(new Role());
    }
}
