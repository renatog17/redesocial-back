package com.renato.projects.redesocial.domain.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class GenderConverter implements AttributeConverter<Gender, String> {

    @Override
    public String convertToDatabaseColumn(Gender gender) {
        return gender == null ? null : gender.name();
    }

    @Override
    public Gender convertToEntityAttribute(String dbData) {
        return dbData == null ? null : Gender.valueOf(dbData);
    }
}
