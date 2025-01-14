package com.edu.auri.frontend.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.edu.auri.backend.registration.AuthViewModel
import com.edu.auri.frontend.gratitude.GratitudeScreen
import com.edu.auri.frontend.insights.Insights
import com.edu.auri.frontend.login.LoginScreen
import com.edu.auri.frontend.mainmenu.HomeScreen
import com.edu.auri.frontend.setttings.SettingsScreen
import com.edu.auri.frontend.sign_up.RegistrationScreen
import com.edu.auri.frontend.welcomescreen.WelcomeScreen

@Composable
fun AuriNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = "welcome", builder = {
            composable("welcome") {
                WelcomeScreen(modifier, navController, authViewModel)
            }
            composable("home") {
                HomeScreen(modifier, navController, authViewModel)
            }
            composable("signup") {
                RegistrationScreen(modifier, navController, authViewModel)
            }
            composable("login") {
                LoginScreen(modifier, navController, authViewModel)

            }
            composable("insights") {
                Insights()
            }
            composable("settings") {
                SettingsScreen()
            }
            composable("gratitude") {
                GratitudeScreen()
            }
        }
    )

}