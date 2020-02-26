package team.mediasoft.education.tracker.entity;

import team.mediasoft.education.tracker.entity.support.PackStates;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "packages")
public class Pack {

    @Id
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
}
