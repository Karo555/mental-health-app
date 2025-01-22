package com.edu.auri.backend.dailylogs

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.edu.auri.R
import com.google.android.material.slider.Slider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Activity for logging daily health and lifestyle data.
 *
 * This activity allows the user to input various daily metrics (such as sleep, mood, stress,
 * water intake, and more) using sliders, spinners, switches, and text fields. The data is then
 * saved to a Firestore subcollection under the user's document.
 *
 * Before using this activity, ensure that the user is authenticated.
 */
class LogDailyActivity : AppCompatActivity() {

    // Firebase instances
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    // UI Components for Sliders & Their Value TextViews
    private lateinit var sliderSleep: Slider
    private lateinit var tvSleepValue: TextView

    private lateinit var sliderGratification: Slider
    private lateinit var tvGratificationValue: TextView

    private lateinit var sliderStress: Slider
    private lateinit var tvStressValue: TextView

    private lateinit var sliderAnxiety: Slider
    private lateinit var tvAnxietyValue: TextView

    private lateinit var sliderWater: Slider
    private lateinit var tvWaterValue: TextView

    private lateinit var sliderWorkout: Slider
    private lateinit var tvWorkoutValue: TextView

    private lateinit var sliderAnger: Slider
    private lateinit var tvAngerValue: TextView

    // Spinner for selecting mood
    private lateinit var spinnerMood: Spinner

    // Switch for drug usage status
    private lateinit var switchDrugs: Switch

    // EditTexts for additional inputs
    private lateinit var etSocial: EditText
    private lateinit var etSweets: EditText
    private lateinit var etCoffee: EditText
    private lateinit var etAlcohol: EditText
    private lateinit var etCigarettes: EditText

    // Button to save daily data
    private lateinit var btnSaveDaily: Button

    /**
     * Called when the activity is starting.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_daily)

        // Back button: Closes this activity and returns to the previous screen.
        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }

        // Initialize Firebase instances
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Check if user is logged in; if not, show a message and finish the activity.
        if (auth.currentUser == null) {
            Toast.makeText(this, "Please log in first", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Initialize UI components and configure listeners.
        initUI()
        setupMoodSpinner()
        setupSliderListeners()

        // Set listener for saving daily data.
        btnSaveDaily.setOnClickListener {
            saveDailyData()
        }
    }

    /**
     * Initializes the UI components.
     */
    private fun initUI() {
        // Sleep
        sliderSleep = findViewById(R.id.sliderSleep)
        tvSleepValue = findViewById(R.id.tvSleepValue)

        // Gratification
        sliderGratification = findViewById(R.id.sliderGratification)
        tvGratificationValue = findViewById(R.id.tvGratificationValue)

        // Stress
        sliderStress = findViewById(R.id.sliderStress)
        tvStressValue = findViewById(R.id.tvStressValue)

        // Anxiety
        sliderAnxiety = findViewById(R.id.sliderAnxiety)
        tvAnxietyValue = findViewById(R.id.tvAnxietyValue)

        // Water
        sliderWater = findViewById(R.id.sliderWater)
        tvWaterValue = findViewById(R.id.tvWaterValue)

        // Workout
        sliderWorkout = findViewById(R.id.sliderWorkout)
        tvWorkoutValue = findViewById(R.id.tvWorkoutValue)

        // Anger
        sliderAnger = findViewById(R.id.sliderAnger)
        tvAngerValue = findViewById(R.id.tvAngerValue)

        // Switch for drugs
        switchDrugs = findViewById(R.id.switchDrugs)

        // Spinner for mood selection
        spinnerMood = findViewById(R.id.spinnerMood)

        // EditTexts for user inputs
        etSocial = findViewById(R.id.etSocial)
        etSweets = findViewById(R.id.etSweets)
        etCoffee = findViewById(R.id.etCoffee)
        etAlcohol = findViewById(R.id.etAlcohol)
        etCigarettes = findViewById(R.id.etCigarettes)

        // Button for saving daily log
        btnSaveDaily = findViewById(R.id.btnSaveDaily)
    }

    /**
     * Sets up the mood spinner with predefined options.
     */
    private fun setupMoodSpinner() {
        val moodOptions = listOf("Happy", "Neutral", "Sad", "Angry", "Anxious")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, moodOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerMood.adapter = adapter
    }

    /**
     * Sets up listeners for the sliders to update their corresponding TextViews.
     */
    private fun setupSliderListeners() {
        // Sleep slider listener
        sliderSleep.addOnChangeListener { _, value, _ ->
            tvSleepValue.text = "Selected: ${value.toInt()}h"
        }
        // Gratification slider listener
        sliderGratification.addOnChangeListener { _, value, _ ->
            tvGratificationValue.text = "Selected: ${value.toInt()}"
        }
        // Stress slider listener
        sliderStress.addOnChangeListener { _, value, _ ->
            tvStressValue.text = "Selected: ${value.toInt()}"
        }
        // Anxiety slider listener
        sliderAnxiety.addOnChangeListener { _, value, _ ->
            tvAnxietyValue.text = "Selected: ${value.toInt()}"
        }
        // Water slider listener
        sliderWater.addOnChangeListener { _, value, _ ->
            tvWaterValue.text = "Selected: ${value} L"
        }
        // Workout slider listener
        sliderWorkout.addOnChangeListener { _, value, _ ->
            tvWorkoutValue.text = "Selected: ${value.toInt()} min"
        }
        // Anger slider listener
        sliderAnger.addOnChangeListener { _, value, _ ->
            tvAngerValue.text = "Selected: ${value.toInt()}"
        }
    }

    /**
     * Saves the daily log data to Firestore.
     *
     * The log is saved under the current user's document subcollection "dailyLogs",
     * using today's date (in "yyyy-MM-dd" format) as the document ID.
     */
    private fun saveDailyData() {
        val user = auth.currentUser ?: return

        // Retrieve values from UI components.
        val sleepHours = sliderSleep.value
        val gratification = sliderGratification.value
        val stress = sliderStress.value
        val anxiety = sliderAnxiety.value
        val water = sliderWater.value
        val workout = sliderWorkout.value
        val anger = sliderAnger.value

        val tookDrugs = switchDrugs.isChecked
        val selectedMood = spinnerMood.selectedItem.toString()

        val social = etSocial.text.toString().toIntOrNull() ?: 0
        val sweets = etSweets.text.toString().toIntOrNull() ?: 0
        val coffee = etCoffee.text.toString().toIntOrNull() ?: 0
        val alcohol = etAlcohol.text.toString().toIntOrNull() ?: 0
        val cigarettes = etCigarettes.text.toString().toIntOrNull() ?: 0

        // Create a document ID based on today's date (e.g., "2025-01-22").
        val todayString = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            .format(System.currentTimeMillis())

        // Prepare the daily log data.
        val dailyData = hashMapOf(
            "mood" to selectedMood,
            "sleepHours" to sleepHours,
            "gratification" to gratification,
            "stressLevel" to stress,
            "anxietyLevel" to anxiety,
            "litersOfWater" to water,
            "workoutTime" to workout,
            "angerLevel" to anger,
            "drugs" to tookDrugs,
            "socialInteractions" to social,
            "sweets" to sweets,
            "cupsOfCoffee" to coffee,
            "alcohol" to alcohol,
            "cigarettes" to cigarettes,
            "timestamp" to FieldValue.serverTimestamp()
        )

        // Reference to the parent user document.
        val userDocRef = firestore.collection("users").document(user.uid)

        // Ensure the user document exists before writing the daily log.
        userDocRef.get().addOnSuccessListener { document ->
            if (!document.exists()) {
                val userData = hashMapOf(
                    "email" to user.email,
                    "displayName" to user.displayName,
                    "createdAt" to FieldValue.serverTimestamp()
                )
                userDocRef.set(userData).addOnCompleteListener {
                    // Once the user document is created, write the daily log.
                    userDocRef.collection("dailyLogs")
                        .document(todayString)
                        .set(dailyData)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Daily data saved!", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            } else {
                // User document exists—write the daily log directly.
                userDocRef.collection("dailyLogs")
                    .document(todayString)
                    .set(dailyData)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Daily data saved!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }.addOnFailureListener { e ->
            Toast.makeText(this, "Error checking user doc: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}