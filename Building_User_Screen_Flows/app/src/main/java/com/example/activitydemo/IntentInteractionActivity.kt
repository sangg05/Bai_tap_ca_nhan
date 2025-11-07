package com.example.activitydemo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
class IntentInteractionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val backgroundColor = Color(0xFFF1F8E9) // xanh lá rất nhạt
            val appBarColor = Color(0xFFB2DFDB)     // xanh mint nhạt
            val textColor = Color(0xFF004D40)       // xanh đậm dễ đọc

            var input by remember { mutableStateOf("") }

            Scaffold(
                containerColor = backgroundColor,
                topBar = {
                    CenterAlignedTopAppBar(
                        title = { Text("Activity Interaction with Intents") },
                        navigationIcon = {
                            IconButton(onClick = { finish() }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Back",
                                    tint = textColor
                                )
                            }
                        },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = appBarColor,
                            titleContentColor = textColor
                        )
                    )
                }
            ) { padding ->
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                        .background(backgroundColor)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = input,
                        onValueChange = { input = it },
                        label = { Text("Enter message") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = {
                            val intent = Intent(
                                this@IntentInteractionActivity,
                                ResultActivity::class.java
                            )
                            intent.putExtra("msg", input)
                            startActivity(intent)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Send Intent")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
class ResultActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val msg = intent.getStringExtra("msg")

        setContent {
            val backgroundColor = Color(0xFFE8F5E9)
            val appBarColor = Color(0xFFB2DFDB)
            val textColor = Color(0xFF004D40)

            Scaffold(
                containerColor = backgroundColor,
                topBar = {
                    CenterAlignedTopAppBar(
                        title = { Text("Received Intent") },
                        navigationIcon = {
                            IconButton(onClick = { finish() }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Back",
                                    tint = textColor
                                )
                            }
                        },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = appBarColor,
                            titleContentColor = textColor
                        )
                    )
                }
            ) { padding ->
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                        .background(backgroundColor)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Message: $msg",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(0xFF1B5E20)
                    )
                }
            }
        }
    }
}
