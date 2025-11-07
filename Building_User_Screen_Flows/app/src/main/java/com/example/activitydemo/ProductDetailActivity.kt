package com.example.activitydemo

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
class ProductDetailActivity : ComponentActivity() {

    private val productNameState = mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Lấy tên sản phẩm từ intent
        productNameState.value = intent.getStringExtra("productName") ?: "Unknown"
        showToast("Viewing: ${productNameState.value}")

        // Set Compose UI
        setContent { ProductDetailScreen(productNameState) }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // Cập nhật state khi có intent mới
        val newProduct = intent.getStringExtra("productName") ?: "Unknown"
        productNameState.value = newProduct
        showToast("onNewIntent: $newProduct")
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(productNameState: MutableState<String>) {
    val productName by productNameState
    val bg = Color(0xFFF1F8E9)
    val text = Color(0xFF004D40)
    val context = androidx.compose.ui.platform.LocalContext.current
    Scaffold(
        containerColor = bg,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(productName) },
                navigationIcon = {
                    IconButton(onClick = { (context as? ComponentActivity)?.finish() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = text)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFB2DFDB),
                    titleContentColor = text
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(bg)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Chi tiết sản phẩm: $productName", fontSize = 20.sp, color = text)
            Spacer(Modifier.height(20.dp))
            Button(
                onClick = {
                    // Thêm sản phẩm vào CartData
                    CartData.addItem(productName)

                    // Mở CartActivity
                    val intent = Intent(context, CartActivity::class.java)
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4DB6AC))
            ) {
                Text("Thêm vào Giỏ Hàng", color = Color.White)
            }
        }
    }

}
