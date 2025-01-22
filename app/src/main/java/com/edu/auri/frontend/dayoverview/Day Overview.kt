package com.edu.auri.frontend.dayoverview

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.edu.auri.backend.dailylogs.DailyLog
import com.edu.auri.backend.dailylogs.LogDailyViewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

/**
 * A composable function that displays the day overview screen.
 *
 * @param modifier The modifier to be applied to the composable function.
 * @param navController The navigation controller to be used for navigation.
 */
@Composable
fun DayOverviewScreen(modifier: Modifier = Modifier, navController: NavController,
                      logDailyViewModel: LogDailyViewModel) {
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

    var dailyLog = DailyLog(
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
    Scaffold(

    ) { paddingValues ->
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                item {
                    Row(
                        modifier = Modifier
                            .width(390.dp)
                            .height(64.dp)
                            .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "Day Overview",
                            style = TextStyle(
                                fontSize = 18.sp,
                                lineHeight = 23.sp,
                                fontWeight = FontWeight(700),
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                }


                item {
                    SectionTitle(title = "Mood")
                    DropdownMenuExample(selectedMood = mood, onMoodSelected = { mood = it })
                }

                item {
                    SectionTitle(title = "Sleep Hours")
                    SliderItem(
                        label = "Hours",
                        value = sleepHours,
                        valueRange = 0f..12f,
                        step = 1f,
                        onValueChange = { sleepHours = it }
                    )
                }

                item {
                    SectionTitle(title = "Gratification")
                    SliderItem(
                        label = "Gratification",
                        value = gratification,
                        valueRange = 0f..10f,
                        step = 1f,
                        onValueChange = { gratification = it }
                    )
                }

                item {
                    SectionTitle(title = "Stress Level")
                    SliderItem(
                        label = "Stress",
                        value = stressLevel,
                        valueRange = 0f..10f,
                        step = 1f,
                        onValueChange = { stressLevel = it }
                    )
                }

                item {
                    SectionTitle(title = "Anxiety Level")
                    SliderItem(
                        label = "Anxiety",
                        value = anxietyLevel,
                        valueRange = 0f..10f,
                        step = 1f,
                        onValueChange = { anxietyLevel = it }
                    )
                }

                item {
                    SectionTitle(title = "Water Intake")
                    SliderItem(
                        label = "Liters",
                        value = waterIntake,
                        valueRange = 0f..3f,
                        step = 0.1f,
                        onValueChange = { waterIntake = it }
                    )
                }

                item {
                    SectionTitle(title = "Workout Time")
                    SliderItem(
                        label = "Minutes",
                        value = workoutTime,
                        valueRange = 0f..120f,
                        step = 1f,
                        onValueChange = { workoutTime = it }
                    )
                }

                item {
                    SectionTitle(title = "Anger Level")
                    SliderItem(
                        label = "Anger",
                        value = angerLevel,
                        valueRange = 0f..10f,
                        step = 1f,
                        onValueChange = { angerLevel = it }
                    )
                }
                item {
                    Column {
                        SectionTitle(title = "Drugs")
                        Text(text = "Select if this applies to you")
                        Checkbox(checked = drugs, onCheckedChange = { drugs = it })
                    }
                }

                item {
                    SectionTitle(title = "Alcohol")
                    SliderItem(
                        label = "Liters",
                        value = alcohol,
                        valueRange = 0f..1f,
                        step = 0.1f,
                        onValueChange = { alcohol = it }
                    )
                }
                item {
                    SectionTitle(title = "Cigarettes")
                    SliderItem(
                        label = "Packs",
                        value = cigarettes,
                        valueRange = 0f..10f,
                        step = 1f,
                        onValueChange = { cigarettes = it }
                    )
                }
                item {
                    SectionTitle(title = "Social interactions")
                    SliderItem(
                        label = "Minutes spent",
                        value = socialIneractions,
                        valueRange = 0f..130f,
                        step = 1f,
                        onValueChange = { socialIneractions = it }
                    )
                }
                item {
                    SectionTitle(title = "Sweets")
                    SliderItem(
                        label = "Sweets",
                        value = sweets,
                        valueRange = 0f..10f,
                        step = 1f,
                        onValueChange = { sweets = it }
                    )
                }
                item {
                    SectionTitle(title = "Cups of Coffee")
                    SliderItem(
                        label = "Cups",
                        value = cupsOfCoffee,
                        valueRange = 0f..5f,
                        step = 1f,
                        onValueChange = { cupsOfCoffee = it }
                    )
                }
                item { Spacer(modifier = Modifier.height(24.dp)) }
                item{
                    Row(
                        modifier = Modifier
                            .width(390.dp)
                            .padding(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 12.dp),
                        horizontalArrangement = Arrangement.Absolute.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {logDailyViewModel.saveDailyLog(dailyLog)},
                            Modifier.width(310.dp)
                        ) {
                            Text("Save data")
                        }
                    }
                }

            }
            }
        }
    }


@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
fun SliderItem(
    label: String,
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    step: Float,
    onValueChange: (Float) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "$label: ${if (step == 1f) value.toInt() else String.format("%.1f", value)}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            steps = ((valueRange.endInclusive - valueRange.start) / step).toInt() - 1,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun DropdownMenuExample(
    selectedMood: String,
    onMoodSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val moods = listOf("Happy", "Sad", "Angry", "Relaxed")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(8.dp)
    ) {
        Text(
            text = selectedMood.ifEmpty { "Select Mood" },
            modifier = Modifier
                .clickable { expanded = true }
                .padding(16.dp)
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.onSurface
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            moods.forEach { mood ->
                DropdownMenuItem(
                    text = { Text(text = mood) },
                    onClick = {
                        onMoodSelected(mood)
                        expanded = false
                    }
                )
            }
        }
    }
}
