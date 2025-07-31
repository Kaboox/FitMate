package kabox.fitmate.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Muscle {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "muscle")
    private List<Exercise> exercises;
}
