package team.mediasoft.education.tracker.dto.mapper.impl;

import org.springframework.stereotype.Component;
import team.mediasoft.education.tracker.dto.TypeInput;
import team.mediasoft.education.tracker.dto.TypeOutput;
import team.mediasoft.education.tracker.dto.mapper.Mapper;
import team.mediasoft.education.tracker.entity.Type;

@Component
public class TypeMapper implements Mapper<Type, TypeOutput, TypeInput> {

    @Override
    public Type getForCreation(TypeInput entityInput) {
        Type type = new Type();
        type.setName(entityInput.getName());
        return type;
    }

    @Override
    public TypeOutput getOutput(Type entity) {
        TypeOutput typeOutput = new TypeOutput();
        typeOutput.setId(entity.getId());
        typeOutput.setName(entity.getName());
        return typeOutput;
    }
}
