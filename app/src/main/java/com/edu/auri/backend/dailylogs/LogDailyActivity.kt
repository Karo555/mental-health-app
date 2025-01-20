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
     *
     * This method initializes the UI, sets up event listeners for various components,
     * and validates that the user is logged in before allowing data entry. If the user
     * is not logged in, a toast is displayed and the activity is finished.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     * closed, this Bundle contains the data it most recently supplied.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_daily)

        // Back button: Closes this activity and returns to the previous screen.
        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }

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
     * Initializes the UI components by binding them to their corresponding views.
     *
     * This method locates each UI element defined in the XML layout and assigns them to
     * the appropriate properties for later use.
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
     * Sets up the mood spinner with predefined mood options.
     *
     * The spinner is configured with a list of moods such as "Happy", "Neutral", "Sad", "Angry",
     * and "Anxious". An [ArrayAdapter] is used to bind the list data to the spinner.
     */
    private fun setupMoodSpinner() {
        val moodOptions = listOf("Happy", "Neutral", "Sad", "Angry", "Anxious")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, moodOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerMood.adapter = adapter
    }

    /**
     * Sets up listeners for the sliders to update their corresponding TextViews.
     *
     * When the slider values change, the associated TextView is updated in real time to reflect
     * the selected value. Each slider is customized to display an appropriate unit (e.g., "h" for
     * sleep hours, "L" for water, "min" for workout time).
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
     * This method reads the current values from the UI components (sliders, switch, spinner, and
     * EditTexts), compiles them into a map, and saves the data as a new document in the user's
     * "daily_logs" subcollection. The document ID is set as today's date in the format "yyyy-MM-dd".
     * A success or failure message is displayed based on the outcome.
     */
    private fun saveDailyData() {
        val user = auth.currentUser ?: return

        // Retrieve float values from sliders.
        val sleepHours = sliderSleep.value
        val gratification = sliderGratification.value
        val stress = sliderStress.value
        val anxiety = sliderAnxiety.value
        val water = sliderWater.value
        val workout = sliderWorkout.value
        val anger = sliderAnger.value

        // Retrieve value from switch.
        val tookDrugs = switchDrugs.isChecked

        // Retrieve selected mood from spinner.
        val selectedMood = spinnerMood.selectedItem.toString()

        // Retrieve int values from EditTexts.
        val social = etSocial.text.toString().toIntOrNull() ?: 0
        val sweets = etSweets.text.toString().toIntOrNull() ?: 0
        val coffee = etCoffee.text.toString().toIntOrNull() ?: 0
        val alcohol = etAlcohol.text.toString().toIntOrNull() ?: 0
        val cigarettes = etCigarettes.text.toString().toIntOrNull() ?: 0

        // Create a document ID based on today's date (format: yyyy-MM-dd).
        val todayString = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            .format(System.currentTimeMillis())

        // Construct the daily log data as a map.
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

        // Save the daily log data to Firestore under the user's document.
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