package team.mediasoft.education.tracker.dto;

import team.mediasoft.education.tracker.entity.support.PackStates;

public class StoryPointInput {

    private Long nodeId;
    private Long packId;
    private PackStates state;

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

    public PackStates getState() {
        return state;
    }

    public void setState(PackStates state) {
        this.state = state;
    }
}
