package kabox.fitmate.Model;

import jakarta.persistence.*;

@Entity
public class WorkoutSet {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "workout_exercise_id")
    private WorkoutExercise workoutExercise;

    private int reps;

    // optional weight - pullups don't require weight if not weighted variant
    private double weight;

    public Long getId() {
        return id;
    }

    public int getReps() {
        return reps;
    }

    public WorkoutExercise getWorkoutExercise() {
        return workoutExercise;
    }

    public double getWeight() {
        return weight;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setWorkoutExercise(WorkoutExercise workoutExercise) {
        this.workoutExercise = workoutExercise;
    }
}
