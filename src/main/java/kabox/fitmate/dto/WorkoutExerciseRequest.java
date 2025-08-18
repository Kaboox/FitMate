package kabox.fitmate.dto;

import java.util.List;

public class WorkoutExerciseRequest {
    private Long exerciseId;
    private List<WorkoutSetRequest> sets;

    public Long getExerciseId() {
        return exerciseId;
    }

    public List<WorkoutSetRequest> getSets() {
        return sets;
    }

    public void setSets(List<WorkoutSetRequest> sets) {
        this.sets = sets;
    }
}
