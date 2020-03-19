package team.mediasoft.education.tracker.entity;

import javax.persistence.*;

@Entity
@Table(name = "nodes")
public class Node {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "node_generator")
    @SequenceGenerator(name = "node_generator", sequenceName = "node_id_sequence", allocationSize = 5)
    private Long id;

    @Column(name = "postcode", unique = true, nullable = false)
    private String postcode;

    @Column(name = "name")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
