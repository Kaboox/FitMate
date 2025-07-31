package kabox.fitmate.Model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class Exercise {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;
    private String videoUrl;

    @ManyToOne
    @JoinColumn(name = "muscle_id")
    private Muscle muscle;
}
