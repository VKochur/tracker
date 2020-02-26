package team.mediasoft.education.tracker.entity;


import team.mediasoft.education.tracker.entity.support.PackStates;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "story_points")
public class StoryPoint {

    @Id
    private Long id;

    @Column(name = "happened")
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
}
