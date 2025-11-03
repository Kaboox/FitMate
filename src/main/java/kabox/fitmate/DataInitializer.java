package kabox.fitmate;

import kabox.fitmate.Model.Exercise;
import kabox.fitmate.Model.Muscle; 
import kabox.fitmate.Model.Role;
import kabox.fitmate.Model.User;
import kabox.fitmate.Repository.ExerciseRepository;
import kabox.fitmate.Repository.MuscleRepository; 
import kabox.fitmate.Repository.UserRepository;
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
                e1.setVideoUrl("https://www.youtube.com/embed/gRVjAtPip0Y?si=5A9GjxxHxzH5M55_");
                e1.setImageUrl("https://images.pexels.com/photos/3837781/pexels-photo-3837781.jpeg"); 
                e1.setPrimaryMuscle(chest);
                e1.setSecondaryMuscles(List.of(shoulders, triceps));
                e1.setMistakes(List.of("Flaring elbows too wide", "Bouncing the bar off the chest", "Not retracting scapula"));

                Exercise e2 = new Exercise();
                e2.setName("Barbell Squat");
                e2.setDescription("A compound, full-body exercise that trains primarily the muscles of the thighs, hips and buttocks, quadriceps femoris muscle, hamstrings, as well as strengthening the bones, ligaments and insertion of the tendons throughout the lower body.");
                e2.setVideoUrl("https://www.youtube.com/embed/q1fCgfieNEs?si=uVF9xFiSWPlzjkuy");
                e2.setImageUrl("https://images.pexels.com/photos/371049/pexels-photo-371049.jpeg"); 
                e2.setPrimaryMuscle(legs);
                e2.setSecondaryMuscles(List.of(core));
                e2.setMistakes(List.of("Knees caving in", "Not going deep enough (below parallel)", "Rounding the lower back"));

                Exercise e3 = new Exercise();
                e3.setName("Deadlift");
                e3.setDescription("A compound exercise in which a loaded barbell is lifted off the ground to the level of the hips, torso perpendicular to the floor, before being placed back on the ground. It works the posterior chain, including the back, hamstrings, and glutes.");
                e3.setVideoUrl("https://www.youtube.com/embed/AweC3UaM14o?si=R2sUxox7h_vA2T9S");
                e3.setImageUrl("https://images.pexels.com/photos/5837267/pexels-photo-5837267.jpeg"); 
                e3.setPrimaryMuscle(back);
                e3.setSecondaryMuscles(List.of(legs, core));
                e3.setMistakes(List.of("Rounding the lower back", "Jerking the bar off the floor", "Hyperextending the back at the top"));

                exerciseRepository.saveAll(List.of(e1, e2, e3));
                System.out.println(exerciseRepository.count() + " ćwiczeń dodanych do bazy.");
            }
        };
    }
}

