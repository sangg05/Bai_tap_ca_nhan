package com.example.uthsmarttasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.uthsmarttasks.ui.screens.TaskDetailScreen
import com.example.uthsmarttasks.ui.screens.TaskListScreen
import com.example.uthsmarttasks.ui.screens.*
import com.uth.smarttasks.ui.theme.SmartTasksTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartTasksTheme {
                SmartTasksApp()
            }
        }
    }
}

@Composable
fun SmartTasksApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "list"
    ) {
        composable("list") {
            TaskListScreen(
                onTaskClick = { taskId ->
                    navController.navigate("detail/$taskId")
                }
            )
        }
        composable("detail/{taskId}") { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")
            TaskDetailScreen(
                taskId = taskId ?: "",
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}