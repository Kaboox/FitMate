package kabox.fitmate.Model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String videoUrl;
    private String imageUrl;

    // --- Primary muscle ---
    @ManyToOne
    @JoinColumn(name = "primary_muscle_id")
    private Muscle primaryMuscle;

    // --- Secondary muscles ---
    @ManyToMany
    @JoinTable(
            name = "exercise_secondary_muscle",
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "muscle_id")
    )
    private List<Muscle> secondaryMuscles;

    @ElementCollection
    private List<String> mistakes;


    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getVideoUrl() { return videoUrl; }
    public String getImageUrl() { return imageUrl; }
    public Muscle getPrimaryMuscle() { return primaryMuscle; }
    public List<Muscle> getSecondaryMuscles() { return secondaryMuscles; }
    public List<String> getMistakes() {return mistakes;}

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setPrimaryMuscle(Muscle primaryMuscle) { this.primaryMuscle = primaryMuscle; }
    public void setSecondaryMuscles(List<Muscle> secondaryMuscles) { this.secondaryMuscles = secondaryMuscles; }

    public void setMistakes(List<String> mistakes) {
        this.mistakes = mistakes;
    }
}
