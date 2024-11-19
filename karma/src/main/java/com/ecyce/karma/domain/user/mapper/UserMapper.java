package com.ecyce.karma.domain.user.mapper;

import com.ecyce.karma.domain.user.dto.request.ModifyInfoRequest;
import com.ecyce.karma.domain.user.entity.User;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "nickname", source = "dto.nickname")
    @Mapping(target = "bio", source = "dto.bio")
    @Mapping(target = "phoneNumber", source = "dto.phoneNumber")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(ModifyInfoRequest dto, @MappingTarget User user);



}
