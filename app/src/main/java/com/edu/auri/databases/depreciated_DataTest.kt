//package com.edu.auri.databases
//
//import android.annotation.SuppressLint
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.edu.auri.backend.moodjournal.Mood
//import com.google.firebase.Timestamp
//
//@Composable
//fun MoodScreenTest(viewModel: DataViewModel = viewModel()) {
//    val getData = viewModel.mood.value
//
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text(text = "Mood Data:")
//        Text(text = "Mood ID: ${getData.mood}")
//        Text(text = "Mood Description: ${getData.notes}")
//        Text(text = "Mood Timestamp: ${getData.timestamp}")
//    }
//}
//
//
//fun transformDate(date:Timestamp){}