package team.mediasoft.education.tracker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import team.mediasoft.education.tracker.dto.NodeInput;
import team.mediasoft.education.tracker.entity.Node;
import team.mediasoft.education.tracker.exception.SurfaceException;
import team.mediasoft.education.tracker.exception.tree.inner.NotSupportedException;
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

    @Override
    public Optional<Node> getByPostcode(String postcode) {
        return nodeRepository.findByPostcode(postcode);
    }

    @Override
    public SurfaceException checkCreateAbility(NodeInput forCreation) {
        Optional<Node> byPostcode = nodeRepository.findByPostcode(forCreation.getPostcode());
        if (byPostcode.isPresent()) {
            return new NotUniqueDataException("node with postcode  = \"" + byPostcode.get().getPostcode() + "\" existed yet");
        }
        return null;
    }

    @Override
    public Node getEntityForCreationByInput(NodeInput dtoInput) {
        Node node = new Node();
        node.setName(dtoInput.getName());
        node.setPostcode(dtoInput.getPostcode());
        return node;
    }

    @Override
    public Wrap<Node, SurfaceException> deleteById(Long aLong) {
        return wrapFactory.ofFail(new NotSupportedException("delete node not supported"));
    }

    @Override
    public SurfaceException checkDeleteAbility(Long aLong) {
        return new NotSupportedException("check ability to delete node by id not supported");
    }

    @Override
    public List<Node> getNodesByPostcodeStartsWith(String postcode, Pageable pageable) {
        return nodeRepository.findByPostcodeStartingWithOrderByPostcode(postcode, pageable);
    }

    @Autowired
    public void setWrapFactory(WrapFactory<Node, SurfaceException> wrapFactory) {
        this.wrapFactory = wrapFactory;
    }

    @Autowired
    public void setNodeRepository(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    @Override
    public WrapFactory<Node, SurfaceException> wrapFactory() {
        return wrapFactory;
    }

    @Override
    public JpaRepository<Node, Long> jpaRepository() {
        return nodeRepository;
    }

}
