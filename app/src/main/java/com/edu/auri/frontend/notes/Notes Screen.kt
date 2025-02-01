package com.edu.auri.frontend.notes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun NotesScreen(navController: NavController) {
        var noteText by remember { mutableStateOf(TextFieldValue()) }
        var notesList by remember { mutableStateOf(listOf<String>()) }

        Column(modifier = Modifier.padding(16.dp)) {
            BasicTextField(
                value = noteText,
                onValueChange = { noteText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                if (noteText.text.isNotEmpty()) {
                    notesList = notesList + noteText.text
                    noteText = TextFieldValue()
                }
            }) {
                Text("Add Note")
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                items(notesList.size) { index ->
                    Text(text = notesList[index], modifier = Modifier.padding(8.dp))
                }
            }
        }
}