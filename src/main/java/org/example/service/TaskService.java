package org.example.service;

import org.example.entity.Project;
import org.example.entity.Task;
import org.example.entity.TaskStatus;
import org.example.entity.User;
import org.example.repository.ProjectRepository;
import org.example.repository.TaskRepository;
import org.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Сложный метод создания (привязка к проекту и юзеру)
    public Task createTask(Task task, Long projectId, Long assigneeId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Проект не найден с id: " + projectId));
        task.setProject(project);

        if (assigneeId != null) {
            User assignee = userRepository.findById(assigneeId)
                    .orElseThrow(() -> new RuntimeException("Пользователь не найден с id: " + assigneeId));
            task.setAssignee(assignee);
        }

        return taskRepository.save(task);
    }

    // Бизнес-логика: нельзя завершить задачу без исполнителя
    public Task updateTaskStatus(Long id, TaskStatus newStatus) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Задача не найдена"));

        if (newStatus == TaskStatus.DONE && task.getAssignee() == null) {
            throw new IllegalStateException("Нельзя завершить задачу (DONE), у которой нет исполнителя!");
        }

        task.setStatus(newStatus);
        return taskRepository.save(task);
    }
}