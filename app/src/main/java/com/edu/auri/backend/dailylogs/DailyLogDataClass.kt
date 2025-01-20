package com.edu.auri.backend.dailylogs

import com.google.firebase.Timestamp

/**
 * Data class representing a daily log entry containing various health and lifestyle metrics.
 *
 * Each instance of [DailyLogDataClass] encapsulates information recorded in a single day, such as
 * consumption of substances, sleep hours, social interactions, and physical activities.
 *
 * @property alcohol The recorded amount or score related to alcohol consumption.
 * @property angerLevel The recorded level of anger experienced during the day.
 * @property anxietyLevel The recorded level of anxiety experienced during the day.
 * @property cigarettes The number of cigarettes smoked during the day.
 * @property cupsOfCoffee The number of cups of coffee consumed during the day.
 * @property drugs A Boolean indicating whether drugs were used on the day.
 * @property gratification The recorded level of gratification or satisfaction experienced.
 * @property litersOfWater The number of liters of water consumed during the day.
 * @property mood A textual description of the overall mood.
 * @property sleepHours The number of hours slept during the day.
 * @property socialInteractions The count or measure of social interactions during the day.
 * @property stressLevel The recorded level of stress experienced during the day.
 * @property sweets The number of sweets consumed during the day.
 * @property timestamp The [Timestamp] indicating when the log was recorded.
 * @property workoutTime The duration of the workout in minutes.
 */
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