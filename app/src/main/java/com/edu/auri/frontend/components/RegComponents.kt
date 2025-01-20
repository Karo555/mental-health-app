package com.edu.auri.frontend.components

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

/**
 * A composable function that displays a text field for entering a name.
 *
 * @param name a [MutableState] holding the current value of the name input.
 */
@Composable
fun NameField(name: MutableState<String>) {
    TextField(
        value = name.value,
        onValueChange = { name.value = it },
        label = { Text("Name") },
        placeholder = { Text("John Doe") },
        singleLine = true,
        textStyle = MaterialTheme.typography.labelSmall,
        modifier = Modifier.width(300.dp)
    )
}

/**
 * A composable function that displays an outlined text field for entering a password.
 *
 * The text field applies a password visual transformation to mask the input. An error state is shown if
 * the password is non-empty and its length is less than 8 characters.
 *
 * @param password a [MutableState] holding the current value of the password input.
 */
@Composable
fun PasswordField(password: MutableState<String>) {
    OutlinedTextField(
        value = password.value,
        onValueChange = { password.value = it },
        label = { Text("Password") },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        modifier = Modifier.width(300.dp),
        isError = password.value.isNotEmpty() && password.value.length < 8,
    )
}

/**
 * A composable function that displays an outlined text field for entering an email.
 *
 * @param email a [MutableState] holding the current value of the email input.
 */
@Composable
fun EmailField(email: MutableState<String>) {
    OutlinedTextField(
        value = email.value,
        onValueChange = { email.value = it },
        label = { Text("Enter your email") },
        placeholder = { Text("Email") },
        singleLine = true,
        modifier = Modifier.width(300.dp)
    )
}