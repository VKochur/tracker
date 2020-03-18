package team.mediasoft.education.tracker.service;

import org.springframework.data.domain.Pageable;
import team.mediasoft.education.tracker.dto.NodeInput;
import team.mediasoft.education.tracker.entity.Node;
import team.mediasoft.education.tracker.exception.SurfaceException;
import team.mediasoft.education.tracker.support.Wrap;

import java.util.List;
import java.util.Optional;


public interface NodeService {

    /**
     * Gets node with specific id, or Optional#empty() if not found
     * @param id must not be null
     * @return node for specific id
     * @throws IllegalArgumentException if id is null
     */
    Optional<Node> getById(Long id);

    /**
     *
     * @param postcode
     * @return node, with specific postcode
     */
    Optional<Node> getByPostcode(String postcode);

    /**
     * Create node in storage
     *
     * @param
     * @return container, that contains created node, or Exception, which contains info about
     * reason why can't get result
     */
    Wrap<Node, SurfaceException> create(NodeInput forCreation);

    /**
     * Gets list of nodes, with similar postcode
     *
     * @param postcode
     * @param pageable
     * @return
     */
    List<Node> getNodesByPostcodeStartsWith(String postcode, Pageable pageable);
}
