package kabox.fitmate.dto;

import java.util.List;

public class AiImportRequest {

    private String title;
    private String date; // AI sends string ex. "2023-10-27", parse it in service
    private String notes;
    private List<ExerciseDto> exercises;

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getNotes() {
        return notes;
    }

    public List<ExerciseDto> getExercises() {
        return exercises;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setExercises(List<ExerciseDto> exercises) {
        this.exercises = exercises;
    }

    // Inner class for exercises
    public static class ExerciseDto {
        private String exerciseName; // Try parsing from our DB
        private List<SetDto> sets;

        public String getExerciseName() {
            return exerciseName;
        }

        public List<SetDto> getSets() {
            return sets;
        }

        public void setExerciseName(String exerciseName) {
            this.exerciseName = exerciseName;
        }

        public void setSets(List<SetDto> sets) {
            this.sets = sets;
        }
    }

    // Inner class for Sets
    public static class SetDto {
        private int reps;
        private double weight;

        public int getReps() {
            return reps;
        }

        public double getWeight() {
            return weight;
        }

        public void setReps(int reps) {
            this.reps = reps;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }
    }
}