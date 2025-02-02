package com.edu.auri.backend.notes

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

data class Note (
    val content: String = "",
    @ServerTimestamp val timestamp: Timestamp? = null
)
