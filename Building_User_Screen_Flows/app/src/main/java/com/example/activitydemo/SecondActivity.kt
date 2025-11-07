package com.example.activitydemo

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class SecondActivity : ComponentActivity() {

    private val TAG = "SecondActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logEvent("onCreate 🎬 — SecondActivity được tạo")

        setContent {
            SecondScreen(onBack = { finish() })
        }
    }

    override fun onStart() {
        super.onStart()
        logEvent("onStart 🌅 — SecondActivity sắp hiển thị")
    }

    override fun onResume() {
        super.onResume()
        logEvent("onResume ▶️ — SecondActivity đang hiển thị")
    }

    override fun onPause() {
        super.onPause()
        logEvent("onPause ⏸️ — SecondActivity sắp bị che (quay về Activity trước)")
    }

    override fun onStop() {
        super.onStop()
        logEvent("onStop 🌙 — SecondActivity không còn hiển thị")
    }

    override fun onDestroy() {
        super.onDestroy()
        logEvent("onDestroy ❌ — SecondActivity bị huỷ")
    }

    private fun logEvent(msg: String) {
        Log.d(TAG, msg)
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun SecondScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("📱 Đây là màn hình 2 (che màn 1)", fontSize = 20.sp, color = Color(0xFF6A1B9A))
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBack) {
            Text("⬅️ Quay lại Activity trước")
        }
    }
}
