package kabox.fitmate.Controller;


import jakarta.persistence.EntityNotFoundException;
import kabox.fitmate.Model.*;
import kabox.fitmate.Repository.ExerciseRepository;
import kabox.fitmate.Repository.UserRepository;
import kabox.fitmate.Repository.WorkoutExerciseRepository;
import kabox.fitmate.Repository.WorkoutPlanRepository;
import kabox.fitmate.dto.WorkoutExerciseRequest;
import kabox.fitmate.dto.WorkoutPlanRequest;
import kabox.fitmate.dto.WorkoutSetRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/workout-plan")
public class WorkoutPlanController {

    @Autowired
    private WorkoutPlanRepository workoutPlanRepository;

    @Autowired
    private WorkoutExerciseRepository workoutExerciseRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private UserRepository userRepository;


    // Get all plans of logged user
    @GetMapping
    public List<Workout> workoutPlanList() {
        return workoutPlanRepository.findAll();
    }

    // Get details of a specific workout plan
    @GetMapping("/{id}")
    public ResponseEntity<Workout> getMuscleById(@PathVariable Long id) {
        Workout workout = workoutPlanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Muscle not found"));

        return ResponseEntity.ok(workout);
    }

    @PostMapping
    public ResponseEntity<Workout> addWorkoutPlan(@RequestBody WorkoutPlanRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Workout plan = new Workout();
        plan.setName(request.getName());
        plan.setUser(user);

        List<WorkoutExercise> exercises = new ArrayList<>();
        for (WorkoutExerciseRequest exReq : request.getExercises()) {
            Exercise exercise = exerciseRepository.findById(exReq.getExerciseId())
                    .orElseThrow(() -> new EntityNotFoundException("Exercise not found."));

            WorkoutExercise workoutExercise = new WorkoutExercise();
            workoutExercise.setExercise(exercise);
            workoutExercise.setWorkoutPlan(plan);

            List<WorkoutSet> sets = new ArrayList<>();
            for (WorkoutSetRequest setReq: exReq.getSets()) {
                WorkoutSet set = new WorkoutSet();
                set.setReps(setReq.getReps());
                set.setWeight(setReq.getWeight());
                set.setWorkoutExercise(workoutExercise);
                sets.add(set);
            }

            workoutExercise.setSets(sets);
            exercises.add(workoutExercise);
        }

        plan.setExercises(exercises);

        Workout savedPlan = workoutPlanRepository.save(plan); // zapis planu i ćwiczeń dzięki CascadeType.ALL

        return ResponseEntity.ok(savedPlan);


    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateWorkoutPlan(@PathVariable Long id, @RequestBody Workout updateWorkout) {
        return workoutPlanRepository.findById(id)
                .map(existingWorkoutPlan -> {
                    if (updateWorkout.getName() != null && !updateWorkout.getName().isBlank()) {
                        existingWorkoutPlan.setName(updateWorkout.getName());
                    }

                    Workout saved = workoutPlanRepository.save(existingWorkoutPlan);

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
