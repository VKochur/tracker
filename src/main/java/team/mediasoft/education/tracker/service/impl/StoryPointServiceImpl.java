package team.mediasoft.education.tracker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import team.mediasoft.education.tracker.dto.StoryPointInput;
import team.mediasoft.education.tracker.entity.StoryPoint;
import team.mediasoft.education.tracker.entity.support.PackStates;
import team.mediasoft.education.tracker.exception.SurfaceException;
import team.mediasoft.education.tracker.repository.NodeRepository;
import team.mediasoft.education.tracker.repository.PackRepository;
import team.mediasoft.education.tracker.repository.StoryPointRepository;
import team.mediasoft.education.tracker.service.StoryPointService;
import team.mediasoft.education.tracker.service.basic.CreatorBasicService;
import team.mediasoft.education.tracker.service.impl.verification.Decider;
import team.mediasoft.education.tracker.service.impl.verification.TestResultSolver;
import team.mediasoft.education.tracker.service.impl.verification.impl.ExistNodeByIdTester;
import team.mediasoft.education.tracker.service.impl.verification.impl.ExistPackByIdTester;
import team.mediasoft.education.tracker.support.Wrap;
import team.mediasoft.education.tracker.support.WrapFactory;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class StoryPointServiceImpl implements StoryPointService {

    private StoryPointRepository storyPointRepository;

    private NodeRepository nodeRepository;

    private PackRepository packRepository;

    private WrapFactory<StoryPoint, SurfaceException> wrapFactory;

    private TestResultSolver<SurfaceException> solver;

    private Decider<SurfaceException> decider;

    private Synchronizator synchronizator;

    private CreatorBasicService<Long, StoryPoint, StoryPointInput> creatorBasicService;

    @PostConstruct
    void initCreatorService() {
        creatorBasicService = new CreatorBasicService<Long, StoryPoint, StoryPointInput>() {
            @Override
            public WrapFactory<StoryPoint, SurfaceException> wrapFactoryForCreator() {
                return wrapFactory;
            }

            @Override
            public JpaRepository<StoryPoint, Long> jpaRepositoryForCreator() {
                return storyPointRepository;
            }

            @Override
            public SurfaceException checkCreateAbility(StoryPointInput dtoInput) {
                List<SurfaceException> exceptionList = Arrays.asList(
                        solver.solve(ExistPackByIdTester.class, dtoInput.getPackId()),
                        solver.solve(ExistNodeByIdTester.class, dtoInput.getNodeId())
                );
                return decider.decide(exceptionList);
            }

            @Override
            public StoryPoint getEntityForCreationByInput(StoryPointInput dtoInput) {
                StoryPoint forCreation = new StoryPoint();
                forCreation.setPack(packRepository.findById(dtoInput.getPackId()).get());
                forCreation.setPlace(nodeRepository.findById(dtoInput.getNodeId()).get());
                forCreation.setState(PackStates.valueOf(dtoInput.getState()));
                forCreation.setPoint(LocalDateTime.now());
                return forCreation;
            }
        };
    }

    /**
     * Creates new storyPoint and does synchronization with related pack
     * @param dtoInput
     * @return
     */
    @Transactional
    @Override
    public Wrap<StoryPoint, SurfaceException> addStoryPoint(StoryPointInput dtoInput) {
        Wrap<StoryPoint, SurfaceException> storyPointWrap = creatorBasicService.create(dtoInput);
        if (storyPointWrap.wasReturnedValue()) {
            synchronizator.doSynchronizationWithPack(storyPointWrap.getValue());
        }
        return storyPointWrap;
    }

    @Override
    public List<StoryPoint> getByIdsOrderByPoint(List<Long> ids) {
      return storyPointRepository.getByIdInOrderByPoint(ids);
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
    public void setNodeRepository(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    @Autowired
    public void setPackRepository(PackRepository packRepository) {
        this.packRepository = packRepository;
    }

    @Autowired
    public void setStoryPointRepository(StoryPointRepository storyPointRepository) {
        this.storyPointRepository = storyPointRepository;
    }

    @Autowired
    public void setWrapFactory(WrapFactory<StoryPoint, SurfaceException> wrapFactory) {
        this.wrapFactory = wrapFactory;
    }

    @Autowired
    public void setSynchronizator(Synchronizator synchronizator) {
        this.synchronizator = synchronizator;
    }

    @Override
    public JpaRepository<StoryPoint, Long> jpaRepositoryForFinder() {
        return storyPointRepository;
    }
}
