package kz.tempest.tpapp.modules.person.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import kz.tempest.tpapp.modules.person.enums.Role;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter(autoApply = true)
public class RolesConverter implements AttributeConverter<List<Role>, String> {

    @Override
    public String convertToDatabaseColumn(List<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            return "";
        }
        return roles.stream()
                .map(Role::name)
                .collect(Collectors.joining(", "));
    }

    @Override
    public List<Role> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return List.of();
        }
        return Arrays.stream(dbData.split(", "))
                .map(Role::valueOf)
                .collect(Collectors.toList());
    }
}

