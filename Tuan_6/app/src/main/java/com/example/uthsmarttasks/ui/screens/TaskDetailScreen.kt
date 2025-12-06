package com.example.uthsmarttasks.ui.screens

import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uthsmarttasks.models.Task
import com.example.uthsmarttasks.models.Subtask
import com.example.uthsmarttasks.models.Attachment
import com.example.uthsmarttasks.models.Reminder
import com.example.uthsmarttasks.viewmodel.TaskViewModel
import com.example.uthsmarttasks.viewmodel.UiState

// Helper extension
fun Any?.asString(): String = this?.toString() ?: "N/A"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    taskId: String,
    onBackClick: () -> Unit,
    viewModel: TaskViewModel = viewModel()
) {
    val taskState by viewModel.taskDetailState.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }

    // ⭐ Lấy Context hợp lệ trong Composable
    val context = LocalContext.current

    LaunchedEffect(taskId) { viewModel.loadTaskDetail(taskId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFE57373))
                    }
                }
            )
        }
    ) { padding ->
        when (taskState) {
            is UiState.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }

            is UiState.Success -> {
                val task = (taskState as UiState.Success<Task>).data
                TaskDetailContent(task = task, modifier = Modifier.padding(padding))
            }

            is UiState.Error -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text((taskState as UiState.Error).message.asString(), color = Color.Red)
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Xóa task") },
            text = { Text("Bạn có chắc chắn muốn xóa task này?") },
            confirmButton = {
                TextButton(onClick = {
                    // Đóng Dialog ngay khi bắt đầu gọi API
                    showDeleteDialog = false

                    viewModel.deleteTask(taskId,
                        onSuccess = {
                            // Nếu thành công, quay lại màn hình trước
                            onBackClick()
                        },
                        onError = { errorMessage ->
                            // ⭐ Sử dụng Context đã lấy để hiển thị Toast
                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    )
                }) {
                    Text("Xóa", color = Color.Red)
                }

            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) { Text("Hủy") }
            }
        )
    }
}

@Composable
fun TaskDetailContent(task: Task, modifier: Modifier = Modifier) {
    // ... (Phần nội dung này không cần sửa)
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Hình ảnh
        task.desImageURL?.let {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = "Task Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
        }

        // Title
        Text(task.title.asString(), fontSize = 24.sp, fontWeight = FontWeight.Bold)

        // Description
        Text(task.description.asString(), fontSize = 14.sp, color = Color.Gray)

        // Info Chips
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            InfoChip(Icons.Default.DateRange, "Category", task.category)
            InfoChip(Icons.Default.Info, "Status", task.status)
            InfoChip(Icons.Default.Star, "Priority", task.priority)
            InfoChip(Icons.Default.Schedule, "Due Date", task.dueDate)
        }

        // Subtasks
        if (!task.subtasks.isNullOrEmpty()) {
            Text("Subtasks", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            task.subtasks.forEach { subtask: Subtask ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = subtask.isCompleted, onCheckedChange = null)
                    Text(subtask.title.asString(), modifier = Modifier.padding(start = 8.dp))
                }
            }
        }

        // Attachments
        if (!task.attachments.isNullOrEmpty()) {
            Text("Attachments", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            task.attachments.forEach { attachment: Attachment ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.AttachFile, contentDescription = null, tint = Color.Gray)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(attachment.fileName.asString())
                }
            }
        }

        // Reminders
        if (!task.reminders.isNullOrEmpty()) {
            Text("Reminders", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            task.reminders.forEach { reminder: Reminder ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Schedule, contentDescription = null, tint = Color.Gray)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("${reminder.time.asString()} (${reminder.type.asString()})")
                }
            }
        }
    }
}

@Composable
fun InfoChip(icon: ImageVector, label: String, value: Any?) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = label, modifier = Modifier.size(20.dp), tint = Color.DarkGray)
            Spacer(modifier = Modifier.height(4.dp))
            Text(label, fontSize = 10.sp, color = Color.Gray)
            Text(value.asString(), fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}