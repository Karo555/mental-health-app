package com.edu.auri.frontend.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.edu.auri.backend.dailylogs.LogDailyViewModel
import com.edu.auri.backend.notes.NotesViewModel
import com.edu.auri.backend.registration.AuthViewModel
import com.edu.auri.frontend.dayoverview.DayOverviewScreen
import com.edu.auri.frontend.history.HistoryScreen
import com.edu.auri.frontend.insights.InsightsScreen
import com.edu.auri.frontend.login.LoginScreen
import com.edu.auri.frontend.menu.HomeScreen
import com.edu.auri.frontend.notes.NotesScreen
//import com.edu.auri.frontend.moodjournal.MoodJournal
import com.edu.auri.frontend.setttings.SettingsScreen
import com.edu.auri.frontend.sign_up.RegistrationScreen
import com.edu.auri.frontend.welcomescreen.WelcomeScreen

/**
 * Sets up the navigation graph for the Auri application.
 *
 * This composable function creates a [NavHost] with defined routes for the Welcome, Home,
 * Registration (signup), and Login screens. It uses a [NavController] to navigate between
 * different composable destinations.
 *
 * @param modifier A [Modifier] for styling and layout. Defaults to [Modifier].
 * @param authViewModel The [AuthViewModel] managing authentication, which is passed along to various screens.
 */
@Composable
fun AuriNavigation(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    logDailyViewModel: LogDailyViewModel,
    notesViewModel: NotesViewModel,
    navController: NavHostController
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "welcome",
        builder = {
            composable("welcome") {
                WelcomeScreen(modifier, navController, authViewModel)
            }
            composable("home") {
                HomeScreen(modifier, navController, authViewModel, logDailyViewModel)
            }
            composable("signup") {
                RegistrationScreen(modifier, navController, authViewModel)
            }
            composable("login") {
                LoginScreen(modifier, navController, authViewModel)
            }
            composable("insights") {
                InsightsScreen(navController)
            }

            composable("settings") {
                SettingsScreen(navController, authViewModel)
            }
            composable("history") {
                HistoryScreen(navController, logDailyViewModel)
            }
            composable("dayoverview") {
                DayOverviewScreen(modifier, navController, logDailyViewModel)
            }
            composable("notes") {
                NotesScreen(navController, notesViewModel, logDailyViewModel)
            }

//            composable("Journal") {
//                MoodJournal(modifier, navController, auth = FirebaseAuth.getInstance()) // recheck
//            }
        }
    )
}