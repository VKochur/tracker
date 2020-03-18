package team.mediasoft.education.tracker.service;

import org.springframework.data.domain.Pageable;
import team.mediasoft.education.tracker.dto.NodeDto;
import team.mediasoft.education.tracker.exception.SurfaceException;
import team.mediasoft.education.tracker.support.Wrap;


import java.util.List;
import java.util.Optional;

public interface NodeService {

    /**
     * Gets dto for type with specific id, or Optional#empty() if not found
     * @param id must not be null
     * @return NodeDto for specific id
     * @throws IllegalArgumentException if id is null
     */
    Optional<NodeDto> getById(Long id);

    /**
     *
     * @param postcode
     * @return node's dto, with specific postcode
     */
    Optional<NodeDto> getByPostcode(String postcode);

    /**
     * Create node in storage
     *
     * @param
     * @return container, that contains created type as NodeDto, or Exception, which contains info about
     * reason why can't get result
     */
    Wrap<NodeDto, SurfaceException> create(NodeDto forCreation);


    /**
     * Gets list of node, with similar postcode
     *
     * @param postcode
     * @param pageable
     * @return
     */
    List<NodeDto> getNodesByPostcodeStartsWith(String postcode, Pageable pageable);
}
