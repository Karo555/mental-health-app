package com.edu.auri.backend.OpenAI

import com.edu.auri.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OpenAIMain : ComponentActivity() {

    private lateinit var btnFetchData: Button
    private lateinit var tvTips: TextView
    private val dataRepository = DataRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        FirebaseFirestore.setLoggingEnabled(true)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_openai)

        btnFetchData = findViewById(R.id.btnFetchData)
        tvTips = findViewById(R.id.tvTips)

        btnFetchData.setOnClickListener {
            Log.d("FirestoreDebug", "Button pressed!")
            // Perform Firestore + OpenAI calls asynchronously
            CoroutineScope(Dispatchers.IO).launch {
                Log.d("FirestoreDebug", "Starting Firestore fetch...")
                try {
                    val dataRepository = DataRepository()
                    CoroutineScope(Dispatchers.IO).launch {
                        val userData = dataRepository.fetchUserData("users")

                        withContext(Dispatchers.Main) {
                            // Update UI or show Toast
                            Toast.makeText(this@OpenAIMain, "Data: $userData", Toast.LENGTH_LONG).show()
                        }
                    }
                } catch (e: Exception) {
                    Log.e("FirestoreDebug", "Firestore fetch failed", e)
                }
            }

        }
    }
}
