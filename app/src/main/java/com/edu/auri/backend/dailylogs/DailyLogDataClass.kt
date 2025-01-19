package com.edu.auri.backend.dailylogs
import com.google.firebase.Timestamp

data class DailyLogDataClass(
    val alcohol: Int? = null,
    val angerLevel: Int? = null,
    val anxietyLevel: Int? = null,
    val cigarettes: Int? = null,
    val cupsOfCoffee: Int? = null,
    val drugs: Boolean? = null,
    val gratification: Int? = null,
    val litersOfWater: Int? = null,
    val mood: String? = null,
    val sleepHours: Int? = null,
    val socialInteractions: Int? = null,
    val stressLevel: Int? = null,
    val sweets: Int? = null,
    val timestamp: Timestamp? = null,
    val workoutTime: Int? = null
)


