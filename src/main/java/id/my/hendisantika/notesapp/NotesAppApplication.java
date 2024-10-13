package id.my.hendisantika.notesapp;

import id.my.hendisantika.notesapp.model.ERole;
import id.my.hendisantika.notesapp.model.Role;
import id.my.hendisantika.notesapp.repository.RoleRepository;
import id.my.hendisantika.notesapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NotesAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotesAppApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(RoleRepository roleRepository, UserRepository userRepository) {
        return (args) -> {
            userRepository.deleteAll();
            roleRepository.deleteAll();
            roleRepository.save(new Role(1, ERole.ROLE_ADMIN));
            roleRepository.save(new Role(1, ERole.ROLE_USER));
            roleRepository.save(new Role(1, ERole.ROLE_MODERATOR));
        };
    }
}
