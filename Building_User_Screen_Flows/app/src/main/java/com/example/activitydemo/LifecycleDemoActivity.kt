package com.example.activitydemo

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
class LifecycleDemoActivity : ComponentActivity() {

    private val TAG = "LifecycleDemo"
    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = mutableStateOf(false)

    /**
     * → Luồng KHÔNG BỊ CHE: bắt đầu tại đây.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logEvent("onCreate 🎬 — Khởi tạo Activity và nhạc")

        mediaPlayer = MediaPlayer.create(this, R.raw.nhac)
        mediaPlayer?.isLooping = true

        setContent {
            LifecycleDemoScreen(
                isPlaying = isPlaying.value,
                onBack = { finish() },
                onOpenSecond = {
                    startActivity(Intent(this, SecondActivity::class.java))
                },
                onExit = { finish() }
            )
        }
    }

    override fun onStart() {
        super.onStart()
        mediaPlayer?.start()
        isPlaying.value = true
        logEvent("onStart 🌅 — Bắt đầu phát nhạc")
    }

    override fun onResume() {
        super.onResume()
        logEvent("onResume ▶️ — Activity hiển thị bình thường (KHÔNG BỊ CHE)")
    }

    override fun onPause() {
        super.onPause()
        logEvent("onPause ⏸️ — Activity sắp bị che khuất")
    }

    override fun onStop() {
        super.onStop()
        mediaPlayer?.pause()
        isPlaying.value = false
        logEvent("onStop 🌙 — Activity không còn hiển thị, tạm dừng nhạc")
    }

    override fun onRestart() {
        super.onRestart()
        logEvent("onRestart 🔁 — Activity quay lại từ nền (BỊ CHE)")
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
        logEvent("onDestroy ❌ — Activity bị huỷ hoàn toàn, giải phóng MediaPlayer")
    }

    private fun logEvent(event: String) {
        Log.d(TAG, event)
        Toast.makeText(this, event, Toast.LENGTH_SHORT).show()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LifecycleDemoScreen(
    isPlaying: Boolean,
    onBack: () -> Unit,
    onOpenSecond: () -> Unit,
    onExit: () -> Unit
) {
    val gradient = Brush.verticalGradient(
        listOf(Color(0xFFB3E5FC), Color(0xFFE1F5FE))
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "🎵 Activity Lifecycle Demo",
                        color = Color(0xFF01579B),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Quay lại",
                            tint = Color(0xFF01579B)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFE1F5FE)
                )
            )
        },
        containerColor = Color.Transparent
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "🧭 3 Luồng chính của vòng đời Activity",
                fontSize = 22.sp,
                color = Color(0xFF01579B),
                fontWeight = FontWeight.Bold
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.8f), shape = MaterialTheme.shapes.medium)
                    .padding(16.dp)
            ) {
                LifecycleStep("🟢 Luồng KHÔNG BỊ CHE",
                    "onCreate → onStart → onResume (hoạt động bình thường).")
                LifecycleStep("🟡 Luồng BỊ CHE",
                    "onPause → onStop → onRestart → onStart → onResume (quay lại từ nền).")
                LifecycleStep("🔴 Luồng BỊ HỦY",
                    "onPause → onStop → onDestroy (thoát hoàn toàn).")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onOpenSecond,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107))
            ) {
                Text("🟡 Mở Activity khác (Bị che)", color = Color.Black)
            }

            Button(
                onClick = onExit,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
            ) {
                Text("🔴 Thoát Activity (Bị huỷ)", color = Color.White)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Image(
                painter = painterResource(id = R.drawable.activity_lifecycle_chart),
                contentDescription = "Activity Lifecycle Diagram",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = if (isPlaying) "🎶 Nhạc đang phát..." else "⏸ Nhạc đã tạm dừng",
                color = Color(0xFF006064),
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun LifecycleStep(title: String, description: String) {
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Text(
            text = title,
            color = Color(0xFF0277BD),
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = description,
            color = Color(0xFF004D40),
            fontSize = 15.sp
        )
    }
}
