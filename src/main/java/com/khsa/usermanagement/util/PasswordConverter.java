package com.khsa.usermanagement.util;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@PropertySource("classpath:application.properties")
@Converter(autoApply = false)
public class PasswordConverter
        implements AttributeConverter<String, String> {

    @Value("${user.authKey:default_key}")
    private String authKey = "";

    @Override
    public String convertToDatabaseColumn(String value) {
        if (value == null) {
            return null;
        }

        return PasswordHelper.getHashPassword(value, authKey);
    }

    @Override
    public String convertToEntityAttribute(String value) {
        return value;
    }
}
