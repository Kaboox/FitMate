package kabox.fitmate.Controller;

import jakarta.persistence.EntityNotFoundException;
import kabox.fitmate.Model.WorkoutExercise;
import kabox.fitmate.Model.WorkoutSet;
import kabox.fitmate.Repository.WorkoutExerciseRepository;
import kabox.fitmate.Repository.WorkoutSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workout-set")
public class WorkoutSetController {
    @Autowired
    private WorkoutSetRepository workoutSetRepository;

    @Autowired
    private WorkoutExerciseRepository workoutExerciseRepository;

    @PostMapping("/{exerciseId}")
    public ResponseEntity<WorkoutSet> addSetToExercise(
            @PathVariable Long exerciseId,
            @RequestBody WorkoutSet workoutSet) {
        WorkoutExercise workoutExercise = workoutExerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new EntityNotFoundException("WorkoutExercise not found"));

        workoutSet.setWorkoutExercise(workoutExercise);

        return ResponseEntity.ok(workoutSetRepository.save(workoutSet));
    }

    @DeleteMapping("/{setId}")
    public ResponseEntity<?> removeSet(@PathVariable Long setId) {
        if (!workoutSetRepository.existsById(setId)) {
            return ResponseEntity.notFound().build();
        }
        workoutSetRepository.deleteById(setId);
        return ResponseEntity.noContent().build();
    }

}
