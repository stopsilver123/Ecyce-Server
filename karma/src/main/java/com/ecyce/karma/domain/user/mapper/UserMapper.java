package com.ecyce.karma.domain.user.mapper;

import com.ecyce.karma.domain.user.dto.request.ModifyInfoRequest;
import com.ecyce.karma.domain.user.entity.User;
import com.ecyce.karma.global.config.JsonNullableMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.openapitools.jackson.nullable.JsonNullable;

@Mapper(uses = JsonNullableMapper.class ,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring"
)
public interface UserMapper {

    @Mapping(target = "name", qualifiedByName = "unwrap")
    @Mapping(target = "nickname", qualifiedByName = "unwrap")
    @Mapping(target = "bio", qualifiedByName = "unwrap")
    @Mapping(target = "phoneNumber", qualifiedByName = "unwrap")
    void update(ModifyInfoRequest entity, @MappingTarget User destination);


}
