package com.example.uthsmarttasks.models

import com.google.gson.annotations.SerializedName

data class Task(
    val id: Int,
    val title: String,
    val description: String?,
    val desImageURL: String?,
    val status: String,
    val priority: String?,
    val category: String?,
    val dueDate: String?,
    val subtasks: List<Subtask>?,
    val attachments: List<Attachment>?,
    val createdAt: String?,
    val updatedAt: String?,
    val reminders: List<Reminder>?
)

data class Subtask(
    val id: Int,
    val title: String,
    val isCompleted: Boolean

)

data class Attachment(
    val id: Int,
    val fileName: String?,
    val fileUrl: String?
)

data class Reminder(
    val id: Int,
    val time: String,
    val type: String
)
data class TaskResponse(
    val isSuccess: Boolean,
    val message: String,
    val data: List<Task>
)


