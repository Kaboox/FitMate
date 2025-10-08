package kabox.fitmate.Controller;


import kabox.fitmate.dto.WorkoutTemplateRequest;
import kabox.fitmate.dto.WorkoutTemplateResponse;
import kabox.fitmate.security.CustomUserDetails;
import kabox.fitmate.service.WorkoutTemplateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workout-template")
public class WorkoutTemplateController {

    private final WorkoutTemplateService workoutTemplateService;

    public WorkoutTemplateController(WorkoutTemplateService workoutTemplateService) {
        this.workoutTemplateService = workoutTemplateService;
    }

    @GetMapping("/me")
    public ResponseEntity<List<WorkoutTemplateResponse>> getTemplates(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).build(); // NOT LOGGED IN
        }

        Long userId = userDetails.getUser().getId();
        List<WorkoutTemplateResponse> templates = workoutTemplateService.getTemplates(userId);
        return ResponseEntity.ok(templates);
    }

    @PostMapping
    public ResponseEntity<WorkoutTemplateResponse> createTemplate(@RequestBody WorkoutTemplateRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {

        WorkoutTemplateResponse response = workoutTemplateService.createTemplate(request, userDetails.getUser().getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
