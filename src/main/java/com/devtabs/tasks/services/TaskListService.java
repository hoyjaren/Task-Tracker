package com.devtabs.tasks.services;

import com.devtabs.tasks.domain.entities.TaskList;

import java.util.List;

public interface TaskListService {
    List<TaskList> listTaskLists();
}
