package kabox.fitmate;

import kabox.fitmate.Model.Exercise;
import kabox.fitmate.Model.Muscle; 
import kabox.fitmate.Model.Role;
import kabox.fitmate.Model.User;
import kabox.fitmate.repository.ExerciseRepository;
import kabox.fitmate.repository.MuscleRepository; 
import kabox.fitmate.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Configuration
public class DataInitializer {

    
    private Muscle createMuscleIfNotExists(String name, String category, MuscleRepository muscleRepository) {
        Optional<Muscle> existingMuscle = muscleRepository.findByName(name);
        if (existingMuscle.isPresent()) {
            return existingMuscle.get();
        }
        
        return muscleRepository.save(new Muscle(name, category)); 
    }

    @Bean
    public CommandLineRunner initDatabase(
            UserRepository userRepository,
            ExerciseRepository exerciseRepository,
            MuscleRepository muscleRepository, 
            PasswordEncoder passwordEncoder
    ) {
        
        return args -> {
            
            
            if (userRepository.findByUsername("admin").isEmpty()) {
                User adminUser = new User();
                adminUser.setUsername("admin");
                adminUser.setEmail("admin@fitmate.com"); 
                adminUser.setPassword(passwordEncoder.encode("admin123")); 
                adminUser.setRole(Role.ADMIN); 
                adminUser.setAvatarUrl(""); 
                userRepository.save(adminUser);
                System.out.println("Utworzono użytkownika administracyjnego: 'admin' (admin123)");
            }

            
            Muscle chest = createMuscleIfNotExists("Chest", "Upper Body", muscleRepository);
            Muscle back = createMuscleIfNotExists("Back", "Upper Body", muscleRepository);
            Muscle legs = createMuscleIfNotExists("Legs", "Lower Body", muscleRepository);
            Muscle shoulders = createMuscleIfNotExists("Shoulders", "Upper Body", muscleRepository);
            Muscle triceps = createMuscleIfNotExists("Arms", "Upper Body", muscleRepository);
            Muscle biceps = createMuscleIfNotExists("Arms", "Upper Body", muscleRepository);
            Muscle core = createMuscleIfNotExists("Core", "Core", muscleRepository);

           
            if (exerciseRepository.count() == 0) {
                System.out.println("Baza ćwiczeń jest pusta. Dodawanie przykładowych danych...");

                Exercise e1 = new Exercise();
                e1.setName("Bench Press");
                e1.setDescription("A foundational compound exercise that primarily targets the chest muscles (pectorals), as well as the shoulders (anterior deltoids) and triceps.");
                e1.setVideoUrl("https://www.youtube.com/watch?v=vcBig73ojpE");
                e1.setImageUrl("https://i.imgur.com/example-bench.jpg"); 
                e1.setPrimaryMuscle(chest);
                e1.setSecondaryMuscles(List.of(shoulders, triceps));
                e1.setMistakes(List.of("Flaring elbows too wide", "Bouncing the bar off the chest", "Not retracting scapula"));

                Exercise e2 = new Exercise();
                e2.setName("Barbell Squat");
                e2.setDescription("A compound, full-body exercise that trains primarily the muscles of the thighs, hips and buttocks, quadriceps femoris muscle, hamstrings, as well as strengthening the bones, ligaments and insertion of the tendons throughout the lower body.");
                e2.setVideoUrl("https://www.youtube.com/watch?v=ultWZbUmD_M");
                e2.setImageUrl("https://i.imgur.com/example-squat.jpg"); 
                e2.setPrimaryMuscle(legs);
                e2.setSecondaryMuscles(List.of(core));
                e2.setMistakes(List.of("Knees caving in", "Not going deep enough (below parallel)", "Rounding the lower back"));

                Exercise e3 = new Exercise();
                e3.setName("Deadlift");
                e3.setDescription("A compound exercise in which a loaded barbell is lifted off the ground to the level of the hips, torso perpendicular to the floor, before being placed back on the ground. It works the posterior chain, including the back, hamstrings, and glutes.");
                e3.setVideoUrl("https://www.youtube.com/watch?v=D-OYMv2zSSQ");
                e3.setImageUrl("https://i.imgur.com/example-deadlift.jpg"); 
                e3.setPrimaryMuscle(back);
                e3.setSecondaryMuscles(List.of(legs, core));
                e3.setMistakes(List.of("Rounding the lower back", "Jerking the bar off the floor", "Hyperextending the back at the top"));

                exerciseRepository.saveAll(List.of(e1, e2, e3));
                System.out.println(exerciseRepository.count() + " ćwiczeń dodanych do bazy.");
            }
        };
    }
}

