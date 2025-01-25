package com.edu.auri.frontend.dayoverview

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.edu.auri.backend.dailylogs.DailyLog
import com.edu.auri.backend.dailylogs.LogDailyViewModel
import kotlinx.coroutines.launch

/**
 * Composable function to display an overview of the user's daily health data.
 *
 * The screen includes various sliders, dropdowns, and checkboxes to input daily metrics
 * such as mood, sleep, stress level, and more.
 *
 * @param modifier Modifier to be applied to the layout.
 * @param navController The navigation controller to handle screen transitions.
 * @param logDailyViewModel ViewModel for managing and saving daily log data.
 */
@Composable
fun DayOverviewScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    logDailyViewModel: LogDailyViewModel
) {
    var mood by remember { mutableStateOf("") }
    var sleepHours by remember { mutableStateOf(0f) }
    var gratification by remember { mutableStateOf(0f) }
    var stressLevel by remember { mutableStateOf(0f) }
    var anxietyLevel by remember { mutableStateOf(0f) }
    var waterIntake by remember { mutableStateOf(0f) }
    var workoutTime by remember { mutableStateOf(0f) }
    var angerLevel by remember { mutableStateOf(0f) }
    var alcohol by remember { mutableStateOf(0f) }
    var cigarettes by remember { mutableStateOf(0f) }
    var sweets by remember { mutableStateOf(0f) }
    var cupsOfCoffee by remember { mutableStateOf(0f) }
    var socialIneractions by remember { mutableStateOf(0f) }
    var drugs by remember { mutableStateOf(false) }

    val dailyLog = DailyLog(
        mood = mood,
        sleepHours = sleepHours,
        gratification = gratification,
        stressLevel = stressLevel,
        anxietyLevel = anxietyLevel,
        waterIntake = waterIntake,
        workoutTime = workoutTime,
        angerLevel = angerLevel,
        alcohol = alcohol,
        cigarettes = cigarettes,
        sweets = sweets,
        socialInteractions = socialIneractions,
        cupsOfCoffee = cupsOfCoffee,
        drugs = drugs
    )

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(
                        text = "Day Overview",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                item {
                    Button(
                        onClick = {
                            logDailyViewModel.saveDailyLog(dailyLog, logDailyViewModel.getCurrentDate())
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Data saved successfully")
                            }
                        },
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("Save data")
                    }
                }
            }
        }
    }
}