package team.mediasoft.education.tracker.dto;

import team.mediasoft.education.tracker.entity.Type;
import team.mediasoft.education.tracker.entity.support.PackStates;

import java.util.List;

public class PackOutput {

    private Long id;
    private String identifier;
    private String recipient;
    private Type type;
    private PackStates state;
    private Long destinationId;
    private List<Long> storiesIds;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public PackStates getState() {
        return state;
    }

    public void setState(PackStates state) {
        this.state = state;
    }

    public Long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Long destinationId) {
        this.destinationId = destinationId;
    }

    public List<Long> getStoriesIds() {
        return storiesIds;
    }

    public void setStoriesIds(List<Long> storiesIds) {
        this.storiesIds = storiesIds;
    }
}
