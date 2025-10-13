package kabox.fitmate.dto;

import kabox.fitmate.Model.WorkoutTemplateExercise;

public class WorkoutTemplateExerciseResponse {
    private Long id;
    private Long exerciseId;
    private String exerciseName;
    private int sets;
    private int reps;

    public WorkoutTemplateExerciseResponse(WorkoutTemplateExercise exercise) {
        // id encji pośredniej (WorkoutTemplateExercise)
        this.id = exercise.getId();

        if (exercise.getExercise() != null) {
            this.exerciseId = exercise.getExercise().getId();
            this.exerciseName = exercise.getExercise().getName();
        }

        this.sets = exercise.getSets();
        this.reps = exercise.getReps();
    }

    public Long getId() {
        return id;
    }

    public Long getExerciseId() {
        return exerciseId;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public int getSets() {
        return sets;
    }

    public int getReps() {
        return reps;
    }
}
