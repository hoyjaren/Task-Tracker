package com.devtabs.tasks.domain.dto;

import com.devtabs.tasks.domain.entities.Task;
import com.sun.jdi.DoubleValue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record TaskListDto(
        UUID id,
        String title,
        String description,
        Integer count,
        Double progress,
        List<TaskDto> tasks
) {
}
