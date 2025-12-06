package com.example.uthsmarttasks.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uthsmarttasks.models.Task
import com.example.uthsmarttasks.viewmodel.TaskViewModel
import com.example.uthsmarttasks.viewmodel.UiState
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    onTaskClick: (String) -> Unit,
    viewModel: TaskViewModel = viewModel()
) {
    val tasksState by viewModel.tasksState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                "SmartTasks",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Icon(
                                Icons.Default.Star,
                                contentDescription = null,
                                tint = Color(0xFFFFD700),
                                modifier = Modifier.size(16.dp).offset(x = 4.dp)
                            )
                        }
                        Text(
                            "A simple and efficient to-do app",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Add task */ },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        }
    ) { padding ->
        when (tasksState) {
            is UiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is UiState.Success -> {
                val tasks = (tasksState as UiState.Success<List<Task>>).data
                if (tasks.isEmpty()) {
                    EmptyTasksView()
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .padding(horizontal = 16.dp)
                    ) {
                        item { Spacer(modifier = Modifier.height(8.dp)) }
                        items(tasks) { task ->
                            TaskCard(
                                task = task,
                                onClick = { onTaskClick(task.id.toString()) }
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                        item { Spacer(modifier = Modifier.height(80.dp)) }
                    }
                }
            }
            is UiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text((tasksState as UiState.Error).message)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { viewModel.loadTasks() }) {
                            Text("Thử lại")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyTasksView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "📋",
            fontSize = 64.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No Tasks Yet!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Stay productive—add something to do",
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun TaskCard(task: Task, onClick: () -> Unit) {
    val backgroundColor = when (task.status.lowercase()) {
        "in progress" -> Color(0xFFE8A9A9)
        "pending" -> Color(0xFFD4E09B)
        else -> Color(0xFFB3D9E5)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = task.status.lowercase() == "completed",
                    onCheckedChange = null
                )
                Text(
                    text = task.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f)
                )
            }

            task.description?.let {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = it,
                    fontSize = 13.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(start = 48.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 48.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Status: ${task.status}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.DarkGray
                )
                task.dueDate?.let {
                    Text(
                        text = formatDate(it),
                        fontSize = 12.sp,
                        color = Color.DarkGray
                    )
                }
            }
        }
    }
}

fun formatDate(dateString: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("HH:mm dd-MM-yyyy", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        outputFormat.format(date ?: Date())
    } catch (e: Exception) {
        dateString
    }
}
