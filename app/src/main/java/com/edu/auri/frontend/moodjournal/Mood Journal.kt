package com.edu.auri.frontend.moodjournal

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.edu.auri.backend.moodjournal.Mood
import com.edu.auri.backend.registration.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun MoodJournal(modifier: Modifier = Modifier, navController: NavController, auth: FirebaseAuth) {

    val mood = remember { mutableListOf<Mood>() }
    val moodCollect = FirebaseFirestore.getInstance()
        .collection("users")
        .document(auth.uid.toString())
        .collection("moods")

    LaunchedEffect(Unit) {
        moodCollect.get().addOnSuccessListener { result ->
            for (doc in result) {
                val data = doc.toObject(Mood::class.java)
                mood.add(data)
            }

        }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

    }

    MoodJournalScreen(moodsList = mood)

}

@Composable
fun MoodJournalScreen(moodsList: List<Mood>) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn {
            items(moodsList) { mood ->
                Text(
                    text = "Mood: ${mood.mood}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Notes: ${mood.notes}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Timestamp: ${mood.timestamp?.toDate()}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
