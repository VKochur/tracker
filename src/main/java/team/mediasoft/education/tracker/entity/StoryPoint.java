package team.mediasoft.education.tracker.entity;


import team.mediasoft.education.tracker.entity.support.PackStates;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "story_points")
public class StoryPoint {

    @Id
    private Long id;

    @Column(name = "happened", nullable = false)
    private LocalDateTime point;

    @ManyToOne
    @JoinColumn(name = "node_id", nullable = false)
    private Node place;

    @Column(name = "package_state")
    @Enumerated(EnumType.STRING)
    private PackStates state;

    @ManyToOne
    @JoinColumn(name = "package_id", nullable = false)
    private Pack pack;

    public boolean updatePackage(Pack pack) {
        if (!Objects.equals(this.pack, pack)) {
            removePackage();
            this.pack = pack;
            pack.addStory(this);
            return true;
        } else {
            return false;
        }
    }

    public boolean removePackage() {
        if (this.pack != null) {
            pack.removeStory(this);
            this.pack = null;
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

    public LocalDateTime getPoint() {
        return point;
    }

    public void setPoint(LocalDateTime point) {
        this.point = point;
    }

    public Node getPlace() {
        return place;
    }

    public void setPlace(Node place) {
        this.place = place;
    }

    public PackStates getState() {
        return state;
    }

    public void setState(PackStates state) {
        this.state = state;
    }

    public Pack getPack() {
        return pack;
    }

    public void setPack(Pack pack) {
        this.pack = pack;
    }
}
