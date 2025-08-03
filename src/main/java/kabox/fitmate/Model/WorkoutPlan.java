package kabox.fitmate.Model;

import jakarta.persistence.*;

import java.util.List;


@Entity
public class WorkoutPlan {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
