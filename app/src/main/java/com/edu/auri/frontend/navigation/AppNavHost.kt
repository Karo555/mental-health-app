package com.edu.auri.frontend.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavHost(isLogged: Boolean) {
    val navController = rememberNavController()

    // Use the SetupNavigation composable from AppNavigation.kt
    SetupNavigation(navController = navController, isLogged)

    // Add any additional UI elements or composables here
}

@Composable
fun SetupNavigation(navController: NavHostController, isLogged: Boolean) {
    var initScreen = screens.first().route
    if(isLogged){
        initScreen = screens[4].route
    }
}
