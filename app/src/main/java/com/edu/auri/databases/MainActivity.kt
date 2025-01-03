/*
package com.edu.auri.databases

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.edu.auri.R
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        // If user is not logged in, redirect
        if (auth.currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        val btnLogDaily = findViewById<Button>(R.id.btnLogDaily)
        val btnViewMonthlyStats = findViewById<Button>(R.id.btnViewMonthlyStats)
        val btnSignOut = findViewById<Button>(R.id.btnSignOut)

        btnLogDaily.setOnClickListener {
            startActivity(Intent(this, LogDailyActivity::class.java))
        }

        btnViewMonthlyStats.setOnClickListener {
            startActivity(Intent(this, MonthlyStatsActivity::class.java))
        }

        btnSignOut.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}

*/
