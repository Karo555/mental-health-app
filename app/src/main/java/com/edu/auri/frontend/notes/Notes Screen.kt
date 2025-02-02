package com.edu.auri.frontend.notes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.edu.auri.backend.dailylogs.LogDailyViewModel
import com.edu.auri.backend.notes.Note
import com.edu.auri.backend.notes.NotesViewModel
import com.edu.auri.frontend.components.BottomBar
import java.time.LocalDate
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun NotesScreen(navController: NavController, viewModel: NotesViewModel, logDailyViewModel: LogDailyViewModel) {
    val today = remember { LocalDate.now().toString() }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        bottomBar = {
            BottomBar(navController)
        },
    ) {paddingValues ->
        Surface () {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {

                Text(
                    text = "Notes",
                    style = TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 23.sp,
                        fontWeight = FontWeight(700),
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = today,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))

                var text by remember { mutableStateOf(TextFieldValue()) }
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                val note = Note(
                    content = text.text
                )
                Button(
                    onClick = {
                        viewModel.saveNote(note)
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Daily note saved successfully"
                            )
                        }
                    },
                    modifier = Modifier.width(100.dp)
                ) {
                    Text("Save")
                }
            }
        }

    }

}