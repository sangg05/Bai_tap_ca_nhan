package com.example.activitydemo

import android.os.Bundle
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
import com.example.activitydemo.CartData

@OptIn(ExperimentalMaterial3Api::class)
class CartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { CartScreen() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen() {
    val bg = Color(0xFFFFF3E0)
    val textColor = Color(0xFFE65100)
    val context = androidx.compose.ui.platform.LocalContext.current

    Scaffold(
        containerColor = bg,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("🛒 Giỏ Hàng") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFFFCC80),
                    titleContentColor = textColor
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (CartData.items.isEmpty()) {
                Text("Giỏ hàng trống", color = textColor)
            } else {
                CartData.items.forEach { item ->
                    Text("• $item", color = textColor, fontSize = 18.sp)
                }
            }

            Spacer(Modifier.height(20.dp))

            Button(onClick = { (context as? ComponentActivity)?.finish() }) {
                Text("Quay lại")
            }
        }
    }
}
