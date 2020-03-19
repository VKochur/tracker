package team.mediasoft.education.tracker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import team.mediasoft.education.tracker.dto.PackInput;
import team.mediasoft.education.tracker.entity.Pack;
import team.mediasoft.education.tracker.exception.SurfaceException;
import team.mediasoft.education.tracker.exception.tree.inner.NotSupportedException;
import team.mediasoft.education.tracker.exception.tree.request.NotExistsDataException;
import team.mediasoft.education.tracker.exception.tree.request.NotUniqueDataException;
import team.mediasoft.education.tracker.repository.NodeRepository;
import team.mediasoft.education.tracker.repository.PackRepository;
import team.mediasoft.education.tracker.service.PackService;
import team.mediasoft.education.tracker.support.Wrap;
import team.mediasoft.education.tracker.support.WrapFactory;


import java.util.Optional;

@Service
public class PackServiceImpl implements PackService {

    private WrapFactory<Pack, SurfaceException> wrapFactory;

    private PackRepository packRepository;

    private NodeRepository nodeRepository;


    @Override
    public Pack getEntityForCreationByInput(PackInput dtoInput) {
        return null;
    }

    @Override
    public Wrap<Pack, SurfaceException> deleteById(Long aLong) {
        return wrapFactory.ofFail(new NotSupportedException("delete pack not supported"));
    }

    @Override
    public SurfaceException checkDeleteAbility(Long aLong) {
        return new NotSupportedException("check delete pack not supported");
    }

    @Override
    public SurfaceException checkCreateAbility(PackInput dtoInput) {
        SurfaceException exception = checkNode(dtoInput.getNodeId());
        if (exception != null) {
            return exception;
        }
        exception =checkUniqueIdentifier(dtoInput.getIdentifier());
        if (exception != null) {
            return exception;
        }
        return null;
    }

    private SurfaceException checkUniqueIdentifier(String identifier) {
        Optional<Pack> byIdentifier = packRepository.findByIdentifier(identifier);
        if (byIdentifier.isPresent()) {
            return new NotUniqueDataException("pack with identifier = \"" + identifier + "\" existed yet");
        }
        return null;
    }

    private SurfaceException checkNode(Long nodeId) {
        if (!nodeRepository.existsById(nodeId)) {
            return new NotExistsDataException("not found node by id = " + nodeId);
        }
        return null;
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

    @Override
    public WrapFactory<Pack, SurfaceException> wrapFactory() {
        return wrapFactory;
    }

    @Override
    public JpaRepository<Pack, Long> jpaRepository() {
        return packRepository;
    }


}
