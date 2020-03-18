package team.mediasoft.education.tracker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import team.mediasoft.education.tracker.dto.NodeInput;
import team.mediasoft.education.tracker.dto.NodeOutput;
import team.mediasoft.education.tracker.dto.mapper.Mapper;
import team.mediasoft.education.tracker.entity.Node;
import team.mediasoft.education.tracker.exception.SurfaceException;
import team.mediasoft.education.tracker.exception.tree.request.NotUniqueDataException;
import team.mediasoft.education.tracker.repository.NodeRepository;
import team.mediasoft.education.tracker.service.NodeService;
import team.mediasoft.education.tracker.support.Wrap;
import team.mediasoft.education.tracker.support.WrapFactory;


import java.util.List;
import java.util.Optional;

@Service
public class NodeServiceImpl implements NodeService {

    private NodeRepository nodeRepository;

    private WrapFactory<Node, SurfaceException> wrapFactory;

    private Mapper<Node, NodeOutput, NodeInput> mapper;

    @Autowired
    public void setWrapFactory(WrapFactory<Node, SurfaceException> wrapFactory) {
        this.wrapFactory = wrapFactory;
    }

    @Autowired
    public void setNodeRepository(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    @Autowired
    public void setMapper(Mapper<Node, NodeOutput, NodeInput> mapper) {
        this.mapper = mapper;
    }

    @Override
    public Optional<Node> getById(Long id) {
        return nodeRepository.findById(id);
    }

    @Override
    public Optional<Node> getByPostcode(String postcode) {
        return nodeRepository.findByPostcode(postcode);
    }

    @Override
    public Wrap<Node, SurfaceException> create(NodeInput forCreation) {
        SurfaceException exception = checkCreateAbility(forCreation);
        if (exception != null) {
            return wrapFactory.ofFail(exception);
        } else {
            Node forSave = mapper.getForCreation(forCreation);
            return wrapFactory.ofSuccess(nodeRepository.save(forSave));
        }
    }

    private SurfaceException checkCreateAbility(NodeInput forCreation) {
        Optional<Node> byPostcode = nodeRepository.findByPostcode(forCreation.getPostcode());
        if (byPostcode.isPresent()) {
            return new NotUniqueDataException("node with postcode  = \"" + byPostcode.get().getPostcode() + "\" existed yet");
        }
        return null;
    }

    @Override
    public List<Node> getNodesByPostcodeStartsWith(String postcode, Pageable pageable) {
        return nodeRepository.findByPostcodeStartingWithOrderByPostcode(postcode, pageable);
    }
}
