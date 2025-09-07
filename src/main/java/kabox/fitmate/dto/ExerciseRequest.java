package kabox.fitmate.dto;

import java.util.List;

public class ExerciseRequest {
    private String name;
    private String description;
    private String videoUrl;
    private String imageUrl;
    private Long primaryMuscleId;
    private List<Long> secondaryMuscleIds;
    private List<String> mistakes;

    // gettery + settery


    public String getVideoUrl() {
        return videoUrl;
    }
    public String getImageUrl() { return imageUrl; }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public Long getPrimaryMuscleId() {
        return primaryMuscleId;
    }

    public List<Long> getSecondaryMuscleIds() {
        return secondaryMuscleIds;
    }

    public List<String> getMistakes() {
        return mistakes;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrimaryMuscleId(Long primaryMuscleId) {
        this.primaryMuscleId = primaryMuscleId;
    }

    public void setSecondaryMuscleIds(List<Long> secondaryMuscleIds) {
        this.secondaryMuscleIds = secondaryMuscleIds;
    }

    public void setMistakes(List<String> mistakes) {
        this.mistakes = mistakes;
    }
}
