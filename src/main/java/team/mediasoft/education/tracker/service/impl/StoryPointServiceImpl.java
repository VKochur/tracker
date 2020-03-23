package team.mediasoft.education.tracker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import team.mediasoft.education.tracker.dto.StoryPointInput;
import team.mediasoft.education.tracker.entity.Pack;
import team.mediasoft.education.tracker.entity.StoryPoint;
import team.mediasoft.education.tracker.exception.SurfaceException;
import team.mediasoft.education.tracker.exception.tree.inner.NotSupportedException;
import team.mediasoft.education.tracker.repository.NodeRepository;
import team.mediasoft.education.tracker.repository.PackRepository;
import team.mediasoft.education.tracker.repository.StoryPointRepository;
import team.mediasoft.education.tracker.service.StoryPointService;
import team.mediasoft.education.tracker.service.impl.verification.Decider;
import team.mediasoft.education.tracker.service.impl.verification.TestResultSolver;
import team.mediasoft.education.tracker.service.impl.verification.impl.ExistNodeByIdTester;
import team.mediasoft.education.tracker.service.impl.verification.impl.ExistPackByIdTester;
import team.mediasoft.education.tracker.support.Wrap;
import team.mediasoft.education.tracker.support.WrapFactory;

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


    @Override
    public StoryPoint getEntityForCreationByInput(StoryPointInput dtoInput) {
        StoryPoint forCreation = new StoryPoint();
        forCreation.setPack(packRepository.findById(dtoInput.getPackId()).get());
        forCreation.setPlace(nodeRepository.findById(dtoInput.getNodeId()).get());
        forCreation.setState(dtoInput.getState());
        forCreation.setPoint(LocalDateTime.now());
        return forCreation;
    }

    /**
     * Can't use create by default, because we need to synchronization pack's story point and pack
     * @param dtoInput
     * @return
     */
   // @Transactional
   //todo: why does synchronization work without transactional and cascade?
    @Override
    public Wrap<StoryPoint, SurfaceException> create(StoryPointInput dtoInput) {
        SurfaceException exception = checkCreateAbility(dtoInput);
        if (exception != null) {
            return wrapFactory.ofFail(exception);
        } else {
            StoryPoint forCreation = getEntityForCreationByInput(dtoInput);
            doSynchronizationWithPack(forCreation);
            StoryPoint saved = storyPointRepository.save(forCreation);
            return wrapFactory.ofSuccess(saved);
        }
    }

    private void doSynchronizationWithPack(StoryPoint forCreation) {
        Pack pack = forCreation.getPack();
        if (pack.getState() != forCreation.getState()) {
            pack.setState(forCreation.getState());
        }
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

    @Override
    public WrapFactory<StoryPoint, SurfaceException> wrapFactory() {
        return wrapFactory;
    }

    @Override
    public JpaRepository<StoryPoint, Long> jpaRepository() {
        return storyPointRepository;
    }

    @Override
    public SurfaceException checkDeleteAbility(Long id) {
        return new NotSupportedException("check delete story's point not supported");
    }

    @Override
    public Wrap<StoryPoint, SurfaceException> deleteById(Long id) {
        return wrapFactory.ofFail(new NotSupportedException("delete story's point not supported"));
    }
}
