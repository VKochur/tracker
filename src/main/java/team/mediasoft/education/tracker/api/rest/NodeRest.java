package team.mediasoft.education.tracker.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import team.mediasoft.education.tracker.dto.NodeInput;
import team.mediasoft.education.tracker.dto.NodeOutput;
import team.mediasoft.education.tracker.dto.mapper.Mapper;
import team.mediasoft.education.tracker.entity.Node;
import team.mediasoft.education.tracker.service.NodeService;

import java.util.List;

@RestController
@RequestMapping("/api/node")
public class NodeRest {

    private NodeService nodeService;

    private Mapper<Node, NodeOutput, NodeInput> mapper;

    @Autowired
    public void setNodeService(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @Autowired
    public void setMapper(Mapper<Node, NodeOutput, NodeInput> mapper) {
        this.mapper = mapper;
    }

    @GetMapping(value = "/{postcode}", params = {"number", "size"})
    public List<NodeOutput> getPagination(@PathVariable("postcode") String postcode,
                                          @RequestParam("size") Integer size,
                                          @RequestParam("number") Integer number) {
        return mapper.getListOutputs(nodeService.getNodesByPostcodeStartsWith(postcode, PageRequest.of(number, size)));
    }
}
