package com.edu.auri.frontend.components

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun NameField() {
    var name = remember { mutableStateOf("") }

    TextField(
        value = name.value,
        onValueChange = { name.value = it },
        label = { Text("Name") },
        placeholder = { Text("John Doe") },
        singleLine = true,
        textStyle = MaterialTheme.typography.labelSmall,
        modifier = Modifier
            .width(300.dp)

    )
}

@Composable
fun PasswordField() {

    var password = remember { mutableStateOf("") }

    OutlinedTextField(
        value = password.value,
        onValueChange = { password.value = it },
        label = { Text("Password") },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        modifier = Modifier
            .width(300.dp),
        isError = password.value.isNotEmpty() && password.value.length < 8,
    )
}
@Composable
fun EmailField() {
    var email = remember { mutableStateOf("") }

    OutlinedTextField(
        value = email.value,
        onValueChange = { email.value = it },
        label = { Text("Enter your email") },
        placeholder = { Text("Email") },
        singleLine = true,
        modifier = Modifier
            .width(300.dp)
    )
}