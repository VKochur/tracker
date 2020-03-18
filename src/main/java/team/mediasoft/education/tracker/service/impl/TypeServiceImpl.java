package team.mediasoft.education.tracker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team.mediasoft.education.tracker.dto.TypeInput;
import team.mediasoft.education.tracker.dto.TypeOutput;
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

    private WrapFactory<Type, SurfaceException> wrapFactory;

    private PackRepository packRepository;

    private Mapper<Type, TypeOutput, TypeInput> mapper;

    @Autowired
    public void setWrapFactory(WrapFactory<Type, SurfaceException> wrapFactory) {
        this.wrapFactory = wrapFactory;
    }

    @Autowired
    public void setTypeRepository(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Autowired
    public void setPackRepository(PackRepository packRepository) {
        this.packRepository = packRepository;
    }

    @Autowired
    public void setMapper(Mapper<Type, TypeOutput, TypeInput> mapper) {
        this.mapper = mapper;
    }

    @Override
    public Optional<Type> getById(Long id) {
        return typeRepository.findById(id);
    }

    @Override
    public Optional<Long> getIdByNameIgnoreCase(String typeName) {
        return typeRepository.findIdByNameIgnoreCase(typeName);
    }

    @Override
    public Wrap<Type, SurfaceException> create(TypeInput forCreation) {
        SurfaceException exception = checkCreateAbility(forCreation);
        if (exception != null) {
            return wrapFactory.ofFail(exception);
        } else {
            Type forSave = mapper.getForCreation(forCreation);
            return wrapFactory.ofSuccess(typeRepository.save(forSave));
        }
    }

    private SurfaceException checkCreateAbility(TypeInput forCreation) {
        String typeName = forCreation.getName();
        Optional<Long> idByName = this.getIdByNameIgnoreCase(typeName);
        if (idByName.isPresent()) {
            return new NotUniqueDataException("type \"" + typeName +"\" exists yet (differences may be in case of letters only)");
        }

        return null;
    }

    @Transactional
    @Override
    public Wrap<Type, SurfaceException> updateName(Long id, String newName) {
        Optional<Type> withOldNameInDb = typeRepository.findById(id);
        if (withOldNameInDb.isPresent()) {
            Optional<Long> withNewNameInDb = typeRepository.findIdByNameIgnoreCase(newName);
            if (!withNewNameInDb.isPresent() || (withNewNameInDb.get().equals(id))) {
                Type typeInDb = withOldNameInDb.get();
                typeInDb.setName(newName);
                return wrapFactory.ofSuccess(typeInDb);
            } else {
                return wrapFactory.ofFail(new NotUniqueDataException("type \"" + newName +"\" exists yet (differences may be in case of letters only)"));
            }
        } else {
            return wrapFactory.ofFail(new NotExistsDataException("not found type by id = " + id));
        }
    }

    @Override
    public Wrap<Type, SurfaceException> deleteById(Long id) {
        SurfaceException constraintException = checkDeleteAbility(id);
        if (constraintException == null) {
            Optional<Type> forDelete = typeRepository.findById(id);
            //delete
            typeRepository.deleteById(id);
            return wrapFactory.ofSuccess(forDelete.get());
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
    public List<Type> getAllTypesOrderByName(boolean asc) {
        return typeRepository.findAll(Sort.by(new Sort.Order((asc) ? Sort.Direction.ASC : Sort.Direction.DESC, "name")));
    }
}
