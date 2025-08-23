package kabox.fitmate.dto;

public class ExerciseMuscleRequest {
    private Long muscleId;
    private String role; // PRIMARY, SECONDARY, STABILIZER

    public Long getMuscleId() {
        return muscleId;
    }

    public void setMuscleId(Long muscleId) {
        this.muscleId = muscleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
