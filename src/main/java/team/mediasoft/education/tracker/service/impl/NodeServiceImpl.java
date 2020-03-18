package team.mediasoft.education.tracker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import team.mediasoft.education.tracker.dto.NodeDto;
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

    private Mapper<Node, NodeDto> dtoMapper;

    private WrapFactory<NodeDto, SurfaceException> wrapFactory;

    @Autowired
    public void setWrapFactory(WrapFactory<NodeDto, SurfaceException> wrapFactory) {
        this.wrapFactory = wrapFactory;
    }

    @Autowired
    public void setNodeRepository(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    @Autowired
    public void setDtoMapper(Mapper<Node, NodeDto> dtoMapper) {
        this.dtoMapper = dtoMapper;
    }

    @Override
    public Optional<NodeDto> getById(Long id) {
        Optional<Node> byId = nodeRepository.findById(id);
        return dtoMapper.getDto(byId);
    }

    @Override
    public Optional<NodeDto> getByPostcode(String postcode) {
        Optional<Node> byPostcode = nodeRepository.findByPostcode(postcode);
        return dtoMapper.getDto(byPostcode);
    }

    @Override
    public Wrap<NodeDto, SurfaceException> create(NodeDto forCreation) {
        SurfaceException exception = checkCreateAbility(forCreation);
        if (exception != null) {
            return wrapFactory.ofFail(exception);
        } else {
            Node newNode = new Node();
            newNode.setName(forCreation.getName());
            newNode.setPostcode(forCreation.getPostcode());
            return wrapFactory.ofSuccess(dtoMapper.getDto(nodeRepository.save(newNode)));
        }
    }

    private SurfaceException checkCreateAbility(NodeDto forCreation) {
        Optional<Node> byPostcode = nodeRepository.findByPostcode(forCreation.getPostcode());
        if (byPostcode.isPresent()) {
            return new NotUniqueDataException("node with postcode  = \"" + byPostcode.get().getPostcode() + "\" existed yet");
        }
        return null;
    }

    @Override
    public List<NodeDto> getNodesByPostcodeStartsWith(String postcode, Pageable pageable) {
        List<Node> byPostcodeStartingWithOrderByPostcode = nodeRepository.findByPostcodeStartingWithOrderByPostcode(postcode, pageable);
        return dtoMapper.getListDto(byPostcodeStartingWithOrderByPostcode);
    }
}
