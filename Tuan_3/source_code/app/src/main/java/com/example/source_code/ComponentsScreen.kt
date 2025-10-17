package com.example.source_code

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ComponentsScreen(navController: NavController) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = "UI Components List",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp)
            )

            Section("Display", listOf(
                "Text" to "Displays text",
                "Image" to "Displays an image"
            ), navController)

            Section("Input", listOf(
                "TextField" to "Input field for text",
                "PasswordField" to "Input field for passwords"
            ), navController)

            Section("Layout", listOf(
                "Column" to "Arranges elements vertically",
                "Row" to "Arranges elements horizontally"
            ), navController)
        }
    }
}

@Composable
fun Section(title: String, items: List<Pair<String, String>>, navController: NavController) {
    Text(
        text = title,
        fontSize = 15.sp,
        color = Color.Black,
        modifier = Modifier.padding(vertical = 8.dp)
    )
    items.forEach { (name, desc) ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFFD0E8FF),
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable { navController.navigate("detail/$name") }
                .padding(12.dp)
        ) {
            Column {
                Text(text = name, color = Color.Black)
                Text(text = desc, fontSize = 13.sp, color = Color.DarkGray)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}
