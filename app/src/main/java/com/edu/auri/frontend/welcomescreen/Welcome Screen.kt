package com.edu.auri.frontend.welcomescreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edu.auri.R

@SuppressLint
@Composable
fun WelcomeScreen() {
    // Use Box to layer elements
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()

    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Background Image
            Image(
                painter = painterResource(id = R.drawable.welcomebackground), // Replace with your image resource
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(140.dp))
                Text(
                    text = "Welcome to Auri", //TODO Global username and  welcome message depending on time of day
                    style = TextStyle(
                        fontSize = 42.sp,
                        lineHeight = 40.sp,
                        fontWeight = FontWeight(700),
                        color = Color(0xFF141C24),
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp) // Add horizontal padding for alignment
                )
                // Welcome Text

                Spacer(modifier = Modifier.height(18.dp))

                // Subtitle
                Text(
                    text = "Mental health is not just the absence of mental illness. " +
                            "It’s emotional, physical, and social well-being\n" + "  .   .   ."
                            +"\n,Meghan McCain",
                    color = Color(0xFF141C24),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight(700),
                    ),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(350.dp))

                // Sign-up Button
                Button(
                    onClick = { /* Handle sign-up action */ },
                    modifier = Modifier.width(200.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF141C24)),
                ) {
                    Text(text = "Sign up")
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Log-in Text
                Text(
                    text = "Already member?",
                    color = Color(0xFF141C24),
                    style = TextStyle(
                        fontWeight = FontWeight(600),
                        fontSize = 15.sp,
                        fontStyle = FontStyle.Normal,
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.clickable { /* Handle log-in action */ }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { /* Handle sign-up action */ },
                    modifier = Modifier
                        .width(200.dp),
                    colors = ButtonDefaults.buttonColors(Color(0XFFFFFFFF)),
                ) {
                    Text(
                        text = "Log in",
                        color = Color(0xFF141C24),

                    )

                }
            }
        }

    }
}
@Preview
@Composable
fun PreviewWelcomeScreen() {
    WelcomeScreen()
}



