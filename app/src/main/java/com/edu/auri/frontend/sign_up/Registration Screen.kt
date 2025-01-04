package com.edu.auri.frontend.sign_up
import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.edu.auri.frontend.components.EmailField
import com.edu.auri.frontend.components.NameField
import com.edu.auri.frontend.components.PasswordField
import com.edu.auri.frontend.login.LoginScreen
import com.edu.auri.ui.theme.AuriTheme


@Composable
fun RegistrationScreen(navController: NavController = rememberNavController()) {
    var email: String by remember { mutableStateOf("") }
    var password: String by remember { mutableStateOf("") }
    var name: String by remember { mutableStateOf("") }

    val context = LocalContext.current

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.surfaceVariant

        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(140.dp))
                    Text(
                        text = "Create account",
                        style = TextStyle(
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Normal,
                            textAlign = TextAlign.Center,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(28.dp))
                    NameField()
                    Spacer(modifier = Modifier.height(50.dp))
                    EmailField()
                    Spacer(modifier = Modifier.height(50.dp))
                    PasswordField()
                    Spacer(modifier = Modifier.height(50.dp))
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .width(300.dp)
                            .height(50.dp),
                    ) {
                        Text(text = "Sign Up")
                    }
                    Spacer(modifier = Modifier.height(50.dp))
                    Text(
                        text = "Already have an account? Log in",
                    )

                }
            }
        }

    }

@Preview
@Composable
fun RegistrationScreenPreview() {
    AuriTheme() {RegistrationScreen()}
}