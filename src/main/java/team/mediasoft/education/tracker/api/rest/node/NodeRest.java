package team.mediasoft.education.tracker.api.rest.node;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import team.mediasoft.education.tracker.dto.NodeDto;
import team.mediasoft.education.tracker.service.NodeService;

import java.util.List;

@RestController
@RequestMapping("/api/node")
public class NodeRest {

    private NodeService nodeService;

    @Autowired
    public void setNodeService(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @GetMapping(value = "/{postcode}", params = {"number", "size"})
    public List<NodeDto> getPagination(@PathVariable("postcode") String postcode,
                                       @RequestParam("size") Integer size,
                                       @RequestParam("number") Integer number) {
        return nodeService.getNodesByPostcodeStartsWith(postcode, PageRequest.of(number, size));
    }

}
