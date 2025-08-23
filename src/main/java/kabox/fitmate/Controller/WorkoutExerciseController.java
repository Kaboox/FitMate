package kabox.fitmate.Controller;

import jakarta.persistence.EntityNotFoundException;
import kabox.fitmate.Model.Exercise;
import kabox.fitmate.Model.WorkoutExercise;
import kabox.fitmate.Model.WorkoutPlan;
import kabox.fitmate.Repository.ExerciseRepository;
import kabox.fitmate.Repository.WorkoutExerciseRepository;
import kabox.fitmate.Repository.WorkoutPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workout-exercise")
public class WorkoutExerciseController {

    @Autowired
    private WorkoutExerciseRepository workoutExerciseRepository;

    @Autowired
    private WorkoutPlanRepository workoutPlanRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @PostMapping("/{planId}")
    public ResponseEntity<WorkoutExercise> addExerciseToPlan(
            @PathVariable Long planId,
            @RequestParam Long exerciseId,
            @RequestBody WorkoutExercise workoutExercise) {

        WorkoutPlan plan = workoutPlanRepository.findById(planId)
                .orElseThrow(() -> new EntityNotFoundException("Plan not found"));

        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new EntityNotFoundException("Exercise not found"));

        workoutExercise.setWorkoutPlan(plan);
        workoutExercise.setExercise(exercise);

        return ResponseEntity.ok(workoutExerciseRepository.save(workoutExercise));
    }

    @DeleteMapping("/{exerciseId}")
    public ResponseEntity<?> removeExercise(@PathVariable Long exerciseId) {
        if (!workoutExerciseRepository.existsById(exerciseId)) {
            return ResponseEntity.notFound().build();
        }
        workoutExerciseRepository.deleteById(exerciseId);
        return ResponseEntity.noContent().build();
    }
}
