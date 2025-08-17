package kabox.fitmate.Model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
public class WorkoutPlan {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "workoutplan_exercises",
            joinColumns = @JoinColumn(name = "workoutplan_id"),
            inverseJoinColumns = @JoinColumn(name = "exercise_id")
    )
    private List<WorkoutExercise> exercises = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
