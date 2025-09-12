package com.devtabs.tasks.services;

import com.devtabs.tasks.domain.entities.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskService {
    List<Task> listTasks(UUID taskListId);
    Task createTask(UUID taskListId,Task task);
    Optional<Task> getTask(UUID taskListId, UUID taskId);

}
