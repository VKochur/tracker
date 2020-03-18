package team.mediasoft.education.tracker.service;

import org.springframework.data.domain.Pageable;
import team.mediasoft.education.tracker.dto.NodeInput;
import team.mediasoft.education.tracker.dto.NodeOutput;
import team.mediasoft.education.tracker.entity.Node;


import java.util.List;
import java.util.Optional;


public interface NodeService
        extends BasicService<Long, Node, NodeOutput, NodeInput>{

    /**
     *
     * @param postcode
     * @return node, with specific postcode
     */
    Optional<Node> getByPostcode(String postcode);

    /**
     * Gets list of nodes, with similar postcode
     *
     * @param postcode
     * @param pageable
     * @return
     */
    List<Node> getNodesByPostcodeStartsWith(String postcode, Pageable pageable);
}
