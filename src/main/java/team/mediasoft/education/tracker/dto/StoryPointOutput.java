package team.mediasoft.education.tracker.dto;

import team.mediasoft.education.tracker.entity.support.PackStates;

import java.time.LocalDateTime;

public class StoryPointOutput {

    private Long id;
    private Long nodeId;
    private Long packId;
    private PackStates state;
    private LocalDateTime point;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getPoint() {
        return point;
    }

    public void setPoint(LocalDateTime point) {
        this.point = point;
    }
}
