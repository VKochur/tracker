package team.mediasoft.education.tracker.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "nodes")
public class Node {

    @Id
    private Long id;

    @Column(name = "postcode", unique = true, nullable = false)
    private String postcode;

    @Column(name = "name")
    private String name;


}
