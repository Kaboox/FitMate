package kabox.fitmate.service;

import kabox.fitmate.Model.Exercise;
import kabox.fitmate.Model.User;
import kabox.fitmate.Model.WorkoutTemplate;
import kabox.fitmate.Model.WorkoutTemplateExercise;
import kabox.fitmate.Repository.ExerciseRepository;
import kabox.fitmate.Repository.UserRepository;
import kabox.fitmate.Repository.WorkoutTemplateExerciseRepository;
import kabox.fitmate.Repository.WorkoutTemplateRepository;
import kabox.fitmate.dto.WorkoutTemplateExerciseRequest;
import kabox.fitmate.dto.WorkoutTemplateRequest;
import kabox.fitmate.dto.WorkoutTemplateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkoutTemplateService {

    private WorkoutTemplateRepository workoutTemplateRepository;
    private WorkoutTemplateExerciseRepository workoutTemplateExerciseRepository;
    private ExerciseRepository exerciseRepository;
    private UserRepository userRepository;

    @Autowired
    public WorkoutTemplateService(WorkoutTemplateRepository workoutTemplateRepository, WorkoutTemplateExerciseRepository workoutTemplateExerciseRepository, ExerciseRepository exerciseRepository ,UserRepository userRepository) {
        this.workoutTemplateRepository = workoutTemplateRepository;
        this.workoutTemplateExerciseRepository = workoutTemplateExerciseRepository;
        this.exerciseRepository = exerciseRepository;
        this.userRepository = userRepository;
    }

    public WorkoutTemplateResponse createTemplate(WorkoutTemplateRequest workoutTemplateRequest, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("User with ID " + userId + " does not exist");
        }
        User user = optionalUser.get();

        if (workoutTemplateRequest.getName() == null || workoutTemplateRequest.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Workout template name must not be empty");
        }

        WorkoutTemplate workoutTemplate = new WorkoutTemplate();
        workoutTemplate.setName(workoutTemplateRequest.getName());



        if (!workoutTemplateRequest.getDescription().isEmpty()) {
            workoutTemplate.setDescription(workoutTemplateRequest.getDescription());
        }

        for (WorkoutTemplateExerciseRequest exerciseRequest : workoutTemplateRequest.getExercises()) {
            Exercise exerciseEntity = exerciseRepository.findById(exerciseRequest.getExerciseId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Exercise with ID " + exerciseRequest.getExerciseId() + " not found"));

            WorkoutTemplateExercise exercise = new WorkoutTemplateExercise();
            exercise.setWorkoutTemplate(workoutTemplate);
            exercise.setExercise(exerciseEntity);
            exercise.setSets(exerciseRequest.getSets());
            exercise.setReps(exerciseRequest.getReps());
        }

        workoutTemplate.setUser(user);

        workoutTemplateRepository.save(workoutTemplate);

        return new WorkoutTemplateResponse(workoutTemplate);


    }

    public List<WorkoutTemplateResponse> getTemplates(Long user_id) {
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + user_id + " not found"));

        List<WorkoutTemplate> workoutTemplates = workoutTemplateRepository.findByUserId(user_id);

        return workoutTemplates.stream()
                .map(template -> new WorkoutTemplateResponse(
                        template.getId(),
                        template.getName(),
                        template.getDescription(),
                        template.getTemplateExercises().size()
                ))
                .collect(Collectors.toList());
    }





}
