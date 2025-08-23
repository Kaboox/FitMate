package kabox.fitmate.Repository;

import kabox.fitmate.Model.Muscle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MuscleRepository extends JpaRepository<Muscle, Long> {
    Optional<Muscle> findByName(String name);

    List<Muscle> findByCategory(String category);


}
