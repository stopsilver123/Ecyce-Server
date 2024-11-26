package com.ecyce.karma.global.config;

import org.mapstruct.Condition;
import org.mapstruct.Named;
import org.mapstruct.Mapper;
import org.openapitools.jackson.nullable.JsonNullable;

@Mapper(componentModel = "spring")
public interface JsonNullableMapper {

    default <T> JsonNullable<T> wrap(T entity) {
        return JsonNullable.of(entity);
    }

    @Named("unwrap")
    default <T> T unwrap(JsonNullable<T> jsonNullable, T currentValue) {
        // JsonNullable이 null인 경우 기존 값 유지
        if (jsonNullable == null) {
            return currentValue;
        }
        // JsonNullable 값이 비어 있으면 기존 값 유지
        return jsonNullable.orElse(currentValue);
    }

    /**
     * Checks whether nullable parameter was passed explicitly.
     * @return true if value was set explicitly, false otherwise
     */
    @Condition
    default <T> boolean isPresent(JsonNullable<T> nullable) {
        return nullable != null && nullable.isPresent();
    }
}