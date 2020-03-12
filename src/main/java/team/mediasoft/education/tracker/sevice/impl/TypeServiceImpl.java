package team.mediasoft.education.tracker.sevice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team.mediasoft.education.tracker.dto.TypeDto;
import team.mediasoft.education.tracker.dto.mapper.Mapper;
import team.mediasoft.education.tracker.entity.Type;
import team.mediasoft.education.tracker.exception.tree.request.NotExistsDataException;
import team.mediasoft.education.tracker.exception.tree.request.NotUniqueDataException;
import team.mediasoft.education.tracker.exception.tree.request.WrongInputDataException;
import team.mediasoft.education.tracker.repository.TypeRepository;
import team.mediasoft.education.tracker.sevice.TypeService;
import team.mediasoft.education.tracker.support.Wrap;
import team.mediasoft.education.tracker.support.WrapFactory;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TypeServiceImpl implements TypeService {

    private TypeRepository typeRepository;

    private Mapper<Type, TypeDto> dtoMapper;

    private WrapFactory<TypeDto, WrongInputDataException> wrapFactory;

    @Autowired
    public void setWrapFactory(WrapFactory<TypeDto, WrongInputDataException> wrapFactory) {
        this.wrapFactory = wrapFactory;
    }

    @Autowired
    public void setDtoMapper(Mapper<Type, TypeDto> dtoMapper) {
        this.dtoMapper = dtoMapper;
    }

    @Autowired
    public void setTypeRepository(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Override
    public Optional<TypeDto> getById(Long id) {
        Optional<Type> byId = typeRepository.findById(id);
        return dtoMapper.getDto(byId);
    }

    @Override
    public boolean isExisted(Long id) {
        return typeRepository.existsById(id);
    }

    @Override
    public Optional<TypeDto> getByNameIgnoreCase(String typeName) {
        Optional<Type> byNameIgnoreCase = typeRepository.findByNameIgnoreCase(typeName);
        return dtoMapper.getDto(byNameIgnoreCase);
    }

    @Override
    public Wrap<TypeDto, WrongInputDataException> create(String typeName) {
        Optional<Type> byNameIgnoreCase = typeRepository.findByNameIgnoreCase(typeName);
        if (!byNameIgnoreCase.isPresent()) {
            Type typeForCreation = new Type();
            typeForCreation.setName(typeName);
            Type created = typeRepository.save(typeForCreation);
            return wrapFactory.ofSuccess(dtoMapper.getDto(created));
        } else {
            return wrapFactory.ofFail(new NotUniqueDataException("type \"" + byNameIgnoreCase.get().getName() +"\" exists yet"));
        }
    }

    @Transactional
    @Override
    public Wrap<TypeDto, WrongInputDataException> updateName(String oldName, String newName) {
        Optional<Type> withOldNameInDb = typeRepository.findByNameIgnoreCase(oldName);
        if (withOldNameInDb.isPresent()) {
            Optional<Type> withNewNameInDb = typeRepository.findByNameIgnoreCase(newName);
            if (!withNewNameInDb.isPresent()) {
                Type typeInDb = withOldNameInDb.get();
                typeInDb.setName(newName);
                return wrapFactory.ofSuccess(dtoMapper.getDto(typeInDb));
            } else {
                return wrapFactory.ofFail(new NotUniqueDataException("type \"" + withNewNameInDb.get().getName() +"\" exists yet"));
            }
        } else {
            return wrapFactory.ofFail(new NotExistsDataException("not found type by name = \"" + oldName +"\""));
        }
    }

    @Override
    public Wrap<TypeDto, WrongInputDataException> deleteByName(String typeName) {
        Optional<Type> byNameIgnoreCase = typeRepository.findByNameIgnoreCase(typeName);
        if (byNameIgnoreCase.isPresent()) {
            //todo: catch exception if fk constrain fails
            typeRepository.delete(byNameIgnoreCase.get());
            return wrapFactory.ofSuccess(dtoMapper.getDto(byNameIgnoreCase.get()));
        } else {
            return wrapFactory.ofFail(new NotExistsDataException("not found type by name = \"" + typeName +"\""));
        }
    }

    @Override
    public List<TypeDto> getAllOrderByName(boolean asc) {
        List<Type> types = typeRepository.findAll(Sort.by(new Sort.Order((asc) ? Sort.Direction.ASC : Sort.Direction.DESC, "name")));
        return dtoMapper.getListDto(types);
    }
}
