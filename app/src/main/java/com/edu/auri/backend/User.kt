package com.edu.auri.backend

/**
 * Data class representing a user.
 *
 * This class holds the essential information for a user, including a unique identifier,
 * username, email, and password.
 *
 * @property uid A unique identifier for the user.
 * @property username The user's display name.
 * @property email The user's email address.
 * @property password The user's password. In a production environment, ensure that passwords are stored securely.
 */
data class User(
    val uid: String,
    val username: String,
    val email: String,
    val password: String
)
