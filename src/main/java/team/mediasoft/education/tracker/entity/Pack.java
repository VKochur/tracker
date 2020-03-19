package team.mediasoft.education.tracker.entity;

import team.mediasoft.education.tracker.entity.support.PackStates;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "packages")
public class Pack {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pack_generator")
    @SequenceGenerator(name = "pack_generator", sequenceName = "package_id_sequence")
    private Long id;

    @Column(name = "identifier", unique = true, nullable = false)
    private String identifier;

    @Column(name = "recipient", nullable = false)
    private String recipient;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;

    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private PackStates state;

    @ManyToOne
    @JoinColumn(name = "destination_id", nullable = false)
    private Node destination;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "pack")
    private List<StoryPoint> storyPoints;

    public Pack() {
        storyPoints = new ArrayList<>();
    }

    public boolean addStory(StoryPoint storyPoint) {
        if (storyPoints.contains(storyPoint)) {
            return false;
        } else {
            storyPoints.add(storyPoint);
            storyPoint.updatePackage(this);
            return true;
        }
    }

    public boolean removeStory(StoryPoint storyPoint) {
        if (storyPoints.contains(storyPoint)) {
            storyPoints.remove(storyPoint);
            storyPoint.removePackage();
            return true;
        } else {
            return false;
        }
    }

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

    public Node getDestination() {
        return destination;
    }

    public void setDestination(Node destination) {
        this.destination = destination;
    }

    public List<StoryPoint> getStoryPoints() {
        return storyPoints;
    }

    public void setStoryPoints(List<StoryPoint> storyPoints) {
        this.storyPoints = storyPoints;
    }
}
