package com.honey.menu_management.converter;

import com.honey.menu_management.entity.Authority;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Converter(autoApply = true) // 자동으로 모든 엔티티에 사용.
public class AuthorityConverter implements AttributeConverter<Set<Authority>, String> {

    private static final String DELIMITER = ",";


    @Override
    public String convertToDatabaseColumn(Set<Authority> authorities) {
        return authorities.stream()
                .map(Authority::name)
                .sorted()
                .collect(Collectors.joining(DELIMITER));
    }

    @Override
    public Set<Authority> convertToEntityAttribute(String dbData) {
        return Arrays.stream(dbData.split(DELIMITER))
                .map(Authority::valueOf)
                .collect(Collectors.toSet());
    }
}
