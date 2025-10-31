package com.example.uthsmart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.* // Cần có remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn //
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(auth: FirebaseAuth, navController: NavController) {
    val user = auth.currentUser
    val context = LocalContext.current // <<< Lấy context
    val photoUrl = user?.photoUrl?.toString()

    // 1. Cấu hình GSO và Khai báo googleSignInClient
    val gso = remember {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("284726775711-gd8kkep31mvap4pf5ll660p5q5gfejct.apps.googleusercontent.com")
            .requestEmail()
            .build()
    }
    val googleSignInClient = remember {
        GoogleSignIn.getClient(context, gso)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Hồ Sơ", fontWeight = FontWeight.SemiBold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // ... (Phần hiển thị Avatar không đổi) ...
            val avatarModifier = Modifier
                .size(80.dp)
                .padding(top = 16.dp, bottom = 24.dp)
                .clip(CircleShape)

            if (photoUrl.isNullOrEmpty()) {
                Surface(
                    modifier = avatarModifier,
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Default Avatar",
                        modifier = Modifier.fillMaxSize(0.7f),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            } else {
                AsyncImage(
                    model = photoUrl,
                    contentDescription = "User Avatar",
                    modifier = avatarModifier,
                    contentScale = ContentScale.Crop,
                )
            }
            // ... (Phần Card thông tin người dùng không đổi) ...
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    user?.let {
                        Text(
                            text = it.displayName ?: "Không có Tên",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Divider()
                        Text(
                            text = "Email: ${it.email ?: "Không có"}",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    } ?: run {
                        Text("Không tìm thấy thông tin người dùng.")
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                        // 1. Đăng xuất Google cục bộ để buộc chọn lại tài khoản
                    googleSignInClient.signOut().addOnCompleteListener {
                        // 2. Đăng xuất khỏi Firebase
                        auth.signOut()

                        // 3. Điều hướng về màn hình đăng nhập và xóa back stack
                        navController.navigate("login") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.ExitToApp, contentDescription = "Sign Out", modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Đăng Xuất", fontSize = 16.sp)
                }
            }
        }
    }
}