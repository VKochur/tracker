package team.mediasoft.education.tracker.dto;

import team.mediasoft.education.tracker.entity.support.PackStates;
import team.mediasoft.education.tracker.support.validation.ValueOfEnum;

import javax.validation.constraints.NotNull;

public class StoryPointInput {

    @NotNull
    private Long nodeId;

    @NotNull
    private Long packId;

    @ValueOfEnum(enumClazz = PackStates.class, message = "must be any of \"STORAGE\", \"MOVEMENT\"")
    private String state;

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public Long getPackId() {
        return packId;
    }

    public void setPackId(Long packId) {
        this.packId = packId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
