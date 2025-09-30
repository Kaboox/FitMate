package kabox.fitmate.Repository;

import kabox.fitmate.Model.User;
import kabox.fitmate.Model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkoutPlanRepository extends JpaRepository<Workout, Long> {
    Optional<Workout> findByName(String name);

    List<Workout> findByUser(User user);

}
