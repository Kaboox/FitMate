package kabox.fitmate.service;

import kabox.fitmate.Model.User;
import kabox.fitmate.Model.WorkoutTemplate;
import kabox.fitmate.Model.WorkoutTemplateExercise;
import kabox.fitmate.Repository.UserRepository;
import kabox.fitmate.Repository.WorkoutTemplateExerciseRepository;
import kabox.fitmate.Repository.WorkoutTemplateRepository;
import kabox.fitmate.dto.WorkoutTemplateRequest;
import kabox.fitmate.dto.WorkoutTemplateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Service
public class WorkoutTemplateService {

    private WorkoutTemplateRepository workoutTemplateRepository;
    private WorkoutTemplateExerciseRepository workoutTemplateExerciseRepository;
    private UserRepository userRepository;

    @Autowired
    public WorkoutTemplateService(WorkoutTemplateRepository workoutTemplateRepository, WorkoutTemplateExerciseRepository workoutTemplateExerciseRepository, UserRepository userRepository) {
        this.workoutTemplateRepository = workoutTemplateRepository;
        this.workoutTemplateExerciseRepository = workoutTemplateExerciseRepository;
        this.userRepository = userRepository;
    }

    public WorkoutTemplateResponse createTemplate(WorkoutTemplateRequest workoutTemplateRequest, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("User with ID " + userId + " does not exist");
        }
        User user = optionalUser.get();

        // CREATE TEMPLATE

    }




}
