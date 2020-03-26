package team.mediasoft.education.tracker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import team.mediasoft.education.tracker.dto.TypeInput;
import team.mediasoft.education.tracker.entity.Type;
import team.mediasoft.education.tracker.exception.SurfaceException;
import team.mediasoft.education.tracker.exception.tree.request.NotExistsDataException;
import team.mediasoft.education.tracker.exception.tree.request.NotUniqueDataException;
import team.mediasoft.education.tracker.repository.TypeRepository;
import team.mediasoft.education.tracker.service.TypeService;
import team.mediasoft.education.tracker.service.impl.verification.Decider;
import team.mediasoft.education.tracker.service.impl.verification.TestResultSolver;
import team.mediasoft.education.tracker.service.impl.verification.impl.ExistTypeByIdTester;
import team.mediasoft.education.tracker.service.impl.verification.impl.NotExistRelatedPack;
import team.mediasoft.education.tracker.service.impl.verification.impl.UniqueNameTypeTester;
import team.mediasoft.education.tracker.support.Wrap;
import team.mediasoft.education.tracker.support.WrapFactory;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class TypeServiceImpl implements TypeService {

    private TypeRepository typeRepository;

    private WrapFactory<Type, SurfaceException> wrapFactory;

    private TestResultSolver<SurfaceException> solver;

    private Decider<SurfaceException> decider;

    @Override
    public Type getEntityForCreationByInput(TypeInput dtoInput) {
        Type type = new Type();
        type.setName(dtoInput.getName());
        return type;
    }

    @Override
    public SurfaceException checkCreateAbility(TypeInput forCreation) {
        return solver.solve(UniqueNameTypeTester.class, forCreation.getName());
    }

    /**
     * Checks exists entity, relations to others entities and other
     *
     * @param id
     * @return if null, it is ok, or exception
     */
    @Override
    public SurfaceException checkDeleteAbility(Long id) {
        List<SurfaceException> exceptionList = Arrays.asList(
                solver.solve(ExistTypeByIdTester.class, id),
                solver.solve(NotExistRelatedPack.class, id)
        );
        return decider.decide(exceptionList);
    }

    @Override
    public Optional<Long> getIdByNameIgnoreCase(String typeName) {
        return typeRepository.findIdByNameIgnoreCase(typeName);
    }

    @Transactional
    @Override
    public Wrap<Type, SurfaceException> updateName(Long id, String newName) {
        Optional<Type> withOldNameInDb = typeRepository.findById(id);
        if (withOldNameInDb.isPresent()) {
            Optional<Long> withNewNameInDb = typeRepository.findIdByNameIgnoreCase(newName);
            //if other type doesn't exist with new name, or we are renaming the same type
            if (!withNewNameInDb.isPresent() || (withNewNameInDb.get().equals(id))) {
                Type typeInDb = withOldNameInDb.get();
                typeInDb.setName(newName);
                return wrapFactory.ofSuccess(typeInDb);
            } else {
                return wrapFactory.ofFail(new NotUniqueDataException("type \"" + newName + "\" exists yet (differences may be in case of letters only)"));
            }
        } else {
            return wrapFactory.ofFail(new NotExistsDataException("not found type by id = " + id));
        }
    }

    @Override
    public List<Type> getAllTypesOrderByName(boolean asc) {
        return typeRepository.findAll(Sort.by(new Sort.Order((asc) ? Sort.Direction.ASC : Sort.Direction.DESC, "name")));
    }

    @Autowired
    public void setSolver(TestResultSolver<SurfaceException> solver) {
        this.solver = solver;
    }

    @Autowired
    public void setDecider(Decider<SurfaceException> decider) {
        this.decider = decider;
    }

    @Autowired
    public void setWrapFactory(WrapFactory<Type, SurfaceException> wrapFactory) {
        this.wrapFactory = wrapFactory;
    }

    @Autowired
    public void setTypeRepository(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Override
    public WrapFactory<Type, SurfaceException> wrapFactoryForCreator() {
        return wrapFactory;
    }

    @Override
    public JpaRepository<Type, Long> jpaRepositoryForCreator() {
        return typeRepository;
    }

    @Override
    public WrapFactory<Type, SurfaceException> wrapFactoryForDeleter() {
        return wrapFactory;
    }

    @Override
    public JpaRepository<Type, Long> jpaRepositoryForDeleter() {
        return typeRepository;
    }

    @Override
    public JpaRepository<Type, Long> jpaRepositoryForFinder() {
        return typeRepository;
    }
}
