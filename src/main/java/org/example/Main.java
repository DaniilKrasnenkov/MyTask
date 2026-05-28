package org.example;

import org.example.entity.Project;
import org.example.entity.User;
import org.example.repository.ProjectRepository;
import org.example.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner loadTestData(UserRepository userRepository, ProjectRepository projectRepository) {
        return args -> {
            if (userRepository.count() == 0 && projectRepository.count() == 0) {
                User user = new User();
                user.setUsername("daniil_dev");
                user.setEmail("daniil@example.com");
                userRepository.save(user);

                Project project = new Project();
                project.setName("MyTask");
                project.setDescription("Тестовый проект");
                projectRepository.save(project);

                System.out.println("Тестовые данные загружены UserID: 1, ProjectID: 1");
            }
        };
    }
}