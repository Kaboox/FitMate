package kabox.fitmate.Model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class WorkoutTemplate {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "workoutTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkoutTemplateExercise> templateExercises = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public List<WorkoutTemplateExercise> getTemplateExercises() { return templateExercises; }
    public void setTemplateExercises(List<WorkoutTemplateExercise> templateExercises) {
        this.templateExercises = templateExercises;
    }
}
