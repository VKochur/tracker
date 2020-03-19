package team.mediasoft.education.tracker.dto.mapper.impl;

import org.springframework.stereotype.Component;
import team.mediasoft.education.tracker.dto.NodeOutput;
import team.mediasoft.education.tracker.dto.mapper.Mapper;
import team.mediasoft.education.tracker.entity.Node;

@Component
public class NodeMapper implements Mapper<Node, NodeOutput> {

    @Override
    public NodeOutput getOutput(Node entity) {
        NodeOutput nodeOutput = new NodeOutput();
        nodeOutput.setId(entity.getId());
        nodeOutput.setName(entity.getName());
        nodeOutput.setPostcode(entity.getPostcode());
        return nodeOutput;
    }
}
