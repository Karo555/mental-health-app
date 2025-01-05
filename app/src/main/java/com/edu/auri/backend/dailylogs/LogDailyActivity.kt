
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

class LogDailyActivity : AppCompatActivity() {

    // Firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    // Sliders & Their Value TextViews
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

    // Spinner
    private lateinit var spinnerMood: Spinner

    // Switch
    private lateinit var switchDrugs: Switch

    // EditTexts
    private lateinit var etSocial: EditText
    private lateinit var etSweets: EditText
    private lateinit var etCoffee: EditText
    private lateinit var etAlcohol: EditText
    private lateinit var etCigarettes: EditText

    private lateinit var btnSaveDaily: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_daily)

        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish() // Closes this activity and returns to the previous screen
        }

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // If user is not logged in, redirect/finish
        if (auth.currentUser == null) {
            Toast.makeText(this, "Please log in first", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        initUI()
        setupMoodSpinner()
        setupSliderListeners()

        btnSaveDaily.setOnClickListener {
            saveDailyData()
        }
    }

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

        // Switch
        switchDrugs = findViewById(R.id.switchDrugs)

        // Spinner
        spinnerMood = findViewById(R.id.spinnerMood)

        // EditTexts
        etSocial = findViewById(R.id.etSocial)
        etSweets = findViewById(R.id.etSweets)
        etCoffee = findViewById(R.id.etCoffee)
        etAlcohol = findViewById(R.id.etAlcohol)
        etCigarettes = findViewById(R.id.etCigarettes)

        // Button
        btnSaveDaily = findViewById(R.id.btnSaveDaily)
    }

    private fun setupMoodSpinner() {
        val moodOptions = listOf("Happy", "Neutral", "Sad", "Angry", "Anxious")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, moodOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerMood.adapter = adapter
    }

    private fun setupSliderListeners() {
        // Sleep slider
        sliderSleep.addOnChangeListener { _, value, _ ->
            tvSleepValue.text = "Selected: ${value.toInt()}h"
        }

        // Gratification
        sliderGratification.addOnChangeListener { _, value, _ ->
            tvGratificationValue.text = "Selected: ${value.toInt()}"
        }

        // Stress
        sliderStress.addOnChangeListener { _, value, _ ->
            tvStressValue.text = "Selected: ${value.toInt()}"
        }

        // Anxiety
        sliderAnxiety.addOnChangeListener { _, value, _ ->
            tvAnxietyValue.text = "Selected: ${value.toInt()}"
        }

        // Water
        sliderWater.addOnChangeListener { _, value, _ ->
            tvWaterValue.text = "Selected: ${value} L"
        }

        // Workout
        sliderWorkout.addOnChangeListener { _, value, _ ->
            tvWorkoutValue.text = "Selected: ${value.toInt()} min"
        }

        // Anger
        sliderAnger.addOnChangeListener { _, value, _ ->
            tvAngerValue.text = "Selected: ${value.toInt()}"
        }
    }

    private fun saveDailyData() {
        val user = auth.currentUser ?: return

        // Sliders (floats)
        val sleepHours = sliderSleep.value
        val gratification = sliderGratification.value
        val stress = sliderStress.value
        val anxiety = sliderAnxiety.value
        val water = sliderWater.value
        val workout = sliderWorkout.value
        val anger = sliderAnger.value

        // Switch
        val tookDrugs = switchDrugs.isChecked

        // Spinner
        val selectedMood = spinnerMood.selectedItem.toString()

        // EditTexts (ints)
        val social = etSocial.text.toString().toIntOrNull() ?: 0
        val sweets = etSweets.text.toString().toIntOrNull() ?: 0
        val coffee = etCoffee.text.toString().toIntOrNull() ?: 0
        val alcohol = etAlcohol.text.toString().toIntOrNull() ?: 0
        val cigarettes = etCigarettes.text.toString().toIntOrNull() ?: 0

        // Document ID: today's date
        val todayString = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            .format(System.currentTimeMillis())

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

        firestore.collection("users")
            .document(user.uid)
            .collection("daily_logs")
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
}
