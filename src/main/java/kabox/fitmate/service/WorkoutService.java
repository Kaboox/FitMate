package kabox.fitmate.service;

import kabox.fitmate.dto.AiImportRequest;
import kabox.fitmate.Model.*;
import kabox.fitmate.Repository.ExerciseRepository;
import kabox.fitmate.Repository.UserRepository;
import kabox.fitmate.Repository.WorkoutRepository;
import kabox.fitmate.enums.WorkoutSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;


    public WorkoutService(WorkoutRepository workoutRepository,
                          ExerciseRepository exerciseRepository,
                          UserRepository userRepository) {
        this.workoutRepository = workoutRepository;
        this.exerciseRepository = exerciseRepository;
        this.userRepository = userRepository;
    }

    /**
     * MAIN AI METHOD
     * Takes user's ID and DTO from JSON.
     */
    @Transactional
    public Workout saveWorkoutFromAi(Long userId, AiImportRequest requestDto) {

        // Find User
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create Workout object
        Workout workout = new Workout();
        workout.setUser(user);
        workout.setName(requestDto.getTitle() != null ? requestDto.getTitle() : "Imported workout");
        workout.setNote(requestDto.getNotes());
        workout.setSource(WorkoutSource.AI_IMPORT); // SET AI FLAG

        // Parse date, AI should return "yyyy-MM-dd")
        if (requestDto.getDate() != null) {
            workout.setDate(LocalDate.parse(requestDto.getDate()).atStartOfDay());
        } else {
            workout.setDate(LocalDateTime.now());
        }

        // Iterate over json's exercises
        for (AiImportRequest.ExerciseDto exDto : requestDto.getExercises()) {

            WorkoutExercise workoutExercise = new WorkoutExercise();
            workoutExercise.setWorkoutPlan(workout); // Wiążemy z rodzicem

            // NAME MATCHING
            Optional<Exercise> dbExercise = exerciseRepository.findByNameIgnoreCase(exDto.getExerciseName());

            if (dbExercise.isPresent()) {
                // Success
                workoutExercise.setExercise(dbExercise.get());
            } else {
                // Not found
                workoutExercise.setTempName(exDto.getExerciseName());
            }

            // Iterate over exercise's sets
            for (AiImportRequest.SetDto setDto : exDto.getSets()) {
                WorkoutSet workoutSet = new WorkoutSet();
                workoutSet.setReps(setDto.getReps());
                workoutSet.setWeight(setDto.getWeight());

                workoutSet.setWorkoutExercise(workoutExercise);

                workoutExercise.getSets().add(workoutSet);
            }

            workout.getExercises().add(workoutExercise);
        }

        return workoutRepository.save(workout);
    }
}