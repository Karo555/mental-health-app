package com.edu.auri.backend.charts

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.edu.auri.R
import com.edu.auri.backend.dailylogs.DailyLogDataClass
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar

class ChartActivity : AppCompatActivity() {

    private val TAG = "ChartActivity"
    private lateinit var firestore: FirebaseFirestore
    private lateinit var lineChart: LineChart
    private lateinit var metricSpinner: Spinner

    // Define available metrics for charting. These should be numeric fields from DailyLogDataClass.
    private val metricOptions = listOf("sleepHours", "stressLevel", "anxietyLevel", "angerLevel")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)

        // Initialize Firestore and enable offline persistence (enabled by default)
        firestore = FirebaseFirestore.getInstance()

        // Link chart view
        lineChart = findViewById(R.id.lineChart)
        metricSpinner = findViewById(R.id.metricSpinner)

        // Set up Spinner to choose metric
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, metricOptions)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        metricSpinner.adapter = spinnerAdapter

        lineChart = findViewById(R.id.lineChart)
        metricSpinner = findViewById(R.id.metricSpinner)

        metricSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View?,
                                        position: Int,
                                        id: Long) {
                val selectedMetric = metricOptions[position]
                Log.d(TAG, "Selected metric: $selectedMetric")
                fetchDataAndDisplayChart(selectedMetric)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No-op
            }
        }
    }

    /**
     * Fetches the daily logs from Firestore, groups them by week, and updates the chart using the selected metric.
     *
     * @param metric The metric field to aggregate (e.g., "sleepHours").
     */
    private fun fetchDataAndDisplayChart(metric: String) {
        firestore.collection("daily_logs")
            .get()
            .addOnSuccessListener { querySnapshot ->
                Log.d(TAG, "Fetched ${querySnapshot.size()} documents from Firestore.")
                val logs = querySnapshot.documents.mapNotNull { doc ->
                    doc.toObject(DailyLogDataClass::class.java)
                }
                // Group by week (using year-week as key)
                val weeklyData = groupLogsByWeek(logs, metric)
                updateChart(weeklyData, metric)
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error fetching documents: ", exception)
            }
    }

    /**
     * Groups the logs by week (year and week number) and computes the average value for the selected metric.
     *
     * @param logs The list of daily logs.
     * @param metric The metric to compute (field names should match DailyLogDataClass numeric properties).
     * @return A map where the key is a string representing the week (e.g., "2025-W03") and the value is the average metric value.
     */
    private fun groupLogsByWeek(logs: List<DailyLogDataClass>, metric: String): Map<String, Float> {
        val weeklyMap = mutableMapOf<String, MutableList<Float>>()

        for (log in logs) {
            val ts: Timestamp = log.timestamp ?: continue
            val cal = Calendar.getInstance().apply { time = ts.toDate() }
            val weekOfYear = cal.get(Calendar.WEEK_OF_YEAR)
            val year = cal.get(Calendar.YEAR)
            val weekKey = "$year-W${weekOfYear}"

            // Retrieve the value for the selected metric.
            // We assume the field is stored as an Int (nullable). Convert it to Float for calculations.
            val value: Float? = when(metric) {
                "sleepHours" -> log.sleepHours?.toFloat()
                "stressLevel" -> log.stressLevel?.toFloat()
                "anxietyLevel" -> log.anxietyLevel?.toFloat()
                "angerLevel" -> log.angerLevel?.toFloat()
                else -> null
            }
            if (value != null) {
                if (weeklyMap.containsKey(weekKey)) {
                    weeklyMap[weekKey]?.add(value)
                } else {
                    weeklyMap[weekKey] = mutableListOf(value)
                }
            }
        }

        // Compute averages
        val weeklyAverages = weeklyMap.mapValues { entry ->
            entry.value.average().toFloat()
        }
        Log.d(TAG, "Weekly averages for $metric: $weeklyAverages")
        return weeklyAverages
    }

    /**
     * Updates the line chart with the weekly aggregated data.
     *
     * @param weeklyData A map of week keys to the average value.
     * @param metric The metric being visualized.
     */
    private fun updateChart(weeklyData: Map<String, Float>, metric: String) {
        val entries = ArrayList<Entry>()
        val weeks = weeklyData.keys.sorted()

        // For a better x-axis representation, we need to map weeks to an index.
        weeks.forEachIndexed { index, weekKey ->
            val avgValue = weeklyData[weekKey] ?: 0f
            entries.add(Entry(index.toFloat(), avgValue))
        }

        val dataSet = LineDataSet(entries, "Weekly average of $metric")
        dataSet.lineWidth = 2f
        dataSet.circleRadius = 4f

        val lineData = LineData(dataSet)
        lineChart.data = lineData

        // Format description and x-axis labels (for now we simply display week keys in log)
        val description = Description().apply {
            text = "Weekly Summary"
        }
        lineChart.description = description
        lineChart.invalidate()

        Log.d(TAG, "Chart updated with ${entries.size} entries for metric: $metric")
    }
}
