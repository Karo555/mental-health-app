
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@SuppressLint
@Composable
fun RegistrationScreen(){
    Surface (
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box (
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(140.dp))
                Text(
                    text = "Create an account",
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal,
                        textAlign = TextAlign.Center,
                        color = Color(0xFF141C24),
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(28.dp))
                BasicTextNameField()
                Spacer(modifier = Modifier.height(50.dp))
                BasicTextEmailField()
                Spacer(modifier = Modifier.height(50.dp))
                BasicTextPasswordField()
                Spacer(modifier = Modifier.height(50.dp))
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .width(300.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Sign Up")
                }

            }
        }
    }

}
@Composable
fun BasicTextNameField() {
    var name = remember { mutableStateOf("") }

    TextField(
        value = name.value,
        onValueChange = { name.value = it },
        label = { Text("Enter your name") },
        placeholder = { Text("John Doe") },
        singleLine = true,
        modifier = Modifier
            .width(300.dp)

    )
}
@Composable
fun BasicTextPasswordField() {

    var password = remember { mutableStateOf("") }

    TextField(
        value = password.value,
        onValueChange = { password.value = it },
        label = { Text("Password") },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        modifier = Modifier
            .width(300.dp),
        isError = password.value.isNotEmpty() && password.value.length < 8,
        colors = TextFieldDefaults.colors()
    )
}
@Composable
fun BasicTextEmailField() {
    var email = remember { mutableStateOf("") }

    TextField(
        value = email.value,
        onValueChange = { email.value = it },
        label = { Text("Enter your email") },
        placeholder = { Text("Email") },
        singleLine = true,
        modifier = Modifier
            .width(300.dp)
    )
}
@Preview
@Composable
fun RegistrationScreenPreview() {
    RegistrationScreen()
}