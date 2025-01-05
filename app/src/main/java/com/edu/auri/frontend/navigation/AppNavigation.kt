package com.edu.auri.frontend.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.edu.auri.frontend.mainmenu.HomeScreen

sealed class Screen(val route: String) {
    data object HomeScreen : Screen("home_screen")
    data object RegistrationScreen : Screen("registration_screen")
    data object LoginScreen : Screen("login_screen")
    data object WelcomeScreen : Screen("welcome_screen")
    data object MoodScreen : Screen("mood_screen")
    data object SettingsScreen : Screen("settings_screen")
    data object InsightsScreen : Screen("insights_screen")
    data object GratitudeScreen : Screen("gratitude_screen")
}

val  screens = listOf(
    Screen.HomeScreen,
    Screen.RegistrationScreen,
    Screen.LoginScreen,
    Screen.WelcomeScreen,
    Screen.MoodScreen,
    Screen.SettingsScreen,
    Screen.GratitudeScreen,
    Screen.InsightsScreen
)
