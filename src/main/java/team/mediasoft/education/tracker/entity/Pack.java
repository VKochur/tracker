package team.mediasoft.education.tracker.entity;

import team.mediasoft.education.tracker.entity.support.PackStates;

import javax.persistence.*;

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
    private Type type;

    @Enumerated(EnumType.STRING)
    private PackStates state;

    @ManyToOne
    private Node destination;

    //@OneToMany
   // private List<StoryPoint> storyPoints;
}
