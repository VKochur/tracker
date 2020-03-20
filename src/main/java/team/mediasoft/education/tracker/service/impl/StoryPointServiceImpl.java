package team.mediasoft.education.tracker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import team.mediasoft.education.tracker.dto.StoryPointInput;
import team.mediasoft.education.tracker.entity.Pack;
import team.mediasoft.education.tracker.entity.StoryPoint;
import team.mediasoft.education.tracker.exception.SurfaceException;
import team.mediasoft.education.tracker.exception.tree.inner.NotSupportedException;
import team.mediasoft.education.tracker.exception.tree.request.NotExistsDataException;
import team.mediasoft.education.tracker.repository.NodeRepository;
import team.mediasoft.education.tracker.repository.PackRepository;
import team.mediasoft.education.tracker.repository.StoryPointRepository;
import team.mediasoft.education.tracker.service.StoryPointService;
import team.mediasoft.education.tracker.support.Wrap;
import team.mediasoft.education.tracker.support.WrapFactory;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StoryPointServiceImpl implements StoryPointService {

    private StoryPointRepository storyPointRepository;

    private NodeRepository nodeRepository;

    private PackRepository packRepository;

    private WrapFactory<StoryPoint, SurfaceException> wrapFactory;

    @Override
    public StoryPoint getEntityForCreationByInput(StoryPointInput dtoInput) {
        StoryPoint forCreation = new StoryPoint();
        forCreation.setPack(packRepository.findById(dtoInput.getPackId()).get());
        forCreation.setPlace(nodeRepository.findById(dtoInput.getNodeId()).get());
        forCreation.setState(dtoInput.getState());
        forCreation.setPoint(LocalDateTime.now());
        return forCreation;
    }


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


    //todo pick up logic for verification. see similar in PackService
    @Override
    public SurfaceException checkCreateAbility(StoryPointInput dtoInput) {

        SurfaceException exception = checkNode(dtoInput.getNodeId());
        if (exception != null) {
            return exception;
        }
        exception = checkPack(dtoInput.getPackId());
        if (exception != null) {
            return exception;
        }

        return null;
    }

    //todo pick up logic for verification
    private SurfaceException checkPack(Long packId) {
        if (!packRepository.existsById(packId)) {
            return new NotExistsDataException("not found pack by id = " + packId);
        }
        return null;
    }

    private SurfaceException checkNode(Long nodeId) {
        if (!nodeRepository.existsById(nodeId)) {
            return new NotExistsDataException("not found node by id = " + nodeId);
        }
        return null;
    }

    @Override
    public List<StoryPoint> getByIdsOrderByPoint(List<Long> ids) {
      return storyPointRepository.getByIdInOrderByPoint(ids);
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
