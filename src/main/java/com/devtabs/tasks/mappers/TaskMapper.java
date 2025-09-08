package com.devtabs.tasks.mappers;

import com.devtabs.tasks.domain.dto.TaskDto;
import com.devtabs.tasks.domain.entities.Task;

public interface TaskMapper {

    Task fromDto(TaskDto taskDto);

    TaskDto toDto(Task task);
}
