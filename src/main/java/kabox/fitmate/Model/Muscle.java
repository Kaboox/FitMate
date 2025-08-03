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

    private String category;


    @OneToMany(mappedBy = "muscle")
    private List<Exercise> exercises;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
