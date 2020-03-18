package team.mediasoft.education.tracker.dto.mapper.impl;

import org.springframework.stereotype.Component;
import team.mediasoft.education.tracker.dto.NodeDto;
import team.mediasoft.education.tracker.dto.mapper.Mapper;
import team.mediasoft.education.tracker.entity.Node;

@Component
public class NodeMapper implements Mapper<Node, NodeDto> {

    @Override
    public NodeDto getDto(Node entity) {
        NodeDto nodeDto = new NodeDto();
        nodeDto.setId(entity.getId());
        nodeDto.setName(entity.getName());
        nodeDto.setPostcode(entity.getPostcode());
        return nodeDto;
    }
}
