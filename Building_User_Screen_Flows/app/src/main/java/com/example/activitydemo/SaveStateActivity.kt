package com.example.activitydemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class SaveStateActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Màu chủ đạo
            val backgroundColor = Color(0xFFF1F8E9)
            val appBarColor = Color(0xFFB2DFDB)
            val textColor = Color(0xFF004D40)
            val buttonColor = Color(0xFF80CBC4)
            val cardColor = Color(0xFFFFFFFF)

            var count by remember{ mutableStateOf(0) }
            val animatedCount by animateIntAsState(targetValue = count, label = "")
            val snackbarHostState = remember { SnackbarHostState() }
            val scope = rememberCoroutineScope()

            Scaffold(
                snackbarHost = { SnackbarHost(snackbarHostState) },
                containerColor = backgroundColor,
                topBar = {
                    CenterAlignedTopAppBar(
                        title = { Text("Saving & Restoring State") },
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
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = cardColor),
                        elevation = CardDefaults.cardElevation(6.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Current Count",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Medium,
                                color = textColor
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "$animatedCount",
                                fontSize = 48.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1B5E20)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = { count++ },
                            colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                            modifier = Modifier
                                .width(140.dp)
                                .height(50.dp)
                        ) {
                            Text("Increase", fontSize = 18.sp)
                        }

                        Button(
                            onClick = {
                                count = 0
                                scope.launch {
                                    snackbarHostState.showSnackbar("Đã reset giá trị về 0!")
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFAB91)),
                            modifier = Modifier
                                .width(140.dp)
                                .height(50.dp)
                        ) {
                            Text("Reset", fontSize = 18.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Giá trị được lưu khi xoay màn hình hoặc thay đổi cấu hình!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF2E7D32),
                        modifier = Modifier.padding(top = 12.dp),
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}
