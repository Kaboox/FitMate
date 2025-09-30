package kabox.fitmate.Repository;

import kabox.fitmate.Model.WorkoutTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutTemplateRepository extends JpaRepository<WorkoutTemplate, Long> {
}
