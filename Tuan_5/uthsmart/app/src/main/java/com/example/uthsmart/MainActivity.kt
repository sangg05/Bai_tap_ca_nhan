package com.example.uthsmart

import android.os.Bundle
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.example.uthsmart.RC_SIGN_IN
import com.example.uthsmart.handleSignInResult

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    // Khai báo NavController để có thể truy cập trong onActivityResult
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        setContent {
            // Sửa lại cách gọi để lấy NavController
            // Hàm AppNavHost cần được sửa để trả về hoặc thiết lập NavController
            navController = AppNavHost(auth) // Giả định AppNavHost trả về NavController
        }
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Bắt kết quả từ Google Sign-In
        if (requestCode == RC_SIGN_IN) {
            // Gọi hàm xử lý và chuyển màn hình
            handleSignInResult(data, auth, navController, this)
        }
    }
}
