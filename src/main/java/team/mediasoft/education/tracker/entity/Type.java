package team.mediasoft.education.tracker.entity;

import javax.persistence.*;

@Entity
@Table(name = "types")
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "type_generator")
    @SequenceGenerator(name = "type_generator", sequenceName = "types_sequence")
    private Long id;

    @Column(name = "name")
    private String name;
}
