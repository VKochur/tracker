package team.mediasoft.education.tracker.sevice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import team.mediasoft.education.tracker.dto.TypeDto;
import team.mediasoft.education.tracker.dto.mapper.Mapper;
import team.mediasoft.education.tracker.entity.Type;
import team.mediasoft.education.tracker.exception.FailForeignConstraintException;
import team.mediasoft.education.tracker.exception.NotExistsDataException;
import team.mediasoft.education.tracker.exception.NotUniqueDataException;
import team.mediasoft.education.tracker.exception.WrongInputDataException;
import team.mediasoft.education.tracker.repository.TypeRepository;
import team.mediasoft.education.tracker.sevice.TypeService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TypeServiceImpl implements TypeService {

    private TypeRepository typeRepository;

    private Mapper<Type, TypeDto> dtoMapper;

    @Autowired
    public void setDtoMapper(Mapper<Type, TypeDto> dtoMapper) {
        this.dtoMapper = dtoMapper;
    }

    @Autowired
    public void setTypeRepository(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Override
    public TypeDto getById(Long id) throws NotExistsDataException {
        //todo: check id != null
        Optional<Type> byId = typeRepository.findById(id);
        if (byId.isPresent()) {
            return dtoMapper.getDto(byId.get());
        } else {
            throw new NotExistsDataException("not found type by id = " + id);
        }
    }

    @Override
    public TypeDto getByNameIgnoreCase(String typeName) throws NotExistsDataException {
        Optional<Type> byNameIgnoreCase = typeRepository.findByNameIgnoreCase(typeName);
        if (byNameIgnoreCase.isPresent()) {
            return dtoMapper.getDto(byNameIgnoreCase.get());
        } else {
            throw new NotExistsDataException("not found type by name = \"" + typeName +"\"");
        }
    }

    @Override
    public TypeDto create(String typeName) throws NotUniqueDataException {
        Optional<Type> byNameIgnoreCase = typeRepository.findByNameIgnoreCase(typeName);
        if (!byNameIgnoreCase.isPresent()) {
            Type typeForCreation = new Type();
            typeForCreation.setName(typeName);
            Type created = typeRepository.save(typeForCreation);
            return dtoMapper.getDto(created);
        } else {
            throw new NotUniqueDataException("type \"" + byNameIgnoreCase.get().getName() +"\" exists yet");
        }
    }

    @Transactional
    @Override
    public TypeDto updateName(String oldName, String newName) throws NotUniqueDataException, NotExistsDataException {
        Optional<Type> withOldNameInDb = typeRepository.findByNameIgnoreCase(oldName);
        if (withOldNameInDb.isPresent()) {
            Optional<Type> withNewNameInDb = typeRepository.findByNameIgnoreCase(newName);
            if (!withNewNameInDb.isPresent()) {
                Type typeInDb = withNewNameInDb.get();
                typeInDb.setName(newName);
                return dtoMapper.getDto(typeInDb);
            } else {
                throw new NotUniqueDataException("type \"" + withNewNameInDb.get().getName() +"\" exists yet");
            }
        } else {
            throw new NotExistsDataException("not found type by name = \"" + oldName +"\"");
        }
    }

    @Override
    public TypeDto deleteByName(String typeName) throws NotExistsDataException, FailForeignConstraintException {
        Optional<Type> byNameIgnoreCase = typeRepository.findByNameIgnoreCase(typeName);
        if (byNameIgnoreCase.isPresent()) {
            //todo: catch exception if fk constrain fails
            typeRepository.delete(byNameIgnoreCase.get());
            return dtoMapper.getDto(byNameIgnoreCase.get());
        } else {
            throw new NotExistsDataException("not found type by name = \"" + typeName +"\"");
        }
    }

    @Override
    public List<TypeDto> getAllOrderByName(boolean asc) {
        List<Type> types = typeRepository.findAll(Sort.by(new Sort.Order((asc) ? Sort.Direction.ASC : Sort.Direction.DESC, "name")));
        return dtoMapper.getListDto(types);
    }
}
