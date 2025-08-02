package kabox.fitmate.Controller;


import jakarta.persistence.EntityNotFoundException;
import kabox.fitmate.Model.Exercise;
import kabox.fitmate.Model.Muscle;
import kabox.fitmate.Repository.MuscleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

}
