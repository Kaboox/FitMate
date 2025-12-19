package kabox.fitmate.Repository;

import kabox.fitmate.Model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
}
