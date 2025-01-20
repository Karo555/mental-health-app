package com.edu.auri.databases

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edu.auri.backend.moodjournal.Mood
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.database.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class DataViewModel : ViewModel(){
    val mood = mutableStateOf(Mood())
    init{
        getData()
    }

    private fun getData(){
        viewModelScope.launch {
            mood.value = getDataMoodsFromFirestore()
        }
    }
}
suspend fun getDataMoodsFromFirestore ():Mood{

    val db = FirebaseFirestore.getInstance()
    var mood = Mood()
    try {
        db.collection("users")
            .document("pD2HsHYApKaDJdAhOaQncBvCIym2")
            .collection("moods")
            .get()
            .await()
            .map {
                val result= it.toObject(Mood::class.java)

                mood = result

            }
    } catch (e:FirebaseFirestoreException){
        Log.d("error", "getDataMoodsFromFirestore:${e}")
    }
    return mood
}