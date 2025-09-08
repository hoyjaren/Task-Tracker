package com.devtabs.tasks.mappers;

import com.devtabs.tasks.domain.dto.TaskListDto;
import com.devtabs.tasks.domain.entities.TaskList;

public interface TaskListMapper {
    TaskList fromDto(TaskListDto taskListDto);

    TaskListDto toDto(TaskList taskList);
}
