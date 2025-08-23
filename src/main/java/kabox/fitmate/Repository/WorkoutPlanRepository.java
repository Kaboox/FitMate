package kabox.fitmate.Repository;

import kabox.fitmate.Model.User;
import kabox.fitmate.Model.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Long> {
    Optional<WorkoutPlan> findByName(String name);

    List<WorkoutPlan> findByUser(User user);

}
