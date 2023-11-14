package mskory.bookstore.service.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import mskory.bookstore.model.RoleName;

@Converter(autoApply = true)
public class RoleNameConverter implements AttributeConverter<RoleName, String> {
    @Override
    public String convertToDatabaseColumn(RoleName attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.name();
    }

    @Override
    public RoleName convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return RoleName.valueOf(dbData);
    }
}
