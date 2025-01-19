package com.edu.auri

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.edu.auri.backend.registration.AuthViewModel
import com.edu.auri.frontend.navigation.AuriNavigation
import com.edu.auri.ui.theme.AuriTheme
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            enableEdgeToEdge()
            val authViewModel: AuthViewModel by viewModels()
            setContent {
                AuriTheme {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                                TipsScreen()
                        AuriNavigation(
                            modifier = Modifier.padding(innerPadding),
                            authViewModel = authViewModel
                        )
                    }
                }
            }
        }
    }