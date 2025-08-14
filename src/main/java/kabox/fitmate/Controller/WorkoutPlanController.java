package kabox.fitmate.Controller;


import jakarta.persistence.EntityNotFoundException;
import kabox.fitmate.Model.Muscle;
import kabox.fitmate.Model.WorkoutPlan;
import kabox.fitmate.Repository.WorkoutPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workout-plan")
public class WorkoutPlanController {

    @Autowired
    private WorkoutPlanRepository workoutPlanRepository;


    // Get all plans of logged user
    @GetMapping
    public List<WorkoutPlan> workoutPlanList() {
        return workoutPlanRepository.findAll();
    }

    // Get details of a specific workout plan
    @GetMapping("/{id}")
    public ResponseEntity<WorkoutPlan> getMuscleById(@PathVariable Long id) {
        WorkoutPlan workoutPlan = workoutPlanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Muscle not found"));

        return ResponseEntity.ok(workoutPlan);
    }

    @PostMapping
    public ResponseEntity<WorkoutPlan> addWorkoutPlan(@RequestBody WorkoutPlan workoutPlan) {
        WorkoutPlan savedWorkoutPlan = workoutPlanRepository.save(workoutPlan);
        return ResponseEntity.ok(savedWorkoutPlan);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateWorkoutPlan(@PathVariable Long id, @RequestBody WorkoutPlan updateWorkoutPlan) {
        return workoutPlanRepository.findById(id)
                .map(existingWorkoutPlan -> {
                    if (updateWorkoutPlan.getName() != null && !updateWorkoutPlan.getName().isBlank()) {
                        existingWorkoutPlan.setName(updateWorkoutPlan.getName());
                    }

                    WorkoutPlan saved = workoutPlanRepository.save(existingWorkoutPlan);

                    return ResponseEntity.ok(saved);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWorkoutPlan(@PathVariable Long id) {
        if (!workoutPlanRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        workoutPlanRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
