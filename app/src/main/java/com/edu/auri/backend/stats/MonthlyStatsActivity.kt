/*
package com.edu.auri.databases
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edu.auri.R
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class MonthlyStatsActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvSummary: TextView
    private lateinit var btnBack: Button
    private lateinit var adapter: DailyLogsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView()

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Initialize UI
        btnBack = findViewById(R.id.btnBack)
        recyclerView = findViewById(R.id.rvDailyLogs)
        tvSummary = findViewById(R.id.tvSummary)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = DailyLogsAdapter(emptyList())
        recyclerView.adapter = adapter

        // Handle the back button
        btnBack.setOnClickListener {
            finish()  // Close this activity and go back to the previous screen
        }

        fetchMonthlyLogs()
    }



    private fun fetchMonthlyLogs() {
        val user = auth.currentUser ?: return

        firestore.collection("users")
            .document(user.uid)
            .collection("daily_logs")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(31)
            .get()
            .addOnSuccessListener { snapshot ->
                val dailyLogs = snapshot.documents.mapNotNull { doc ->
                    val mood = doc.getString("mood") ?: "Unknown"
                    val gratification = doc.getLong("gratification")?.toInt() ?: 0
                    val sleep = doc.getDouble("sleepHours") ?: 0.0
                    val timestamp = doc.getTimestamp("timestamp") ?: Timestamp.now()
                    val docId = doc.id

                    DailyLog(
                        docId = docId,
                        mood = mood,
                        gratification = gratification,
                        sleepHours = sleep.toFloat(),
                        timestamp = timestamp
                    )
                }

                adapter.updateLogs(dailyLogs)
                calculateSummary(dailyLogs)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to fetch logs: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun calculateSummary(dailyLogs: List<DailyLog>) {
        if (dailyLogs.isEmpty()) {
            tvSummary.text = "No data to display"
            return
        }

        val avgGratification = dailyLogs.map { it.gratification }.average()
        val avgSleep = dailyLogs.map { it.sleepHours }.average()

        val summaryText = """
            Logs: ${dailyLogs.size}
            Avg Gratification: ${"%.2f".format(avgGratification)}
            Avg Sleep: ${"%.2f".format(avgSleep)} hours
        """.trimIndent()

        tvSummary.text = summaryText
    }
}

data class DailyLog(
    val docId: String,
    val mood: String,
    val gratification: Int,
    val sleepHours: Float,
    val timestamp: Timestamp
)*/
