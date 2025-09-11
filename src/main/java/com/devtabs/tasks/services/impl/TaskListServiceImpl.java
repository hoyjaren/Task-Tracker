package com.devtabs.tasks.services.impl;

import com.devtabs.tasks.domain.entities.TaskList;
import com.devtabs.tasks.repositories.TaskListRepositories;
import com.devtabs.tasks.repositories.TaskRepositories;
import com.devtabs.tasks.services.TaskListService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskListServiceImpl implements TaskListService {

    private final TaskListRepositories taskListRepositories;

    public TaskListServiceImpl(TaskListRepositories taskListRepositories) {
        this.taskListRepositories = taskListRepositories;
    }

    @Override
    public List<TaskList> listTaskLists() {
        return taskListRepositories.findAll();
    }

    @Override
    public TaskList createTaskList(TaskList taskList) {
        if (null != taskList.getId()){
            throw new IllegalArgumentException("Task list already has an ID");
        }
        if (null == taskList.getTitle() || taskList.getTitle().isBlank()){
            throw new IllegalArgumentException("Task list title must be present!");
        }
        LocalDateTime now = LocalDateTime.now();
        return taskListRepositories.save(new TaskList(
                null,
                taskList.getTitle(),
                taskList.getDescription(),
                null,
                now,
                now
        ));
    }

    @Override
    public Optional<TaskList> getTaskList(UUID id) {
        return taskListRepositories.findById(id);
    }

    @Override
    public TaskList updateTaskList(UUID taskListId, TaskList taskList) {
        if (null == taskList.getId()){
            throw new IllegalArgumentException("Task list must have an ID");
        }

        if(!Objects.equals(taskList.getId(), taskListId)) {
            throw new IllegalArgumentException("Attempting to change task list ID, this is not permitted!");
        }

        TaskList existingTaskList = taskListRepositories.findById(taskListId).orElseThrow(() ->
                new IllegalArgumentException("Task list not found!"));

        existingTaskList.setTitle(taskList.getTitle());
        existingTaskList.setDescription(taskList.getDescription());
        existingTaskList.setUpdated(LocalDateTime.now());
        return taskListRepositories.save(existingTaskList);
    }
}
