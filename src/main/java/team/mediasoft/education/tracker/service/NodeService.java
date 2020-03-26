package team.mediasoft.education.tracker.service;

import org.springframework.data.domain.Pageable;
import team.mediasoft.education.tracker.dto.NodeInput;
import team.mediasoft.education.tracker.entity.Node;
import team.mediasoft.education.tracker.service.basic.CreatorBasicService;
import team.mediasoft.education.tracker.service.basic.FinderBasicService;


import java.util.List;
import java.util.Optional;


public interface NodeService extends
        CreatorBasicService<Long, Node, NodeInput>,
        FinderBasicService<Node, Long> {

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
