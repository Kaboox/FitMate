package kabox.fitmate.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;
    private String name;

    @Enumerated(EnumType.STRING)
    private Role role; // ADMIN / USER / PREMIUM

    @OneToMany(mappedBy = "user")
    private List<WorkoutPlan> workoutPlans;

}
