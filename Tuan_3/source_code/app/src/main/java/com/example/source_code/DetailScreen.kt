package com.example.source_code

import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun DetailScreen(navController: NavController, name: String) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        when (name) {
            "Text" -> TextDemoScreen(navController)
            "Image" -> ImageDemoScreen(navController)
            "TextField" -> TextFieldDemoScreen(navController)
            "Row" -> RowDemoScreen(navController)
            "Column" -> ColumnDemoScreen(navController)
            "PasswordField" -> PasswordFieldDemoScreen(navController)
            else -> DefaultScreen(navController, name)
        }
    }
}

@Composable
fun TopBar(title: String, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton(onClick = { navController.popBackStack() }) {
            Text("<", fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun TextDemoScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar("Text Detail", navController)
        Spacer(modifier = Modifier.height(40.dp))
        TextDemo()
    }
}

@Composable
fun TextDemo() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("The ", fontSize = 22.sp)
        Text("quick", textDecoration = TextDecoration.LineThrough, fontSize = 22.sp)
        Text(" Brown", color = MaterialTheme.colorScheme.primary, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text("\nfox j u m p s ", fontSize = 22.sp)
        Text(" over ", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text("the ", fontSize = 22.sp)
        Text("lazy", fontStyle = FontStyle.Italic, fontSize = 22.sp)
        Text(" dog.", fontSize = 22.sp)
    }
}

@Composable
fun ImageDemoScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar("Images", navController)

        Image(
            painter = painterResource(id = R.drawable.school1),
            contentDescription = "UTT1",
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text("https://giaothongvantaitphcm.edu.vn/wp-content/uploads/2025/01/Logo-GTVT.png")

        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.school1),
            contentDescription = "UTT2",
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text("In app")
    }
}

@Composable
fun TextFieldDemoScreen(navController: NavController) {
    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar("TextField", navController)

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Thông tin nhập") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (text.isEmpty()) "Tự động cập nhật dữ liệu theo textfield" else text,
            color = MaterialTheme.colorScheme.error,
            fontSize = 14.sp
        )
    }
}

@Composable
fun RowDemoScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar("Row Layout", navController)
        Spacer(modifier = Modifier.height(16.dp))
        repeat(3) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                repeat(3) { index ->
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .background(
                                if (index == 1)
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                                else
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                            )
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
@Composable
fun ColumnDemoScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar("Column Layout", navController)
        Spacer(modifier = Modifier.height(16.dp))

        // Tạo một hàng chứa nhiều cột
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            repeat(3) { columnIndex ->
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    repeat(3) { index ->
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .background(
                                    if (index == 1)
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                                    else
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                                )
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}
@Composable
fun PasswordFieldDemoScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar("Password Field", navController)
        Spacer(modifier = Modifier.height(32.dp))
        PasswordField()
    }
}

@Composable
fun PasswordField() {
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        onValueChange = { password = it },
        label = { Text("Mật khẩu") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = if (passwordVisible) VisualTransformation.None
        else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        trailingIcon = {
            val icon = if (passwordVisible)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = icon, contentDescription = null)
            }
        }
    )

    Spacer(modifier = Modifier.height(12.dp))

    if (password.isNotEmpty()) {
        Text(
            text = "Mật khẩu bạn nhập: $password",
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun DefaultScreen(navController: NavController, name: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TopBar(name, navController)
        Text("Chưa có giao diện cho: $name")
    }
}
