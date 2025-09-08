package com.devtabs.tasks.services.impl;

import com.devtabs.tasks.domain.entities.TaskList;
import com.devtabs.tasks.repositories.TaskListRepositories;
import com.devtabs.tasks.repositories.TaskRepositories;
import com.devtabs.tasks.services.TaskListService;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
