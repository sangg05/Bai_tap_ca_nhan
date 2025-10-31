package com.example.uthsmart

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

const val RC_SIGN_IN = 9001

@Composable
fun LoginScreen(navController: NavController, auth: FirebaseAuth) {
    val context = LocalContext.current

    // Kiểm tra xem người dùng đã đăng nhập chưa khi app khởi động
    if (auth.currentUser != null) {
        LaunchedEffect(key1 = Unit) {
            navController.navigate("profile") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Lỗi Image bị đỏ: Đã đảm bảo import Image và painterResource
        Image(
            painter = painterResource(id = R.drawable.uthsmart_logo),
            contentDescription = "UTH SmartTasks Logo",
            modifier = Modifier.size(150.dp)
        )
        Text("Welcome to UTH SmartTasks")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("284726775711-gd8kkep31mvap4pf5ll660p5q5gfejct.apps.googleusercontent.com")
                .requestEmail()
                .build()
            val googleSignInClient = GoogleSignIn.getClient(context, gso)
            val signInIntent = googleSignInClient.signInIntent
            (context as? Activity)?.startActivityForResult(signInIntent, RC_SIGN_IN) // Dùng as? Activity an toàn hơn
        }) {
            Text("SIGN IN WITH GOOGLE")
        }
    }
}

/**
 * Xử lý kết quả trả về từ màn hình đăng nhập Google.
 * Hàm này cần được gọi từ onActivityResult/Activity Result API của MainActivity.
 */
fun handleSignInResult(data: android.content.Intent?, auth: FirebaseAuth, navController: NavController, context: Activity) {
    val task = GoogleSignIn.getSignedInAccountFromIntent(data)
    try {
        val account = task.getResult(Exception::class.java)
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                navController.navigate("profile") {
                    popUpTo("login") { inclusive = true } // Xóa màn hình Login khỏi stack
                }
            } else {
                Toast.makeText(context, "Đăng nhập Firebase thất bại", Toast.LENGTH_SHORT).show()
            }
        }
    } catch (e: Exception) {
        Toast.makeText(context, "Đăng nhập Google thất bại: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}