package team.mediasoft.education.tracker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import team.mediasoft.education.tracker.dto.PackInput;
import team.mediasoft.education.tracker.entity.Pack;
import team.mediasoft.education.tracker.entity.support.PackStates;
import team.mediasoft.education.tracker.exception.SurfaceException;
import team.mediasoft.education.tracker.exception.tree.inner.NotSupportedException;
import team.mediasoft.education.tracker.repository.NodeRepository;
import team.mediasoft.education.tracker.repository.PackRepository;
import team.mediasoft.education.tracker.repository.TypeRepository;
import team.mediasoft.education.tracker.service.PackService;
import team.mediasoft.education.tracker.service.impl.verification.Decider;
import team.mediasoft.education.tracker.service.impl.verification.TestResultSolver;
import team.mediasoft.education.tracker.service.impl.verification.impl.ExistNodeByIdTester;
import team.mediasoft.education.tracker.service.impl.verification.impl.ExistPackByIdTester;
import team.mediasoft.education.tracker.service.impl.verification.impl.ExistTypeByIdTester;
import team.mediasoft.education.tracker.service.impl.verification.impl.UniqueIdentifierPackTester;
import team.mediasoft.education.tracker.support.Wrap;
import team.mediasoft.education.tracker.support.WrapFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PackServiceImpl implements PackService {

    private WrapFactory<Pack, SurfaceException> wrapFactory;

    private PackRepository packRepository;

    private NodeRepository nodeRepository;

    private TypeRepository typeRepository;

    private TestResultSolver<SurfaceException> testResultSolver;

    private Decider<SurfaceException> decider;

    @Override
    public Optional<Pack> getByIdentifier(String identifier) {
        return packRepository.findByIdentifier(identifier);
    }

    @Override
    public Pack getEntityForCreationByInput(PackInput dtoInput) {
        Pack forCreation = new Pack();
        forCreation.setIdentifier(dtoInput.getIdentifier());
        forCreation.setRecipient(dtoInput.getRecipient());
        forCreation.setType(typeRepository.findById(dtoInput.getTypeId()).get());
        forCreation.setState(stateForCreated());
        forCreation.setDestination(nodeRepository.findById(dtoInput.getNodeId()).get());
        return packRepository.save(forCreation);
    }

    private PackStates stateForCreated() {
        return PackStates.STORAGE;
    }

    @Override
    public SurfaceException checkCreateAbility(PackInput dtoInput) {
        List<SurfaceException> exceptionList = Arrays.asList(
                testResultSolver.solve(UniqueIdentifierPackTester.class, dtoInput.getIdentifier()),
                testResultSolver.solve(ExistNodeByIdTester.class, dtoInput.getNodeId()),
                testResultSolver.solve(ExistTypeByIdTester.class, dtoInput.getTypeId())
        );

        return decider.decide(exceptionList);
    }

    @Override
    public SurfaceException checkDeleteAbility(Long id) {
        return testResultSolver.solve(ExistPackByIdTester.class, id);
    }

    @Autowired
    public void setTestResultSolver(TestResultSolver<SurfaceException> testResultSolver) {
        this.testResultSolver = testResultSolver;
    }

    @Autowired
    public void setDecider(Decider<SurfaceException> decider) {
        this.decider = decider;
    }

    @Autowired
    public void setNodeRepository(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    @Autowired
    public void setWrapFactory(WrapFactory<Pack, SurfaceException> wrapFactory) {
        this.wrapFactory = wrapFactory;
    }

    @Autowired
    public void setPackRepository(PackRepository packRepository) {
        this.packRepository = packRepository;
    }

    @Autowired
    public void setTypeRepository(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Override
    public WrapFactory<Pack, SurfaceException> wrapFactory() {
        return wrapFactory;
    }

    @Override
    public JpaRepository<Pack, Long> jpaRepository() {
        return packRepository;
    }

}
