package kabox.fitmate.Controller;


import jakarta.persistence.EntityNotFoundException;
import kabox.fitmate.Model.Exercise;
import kabox.fitmate.Model.Muscle;
import kabox.fitmate.Repository.MuscleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/muscles")
public class MuscleController {

    @Autowired
    private MuscleRepository muscleRepository;

    @GetMapping
    public List<Muscle> getAllMuscles() {
        return muscleRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Muscle> getMuscleById(@PathVariable Long id) {
        Muscle muscle = muscleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Muscle not found"));

        return ResponseEntity.ok(muscle);
    }

    @GetMapping("/by-category/{category}")
    public List<Muscle> getMusclesByCategory(@PathVariable String category) {
        return muscleRepository.findByCategory(category);
    }


    @PostMapping
    public ResponseEntity<Muscle> addMuscle(@RequestBody Muscle muscle) {
        Muscle savedMuscle = muscleRepository.save(muscle);
        return ResponseEntity.ok(savedMuscle);
    }

    // --- Dodawanie wielu mięśni naraz ---
    @PostMapping("/batch")
    public List<Muscle> addMuscles(@RequestBody List<Muscle> muscles) {
        return muscleRepository.saveAll(muscles);
    }

}
