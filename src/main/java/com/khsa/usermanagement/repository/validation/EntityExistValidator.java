package com.khsa.usermanagement.repository.validation;

import com.khsa.usermanagement.domain.model.Role;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class EntityExistValidator implements ConstraintValidator<EntityExist, List<Role>> {

    private Role wrongRole;
    private String messageTemplate;

    @Override
    public void initialize(EntityExist constraintAnnotation) {
        this.messageTemplate = constraintAnnotation.message();
        this.wrongRole = new Role();
    }

    @Override
    public boolean isValid(List<Role> value, ConstraintValidatorContext context) {
        boolean match = value.stream()
                .anyMatch(role -> role.equals(wrongRole));
        if (match) {
            context.buildConstraintViolationWithTemplate(messageTemplate);
        }
        return !match;
    }
}
