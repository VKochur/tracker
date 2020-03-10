package team.mediasoft.education.tracker.dto.mapper.impl;

import org.springframework.stereotype.Component;
import team.mediasoft.education.tracker.dto.TypeDto;
import team.mediasoft.education.tracker.dto.mapper.Mapper;
import team.mediasoft.education.tracker.entity.Type;

@Component
public class TypeMapper implements Mapper<Type, TypeDto> {

    @Override
    public TypeDto getDto(Type entity) {
        TypeDto typeDto = new TypeDto();
        typeDto.setId(entity.getId());
        typeDto.setName(entity.getName());
        return typeDto;
    }

    @Override
    public Type getEntity(TypeDto dto) {
        Type type = new Type();
        type.setId(dto.getId());
        type.setName(dto.getName());
        return type;
    }
}
