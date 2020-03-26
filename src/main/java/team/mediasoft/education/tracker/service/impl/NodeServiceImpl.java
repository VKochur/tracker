package team.mediasoft.education.tracker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import team.mediasoft.education.tracker.dto.NodeInput;
import team.mediasoft.education.tracker.entity.Node;
import team.mediasoft.education.tracker.exception.SurfaceException;
import team.mediasoft.education.tracker.repository.NodeRepository;
import team.mediasoft.education.tracker.service.NodeService;
import team.mediasoft.education.tracker.service.impl.verification.TestResultSolver;
import team.mediasoft.education.tracker.service.impl.verification.impl.UniquePostcodeNodeTester;
import team.mediasoft.education.tracker.support.WrapFactory;

import java.util.List;
import java.util.Optional;

@Service
public class NodeServiceImpl implements NodeService {

    private NodeRepository nodeRepository;

    private WrapFactory<Node, SurfaceException> wrapFactory;

    private TestResultSolver<SurfaceException> testResultSolver;

    @Override
    public Optional<Node> getByPostcode(String postcode) {
        return nodeRepository.findByPostcode(postcode);
    }

    @Override
    public SurfaceException checkCreateAbility(NodeInput forCreation) {
        return testResultSolver.solve(UniquePostcodeNodeTester.class, forCreation.getPostcode());
    }

    @Override
    public Node getEntityForCreationByInput(NodeInput dtoInput) {
        Node node = new Node();
        node.setName(dtoInput.getName());
        node.setPostcode(dtoInput.getPostcode());
        return node;
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

    @Autowired
    public void setTestResultSolver(TestResultSolver<SurfaceException> testResultSolver) {
        this.testResultSolver = testResultSolver;
    }

    @Override
    public WrapFactory<Node, SurfaceException> wrapFactoryForCreator() {
        return wrapFactory;
    }

    @Override
    public JpaRepository<Node, Long> jpaRepositoryForCreator() {
        return nodeRepository;
    }

    @Override
    public JpaRepository<Node, Long> jpaRepositoryForFinder() {
        return nodeRepository;
    }
}
