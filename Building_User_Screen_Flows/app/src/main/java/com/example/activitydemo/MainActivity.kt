package com.example.activitydemo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.activitydemo.ui.theme.ActivityDemoTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ActivityDemoTheme {
                MainScreen { target ->
                    val intent = when (target) {
                        "lifecycle" -> Intent(this, LifecycleDemoActivity::class.java)
                        "state" -> Intent(this, SaveStateActivity::class.java)
                        "intents" -> Intent(this, IntentInteractionActivity::class.java)
                        "tasks" -> Intent(this, LaunchModeActivity::class.java)
                        else -> null
                    }
                    intent?.let { startActivity(it) }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onNavigate: (String) -> Unit) {
    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF6A11CB), Color(0xFF2575FC)) // tím - xanh
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "✨ Building User Screen Flows ✨",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF6A11CB)
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Chọn tính năng: ",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 30.dp)
            )

            val buttons = listOf(
                "Activity Lifecycle" to "lifecycle",
                "Saving & Restoring State" to "state",
                "Intents Interaction" to "intents",
                "Launch Modes & Tasks" to "tasks"
            )

            buttons.forEachIndexed { i, (label, key) ->
                ColorfulButton(
                    text = label,
                    color = when (i) {
                        0 -> Color(0xFFFF6B6B) // đỏ cam
                        1 -> Color(0xFF4ECDC4) // xanh ngọc
                        2 -> Color(0xFFFFC300) // vàng
                        else -> Color(0xFF1DD1A1) // xanh lá
                    },
                    onClick = { onNavigate(key) }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun ColorfulButton(text: String, color: Color, onClick: () -> Unit) {
    var pressed by remember { mutableStateOf(false) }
    val animatedColor by animateColorAsState(
        targetValue = if (pressed) color.copy(alpha = 0.8f) else color,
        label = "buttonColorAnim"
    )

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = animatedColor,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(text = text, fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}
