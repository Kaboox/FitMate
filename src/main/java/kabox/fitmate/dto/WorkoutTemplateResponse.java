package kabox.fitmate.dto;

import java.util.List;

public class WorkoutTemplateResponse {
    private Long id;
    private String name;
    private String description;

    private List<WorkoutTemplateExerciseResponse> exercises;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setExercises(List<WorkoutTemplateExerciseResponse> exercises) {
        this.exercises = exercises;
    }

    public List<WorkoutTemplateExerciseResponse> getExercises() {
        return exercises;
    }
}
