package kabox.fitmate.Repository;

import kabox.fitmate.Model.Exercise;
import kabox.fitmate.Model.Muscle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    Optional<Exercise> findByName(String name);

    // Cwiczenia po czesci nazwy np barbell - barbell row
    List<Exercise> findByNameContainingIgnoreCase(String fragment);

    List<Exercise> findByMuscle(Muscle muscle);
}
