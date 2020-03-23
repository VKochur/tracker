package team.mediasoft.education.tracker.service.impl.verification.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import team.mediasoft.education.tracker.exception.tree.request.NotExistsDataException;
import team.mediasoft.education.tracker.repository.NodeRepository;
import team.mediasoft.education.tracker.service.impl.verification.Tester;

/**
 * Test is success, if exists node with specify id
 */
@Component
public class ExistNodeByIdTester implements Tester<NotExistsDataException> {

    private final NodeRepository nodeRepository;

    @Autowired
    public ExistNodeByIdTester(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    @Override
    public NotExistsDataException apply(Object nodeId) {
        if (!nodeRepository.existsById((Long) nodeId)) {
            return new NotExistsDataException("not found node by id = " + nodeId);
        }
        return null;
    }
}
