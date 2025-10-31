package com.example.uthsmart

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import androidx.navigation.NavController // Import NavController

@Composable
fun AppNavHost(auth: FirebaseAuth): NavController { //
    val navController = rememberNavController()
    NavHost(navController, startDestination = "login") {
        composable("login") { LoginScreen(navController, auth) }
        composable("profile") { ProfileScreen(auth, navController) }
    }
    return navController //
}