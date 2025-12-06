package com.example.uthsmarttasks.viewmodel

import com.example.uthsmarttasks.api.RetrofitClient
import com.example.uthsmarttasks.models.Task

import com.example.uthsmarttasks.viewmodel.TaskViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

class TaskViewModel : ViewModel() {
    private val _tasksState = MutableStateFlow<UiState<List<Task>>>(UiState.Loading)
    val tasksState: StateFlow<UiState<List<Task>>> = _tasksState.asStateFlow()

    private val _taskDetailState = MutableStateFlow<UiState<Task>>(UiState.Loading)
    val taskDetailState: StateFlow<UiState<Task>> = _taskDetailState.asStateFlow()

    init {
        loadTasks()
    }

    fun loadTasks() {
        viewModelScope.launch {
            _tasksState.value = UiState.Loading
            try {
                val response = RetrofitClient.apiService.getTasks()
                if (response.isSuccessful) {
                    val tasks = response.body()?.data ?: emptyList()
                    _tasksState.value = UiState.Success(tasks)
                } else {
                    _tasksState.value = UiState.Error("Lỗi: ${response.code()}")
                }
            } catch (e: Exception) {
                _tasksState.value = UiState.Error("Lỗi kết nối: ${e.message}")
            }
        }
    }

    fun loadTaskDetail(taskId: String) {
        viewModelScope.launch {
            _taskDetailState.value = UiState.Loading
            try {
                val response = RetrofitClient.apiService.getTask(taskId)
                if (response.isSuccessful) {
                    response.body()?.data?.let { task ->
                        _taskDetailState.value = UiState.Success(task)
                    } ?: run {
                        _taskDetailState.value = UiState.Error("Không tìm thấy task")
                    }
                } else {
                    _taskDetailState.value = UiState.Error("Lỗi: ${response.code()}")
                }
            } catch (e: Exception) {
                _taskDetailState.value = UiState.Error("Lỗi kết nối: ${e.message}")
            }
        }
    }

    fun deleteTask(taskId: String, onSuccess: () -> Unit, onError: (String) -> Unit = {}) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.deleteTask(taskId)
                if (response.isSuccessful) {
                    // Nếu muốn, cập nhật local state: reload tasks
                    loadTasks() // 👈 DÒNG NÀY PHẢI ĐẢM BẢO TẢI LẠI DANH SÁCH
                    onSuccess()
                } else {
                    // ...
                }
            } catch (e: Exception) {
                // ...
            }
        }
    }
}
