package kabox.fitmate.dto;

public class WorkoutTemplateExerciseRequest {
    private Long exerciseId;
    private int sets;
    private int reps;

    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public Long getExerciseId() {
        return exerciseId;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getSets() {
        return sets;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getReps() {
        return reps;
    }
}