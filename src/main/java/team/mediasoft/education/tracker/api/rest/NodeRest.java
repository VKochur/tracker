package team.mediasoft.education.tracker.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import team.mediasoft.education.tracker.dto.NodeInput;
import team.mediasoft.education.tracker.dto.NodeOutput;
import team.mediasoft.education.tracker.dto.mapper.Mapper;
import team.mediasoft.education.tracker.entity.Node;
import team.mediasoft.education.tracker.exception.SurfaceException;
import team.mediasoft.education.tracker.exception.tree.request.NotExistsDataException;
import team.mediasoft.education.tracker.service.NodeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/node")
public class NodeRest {

    private NodeService nodeService;

    private Mapper<Node, NodeOutput> mapper;

    @Autowired
    public void setNodeService(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @Autowired
    public void setMapper(Mapper<Node, NodeOutput> mapper) {
        this.mapper = mapper;
    }

    @PostMapping(produces = "application/json;charset=utf-8", consumes = "application/json;charset=utf-8")
    public NodeOutput createNode(@RequestBody NodeInput nodeInput) throws SurfaceException {
        return mapper.getOutput(nodeService.create(nodeInput).getValueOrElseThrow());
    }

    @GetMapping(value = "/{id}")
    public NodeOutput getById(@PathVariable(name = "id") Long id) throws NotExistsDataException {
        Optional<Node> byId = nodeService.getById(id);
        if (byId.isPresent()) {
            return mapper.getOutput(byId.get());
        } else {
            throw new NotExistsDataException("node not found. id = " + id);
        }
    }

    @GetMapping(value = "/postcode/{postcode}")
    public NodeOutput getByPostcode(@PathVariable(name = "postcode") String postcode) throws NotExistsDataException {
        Optional<Node> byId = nodeService.getByPostcode(postcode);
        if (byId.isPresent()) {
            return mapper.getOutput(byId.get());
        } else {
            throw new NotExistsDataException("not found node by postcode = " + postcode);
        }
    }

    @GetMapping(value = "/postcode_start_with", params = {"postcodeStartsWith", "pageNumber", "pageSize"})
    public List<NodeOutput> getByPostCodeStartsWith(@RequestParam("postcodeStartsWith") String postcodeStartsWith,
                                          @RequestParam("pageNumber") Integer pageNumber,
                                          @RequestParam("pageSize") Integer pageSize) {
        return mapper.getListOutputs(nodeService.getNodesByPostcodeStartsWith(postcodeStartsWith, PageRequest.of(pageNumber, pageSize)));
    }

    @GetMapping(value = "/all")
    public List<NodeOutput> getAllOrderByPostcode(@RequestParam("pageNumber") Integer pageNumber,
                                                  @RequestParam("pageSize") Integer pageSize) {
        return mapper.getListOutputs(nodeService.getAll(PageRequest.of(pageNumber, pageSize, Sort.by("postcode"))));
    }

}
