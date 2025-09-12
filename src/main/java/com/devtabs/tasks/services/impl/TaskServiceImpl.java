package com.devtabs.tasks.services.impl;

import com.devtabs.tasks.domain.entities.Task;
import com.devtabs.tasks.domain.entities.TaskList;
import com.devtabs.tasks.domain.entities.TaskPriority;
import com.devtabs.tasks.domain.entities.TaskStatus;
import com.devtabs.tasks.repositories.TaskListRepositories;
import com.devtabs.tasks.repositories.TaskRepositories;
import com.devtabs.tasks.services.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepositories taskRepositories;
    private final TaskListRepositories taskListRepositories;

    public TaskServiceImpl(TaskRepositories taskRepositories, TaskListRepositories taskListRepositories) {
        this.taskRepositories = taskRepositories;
        this.taskListRepositories = taskListRepositories;
    }

    @Override
    public List<Task> listTasks(UUID taskListId) {
        return taskRepositories.findByTaskListId(taskListId);
    }

    @Transactional
    @Override
    public Task createTask(UUID taskListId, Task task) {
        if (null != task.getId()){
            throw new IllegalArgumentException("Task already has an ID!");
        }
        if (null == task.getTitle() || task.getTitle().isBlank()){
            throw  new IllegalArgumentException("Task must have a title!");
        }

        TaskPriority taskPriority = Optional.ofNullable(task.getPriority())
                .orElse(TaskPriority.MEDIUM);

        TaskStatus taskStatus = TaskStatus.OPEN;

        TaskList taskList = taskListRepositories.findById(taskListId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Task List ID provided!"));

        LocalDateTime now = LocalDateTime.now();
        Task taskToSave = new Task(
                null,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                taskStatus,
                taskPriority,
                taskList,
                now,
                now
        );
        return taskRepositories.save(taskToSave);
    }

    @Override
    public Optional<Task> getTask(UUID taskListId, UUID taskId) {
        return taskRepositories.findByTaskListIdAndId(taskListId, taskId);
    }

    @Transactional
    @Override
    public Task updateTask(UUID taskListId, UUID taskId, Task task) {
        if (null == task.getId()){
            throw new IllegalArgumentException("Task must have an ID");
        }

        if (!Objects.equals(taskId, task.getId())){
            throw new IllegalArgumentException("Task IDs do not match");
        }
        if (null == task.getPriority()){
            throw new IllegalArgumentException("Task must have a valid priority!");
        }
        if (null == task.getStatus()){
            throw new IllegalArgumentException("Task must have a valid status");
        }

        Task existingTask = taskRepositories.findByTaskListIdAndId(taskListId, taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found!"));

        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setPriority(task.getPriority());
        existingTask.setStatus(task.getStatus());
        existingTask.setUpdated(LocalDateTime.now());
        return taskRepositories.save(existingTask);

    }

    @Transactional
    @Override
    public void deleteTask(UUID taskListId, UUID taskId) {
        taskRepositories.deleteByTaskListIdAndId(taskListId, taskId);
    }

}
