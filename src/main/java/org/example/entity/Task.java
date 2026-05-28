package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tasks")
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status = TaskStatus.TODO;


    // Связь с проектом
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    // Связь с пользователем
    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User assignee;
}








