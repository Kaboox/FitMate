package kabox.fitmate.dto;

import java.util.List;

public class WorkoutPlanRequest {
    private String name;
    private Long userId;
    private List<WorkoutExerciseRequest> exercises;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public List<WorkoutExerciseRequest> getExercises() {
        return exercises;
    }
}
