package team.mediasoft.education.tracker.entity;

import javax.persistence.*;

@Entity
@Table(name = "types")
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "type_generator")
    @SequenceGenerator(name = "type_generator", sequenceName = "types_sequence")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
