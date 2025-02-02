package com.edu.auri.backend.notes

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

class NotesViewModel : ViewModel() {
    private val database = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()

    private val _notes = MutableStateFlow<Map<String, Note>>(emptyMap())
    val notes = _notes.asStateFlow()

    fun saveNote(note: Note) {
        val date = LocalDate.now().toString()
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Log.e("SaveNote", "User not authenticated")
            return
        }

        database.collection("users")
            .document(currentUser.uid)
            .collection("notes")
            .document(date)
            .set(note)
            .addOnSuccessListener {
                Log.d("SaveNote", "Note saved successfully")
            }
            .addOnFailureListener { exception ->
                Log.e("SaveNote", "Error saving note: ${exception.message}", exception)
            }
    }

}