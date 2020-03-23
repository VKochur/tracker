package team.mediasoft.education.tracker.service.impl.verification.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import team.mediasoft.education.tracker.entity.Node;
import team.mediasoft.education.tracker.exception.tree.request.NotUniqueDataException;
import team.mediasoft.education.tracker.repository.NodeRepository;
import team.mediasoft.education.tracker.service.impl.verification.Tester;

import java.util.Optional;

/**
 * Test is success, if doesn't exists any nodes, that have specify postcode
 */
@Component
public class UniquePostcodeNodeTester implements Tester<NotUniqueDataException> {

    private NodeRepository nodeRepository;

    @Autowired
    public UniquePostcodeNodeTester(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    @Override
    public NotUniqueDataException apply(Object nodePostcode) {
        Optional<Node> byPostcode = nodeRepository.findByPostcode((String) nodePostcode);
        if (byPostcode.isPresent()) {
            return new NotUniqueDataException("node with postcode = \"" + nodePostcode + "\" existed yet");
        }
        return null;
    }
}
