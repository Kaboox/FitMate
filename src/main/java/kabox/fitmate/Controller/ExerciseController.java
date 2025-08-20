package kabox.fitmate.Controller;

import jakarta.persistence.EntityNotFoundException;
import kabox.fitmate.Model.Exercise;
import kabox.fitmate.Model.Muscle;
import kabox.fitmate.Repository.ExerciseRepository;
import kabox.fitmate.Repository.MuscleRepository;
import kabox.fitmate.dto.ExerciseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private MuscleRepository muscleRepository;

    // Dodanie ćwiczenia
    @PostMapping
    public ResponseEntity<Exercise> addExercise(@RequestBody ExerciseRequest request) {
        // --- Primary ---
        Muscle primaryMuscle = muscleRepository.findById(request.getPrimaryMuscleId())
                .orElseThrow(() -> new EntityNotFoundException("Primary muscle not found"));

        Exercise exercise = new Exercise();
        exercise.setName(request.getName());
        exercise.setDescription(request.getDescription());
        exercise.setVideoUrl(request.getVideoUrl());
        exercise.setPrimaryMuscle(primaryMuscle);

        // --- Secondary (opcjonalne) ---
        if (request.getSecondaryMuscleIds() != null && !request.getSecondaryMuscleIds().isEmpty()) {
            List<Muscle> secondaryMuscles = request.getSecondaryMuscleIds().stream()
                    .map(id -> muscleRepository.findById(id)
                            .orElseThrow(() -> new EntityNotFoundException("Secondary muscle not found: " + id)))
                    .collect(Collectors.toList());
            exercise.setSecondaryMuscles(secondaryMuscles);
        }

        Exercise saved = exerciseRepository.save(exercise);
        return ResponseEntity.ok(saved);
    }

    // Pobranie wszystkich ćwiczeń
    @GetMapping
    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAll();
    }

    // Pobranie ćwiczenia po ID
    @GetMapping("/{id}")
    public ResponseEntity<Exercise> getExerciseById(@PathVariable Long id) {
        return exerciseRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
