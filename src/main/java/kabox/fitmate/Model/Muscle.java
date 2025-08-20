package kabox.fitmate.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Muscle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category;

    // Ćwiczenia gdzie ten mięsień jest PRIMARY
    @JsonIgnore
    @OneToMany(mappedBy = "primaryMuscle", cascade = CascadeType.ALL)
    private List<Exercise> primaryExercises;

    @JsonIgnore
    // Ćwiczenia gdzie ten mięsień jest SECONDARY
    @ManyToMany(mappedBy = "secondaryMuscles")
    private List<Exercise> secondaryExercises;

    // --- GETTERY / SETTERY ---
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public List<Exercise> getPrimaryExercises() { return primaryExercises; }
    public List<Exercise> getSecondaryExercises() { return secondaryExercises; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCategory(String category) { this.category = category; }
    public void setPrimaryExercises(List<Exercise> primaryExercises) { this.primaryExercises = primaryExercises; }
    public void setSecondaryExercises(List<Exercise> secondaryExercises) { this.secondaryExercises = secondaryExercises; }
}
