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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
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

        List<WorkoutTemplateExercise> exerciseEntities = new ArrayList<>();

        for (WorkoutTemplateExerciseRequest exerciseRequest : workoutTemplateRequest.getExercises()) {
            Exercise exerciseEntity = exerciseRepository.findById(exerciseRequest.getExerciseId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Exercise with ID " + exerciseRequest.getExerciseId() + " not found"));

            WorkoutTemplateExercise templateExercise = new WorkoutTemplateExercise();
            templateExercise.setWorkoutTemplate(workoutTemplate);
            templateExercise.setExercise(exerciseEntity);
            templateExercise.setSets(exerciseRequest.getSets());
            templateExercise.setReps(exerciseRequest.getReps());

            exerciseEntities.add(templateExercise);
        }

        workoutTemplate.setTemplateExercises(exerciseEntities);
        workoutTemplate.setUser(user);

        workoutTemplateRepository.save(workoutTemplate);

        return new WorkoutTemplateResponse(workoutTemplate);


    }

    @Transactional(readOnly = true)
    public List<WorkoutTemplateResponse> getTemplates(Long user_id) {
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + user_id + " not found"));

        List<WorkoutTemplate> workoutTemplates = workoutTemplateRepository.findByUserId(user_id);

        return workoutTemplates.stream()
                .map(WorkoutTemplateResponse::new)
                .collect(Collectors.toList());

    }

    @Transactional
    public WorkoutTemplateResponse updateTemplate(Long templateId, WorkoutTemplateRequest workoutTemplateRequest, Long userId) {
        WorkoutTemplate template = workoutTemplateRepository.findById(templateId)
                .orElseThrow(() -> new IllegalArgumentException("Template with ID " + templateId + " not found"));

        if (!template.getUser().getId().equals(userId)) {
            throw new IllegalStateException("You cannot update someone else's temaplte");
        }

        if (workoutTemplateRequest.getName() != null && !workoutTemplateRequest.getName().isBlank()) {
            template.setName(workoutTemplateRequest.getName());
        }

        if (workoutTemplateRequest.getDescription() != null) {
            template.setDescription(workoutTemplateRequest.getDescription());
        }

        if(workoutTemplateRequest.getExercises() != null) {
            template.getTemplateExercises().clear();

            for (WorkoutTemplateExerciseRequest exReq : workoutTemplateRequest.getExercises()) {
                Exercise exerciseEntity = exerciseRepository.findById(exReq.getExerciseId())
                        .orElseThrow(() -> new IllegalArgumentException("Exercise with ID " + exReq.getExerciseId() + " not found"));

                WorkoutTemplateExercise exercise = new WorkoutTemplateExercise();
                exercise.setWorkoutTemplate(template);
                exercise.setExercise(exerciseEntity);
                exercise.setSets(exReq.getSets());
                exercise.setReps(exReq.getReps());
                template.getTemplateExercises().add(exercise);
            }
        }
        //workoutTemplateRepository.save(template);
        return new WorkoutTemplateResponse(template);

    }

    @Transactional
    public void deleteTemplate(Long templateId, Long userId) {
        WorkoutTemplate template = workoutTemplateRepository.findById(templateId)
                .orElseThrow(() -> new IllegalArgumentException("Template with ID " + templateId + " not found"));

        if (!template.getUser().getId().equals(userId)) {
            throw new IllegalStateException("You cannot delete someone else's template");
        }

        workoutTemplateRepository.delete(template);
    }





}
