package com.edu.auri.frontend.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.edu.auri.backend.dailylogs.DailyLog
import com.edu.auri.backend.dailylogs.LogDailyViewModel
import com.edu.auri.frontend.components.BottomBar
import com.kizitonwose.calendar.compose.VerticalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.*
import java.time.YearMonth
import java.time.format.DateTimeFormatter

/**
 * Displays the history screen with a calendar view of logged daily data.
 *
 * @param navController The navigation controller for handling screen navigation.
 * @param viewModel The ViewModel for managing daily logs data.
 */
@Composable
fun HistoryScreen(navController: NavController, viewModel: LogDailyViewModel) {
    val dailyLogs by viewModel.dailyLogs.collectAsState()

    val moodColors = mapOf(
        "Happy" to Color.Green,
        "Neutral" to Color.Gray,
        "Sad" to Color.Blue,
        "Angry" to Color.Red,
        "Anxious" to Color.Yellow
    )

    val logMap = remember(dailyLogs) {
        dailyLogs.mapValues { it.value.mood ?: "Neutral" }
    }

    val currentMonth = remember { YearMonth.now() }
    var selectedLog by remember { mutableStateOf<DailyLog?>(null) }

    LaunchedEffect(Unit) {
        viewModel.fetchDailyLogs()
    }

    Scaffold(
        bottomBar = {
            BottomBar(navController)
        }
    ) { paddingValues ->
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(paddingValues),
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "History",
                        style = TextStyle(
                            fontSize = 18.sp,
                            lineHeight = 23.sp,
                            fontWeight = FontWeight(700),
                            textAlign = TextAlign.Center
                        )
                    )
                }
                Spacer(modifier = Modifier.height(44.dp))

                VerticalCalendar(
                    modifier = Modifier.fillMaxSize(),
                    state = rememberCalendarState(
                        startMonth = currentMonth.minusMonths(6),
                        endMonth = currentMonth.plusMonths(6),
                        firstVisibleMonth = currentMonth
                    ),

                    monthHeader = { month ->
                        Text(
                            text = month.yearMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")),
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(8.dp)
                        )
                    },
                    dayContent = { day ->
                        val formattedDate = day.date.format(DateTimeFormatter.ISO_LOCAL_DATE)
                        val mood = logMap[formattedDate] ?: "Neutral"

                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(moodColors[mood] ?: Color.Gray, CircleShape)
                                .clickable {
                                    selectedLog = dailyLogs[formattedDate]
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(day.date.dayOfMonth.toString(), color = Color.White)
                        }
                    }
                )
            }
        }
    }

    selectedLog?.let {
        MoodDetailsBottomSheet(it) { selectedLog = null }
    }
}

/**
 * Displays the bottom sheet with detailed information of the selected daily log.
 *
 * @param selectedLog The daily log selected by the user.
 * @param onDismiss Callback function to dismiss the bottom sheet.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodDetailsBottomSheet(selectedLog: DailyLog, onDismiss: () -> Unit) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(Modifier.padding(16.dp)) {
            Text("Date: ${selectedLog.timestamp?.toDate()?.toString() ?: "Unknown"}")
            Text("Mood: ${selectedLog.mood ?: "Not recorded"}")
            Text("Water Intake: ${selectedLog.waterIntake ?: 0} liters")
            Text("Sleep Hours: ${selectedLog.sleepHours ?: 0} hrs")
            Text("Workout Time: ${selectedLog.workoutTime ?: 0} min")
            Text("Stress Level: ${selectedLog.stressLevel ?: 0}")
            Text("Anxiety Level: ${selectedLog.anxietyLevel ?: 0}")
            Text("Gratification: ${selectedLog.gratification ?: 0}")
            Text("Anger Level: ${selectedLog.angerLevel ?: 0}")
            Text("Social Interactions: ${selectedLog.socialInteractions ?: 0}")
            Text("Alcohol: ${selectedLog.alcohol ?: 0}")
            Text("Cigarettes: ${selectedLog.cigarettes ?: 0}")
            Text("Cups of Coffee: ${selectedLog.cupsOfCoffee ?: 0}")
            Text("Drugs: ${selectedLog.drugs ?: false}")
            Text("Sweets: ${selectedLog.sweets ?: 0}")
        }
    }
}