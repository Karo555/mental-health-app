package com.edu.auri.backend.endpoint

data class DailyLog(
    val alcohol: Int = 0,
    val angerLevel: Int = 0,
    val anxietyLevel: Int = 0,
    val cigarettes: Int = 0,
    val cupsOfCoffee: Int = 0,
    val drugs: Boolean = false,
    val gratification: Int = 0,
    val litersOfWater: Int = 0,
    val mood: String = "",
    val sleepHours: Int = 0,
    val socialInteractions: Int = 0,
    val stressLevel: Int = 0,
    val sweets: Int = 0,
    val timestamp: String = "",
    val workoutTime: Int = 0
)
