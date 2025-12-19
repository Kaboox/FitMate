package kabox.fitmate.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class WorkoutExercise {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "workout_id")
    private Workout workout;

    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = true)
    private Exercise exercise;

    /* LOGIC
    * If exercise name found in db - exercise is set
    * otherwise AI uses tempName
    * */

    // "SAFETY BUFOR"
    @Column(name = "temp_name")
    private String tempName;

    @OneToMany(mappedBy = "workoutExercise", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkoutSet> sets;

    public Long getId() {
        return id;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public Workout getWorkoutPlan() {
        return workout;
    }

    public void setWorkoutPlan(Workout workout) {
        this.workout = workout;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<WorkoutSet> getSets() {
        return sets;
    }

    public void setSets(List<WorkoutSet> sets) {
        this.sets = sets;
    }

    public String getTempName() {
        return tempName;
    }

    public void setTempName(String tempName) {
        this.tempName = tempName;
    }
}