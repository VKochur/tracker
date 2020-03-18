package team.mediasoft.education.tracker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team.mediasoft.education.tracker.dto.TypeDto;
import team.mediasoft.education.tracker.dto.mapper.Mapper;
import team.mediasoft.education.tracker.entity.Type;
import team.mediasoft.education.tracker.exception.SurfaceException;
import team.mediasoft.education.tracker.exception.tree.request.FailForeignConstraintException;
import team.mediasoft.education.tracker.exception.tree.request.NotExistsDataException;
import team.mediasoft.education.tracker.exception.tree.request.NotUniqueDataException;
import team.mediasoft.education.tracker.repository.PackRepository;
import team.mediasoft.education.tracker.repository.TypeRepository;
import team.mediasoft.education.tracker.service.TypeService;
import team.mediasoft.education.tracker.support.Wrap;
import team.mediasoft.education.tracker.support.WrapFactory;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TypeServiceImpl implements TypeService {

    private TypeRepository typeRepository;

    private Mapper<Type, TypeDto> dtoMapper;

    private WrapFactory<TypeDto, SurfaceException> wrapFactory;

    private PackRepository packRepository;

    @Autowired
    public void setWrapFactory(WrapFactory<TypeDto, SurfaceException> wrapFactory) {
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

    @Autowired
    public void setPackRepository(PackRepository packRepository) {
        this.packRepository = packRepository;
    }

    @Override
    public Optional<TypeDto> getById(Long id) {
        Optional<Type> byId = typeRepository.findById(id);
        return dtoMapper.getDto(byId);
    }

    @Override
    public Optional<Long> getIdByNameIgnoreCase(String typeName) {
        return typeRepository.findIdByNameIgnoreCase(typeName);
    }

    @Override
    public Wrap<TypeDto, SurfaceException> create(String typeName) {
        Optional<Long> idByName = this.getIdByNameIgnoreCase(typeName);
        if (!idByName.isPresent()) {
            Type typeForCreation = new Type();
            typeForCreation.setName(typeName);
            Type created = typeRepository.save(typeForCreation);
            return wrapFactory.ofSuccess(dtoMapper.getDto(created));
        } else {
            return wrapFactory.ofFail(new NotUniqueDataException("type \"" + typeName +"\" exists yet (differences may be in case of letters only)"));
        }
    }

    @Transactional
    @Override
    public Wrap<TypeDto, SurfaceException> updateName(Long id, String newName) {
        Optional<Type> withOldNameInDb = typeRepository.findById(id);
        if (withOldNameInDb.isPresent()) {
            Optional<Long> withNewNameInDb = typeRepository.findIdByNameIgnoreCase(newName);
            if (!withNewNameInDb.isPresent() || (withNewNameInDb.get().equals(id))) {
                Type typeInDb = withOldNameInDb.get();
                typeInDb.setName(newName);
                return wrapFactory.ofSuccess(dtoMapper.getDto(typeInDb));
            } else {
                return wrapFactory.ofFail(new NotUniqueDataException("type \"" + newName +"\" exists yet (differences may be in case of letters only)"));
            }
        } else {
            return wrapFactory.ofFail(new NotExistsDataException("not found type by id = " + id));
        }
    }

    @Override
    public Wrap<TypeDto, SurfaceException> deleteById(Long id) {
        SurfaceException constraintException = checkDeleteAbility(id);
        if (constraintException == null) {
            Optional<Type> forDelete = typeRepository.findById(id);
            //delete
            typeRepository.deleteById(id);
            return wrapFactory.ofSuccess(dtoMapper.getDto(forDelete.get()));
        } else {
            return wrapFactory.ofFail(constraintException);
        }
    }

    /**
     *
     * Checks exists entity, relations to others entities and other
     * @param id
     * @return if null, it is ok, or exception
     */
    private SurfaceException checkDeleteAbility(Long id) {

        Optional<Type> type = typeRepository.findById(id);
        //check exists
        if (!type.isPresent()) {
            return new NotExistsDataException("not found type by id = " + id);
        }

        //check fk constrain
        if (packRepository.existsPackByType(type.get())) {
            return new FailForeignConstraintException("related packs existed");
        }

        return null;
    }

    @Override
    public List<TypeDto> getAllTypesOrderByName(boolean asc) {
        List<Type> types = typeRepository.findAll(Sort.by(new Sort.Order((asc) ? Sort.Direction.ASC : Sort.Direction.DESC, "name")));
        return dtoMapper.getListDto(types);
    }
}
