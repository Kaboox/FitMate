package kabox.fitmate.dto;

import java.util.List;

public class WorkoutTemplateRequest {


    private String name;
    private String description;
    private List<WorkoutTemplateExerciseRequest> exercises;

    public void setExercises(List<WorkoutTemplateExerciseRequest> exercises) {
        this.exercises = exercises;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<WorkoutTemplateExerciseRequest> getExercises() {
        return exercises;
    }
}
