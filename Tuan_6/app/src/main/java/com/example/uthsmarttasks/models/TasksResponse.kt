package com.example.uthsmarttasks.models

data class TasksResponse(
    val isSuccess: Boolean,
    val message: String,
    val data: List<Task>
)

// Dành cho chi tiết task
data class TaskDetailResponse(
    val isSuccess: Boolean,
    val message: String,
    val data: Task
)
