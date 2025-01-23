package com.edu.auri.backend.registration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore

/**
 * ViewModel responsible for managing user authentication using Firebase Authentication.
 *
 * This ViewModel provides functions to log in, sign up, and sign out users. It exposes a
 * [MutableLiveData] called [authState] that represents the current authentication status.
 */
class AuthViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    // Internal mutable live data for updating the authentication state.
    private val _authState = MutableLiveData<AuthState>()

    /**
     * LiveData exposing the current authentication state.
     */
    val authState: MutableLiveData<AuthState> = _authState

    init {
        checkAuthState()
    }

    /**
     * Checks the current authentication state and updates [authState] accordingly.
     *
     * If there is no current user, the state is set to [AuthState.Unauthenticated];
     * otherwise, it is set to [AuthState.Authenticated].
     */
    private fun checkAuthState() {
        if (auth.currentUser == null) {
            _authState.value = AuthState.Unauthenticated
        } else {
            // Ensure that a user document exists.
            ensureUserDocumentExists()
            _authState.value = AuthState.Authenticated
        }
    }

    /**
     * Attempts to log in a user with the provided email and password.
     *
     * If the email or password is empty, [authState] is updated with an [AuthState.Error].
     * Otherwise, an asynchronous login operation is initiated. Upon completion, [authState]
     * is updated according to whether the operation was successful or not.
     *
     * @param email The user's email address.
     * @param password The user's password.
     */
    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Fields cannot be empty")
            return
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Ensure that a user document exists when login is successful.
                    ensureUserDocumentExists()
                    _authState.value = AuthState.Authenticated
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Login failed")
                }
            }
    }

    /**
     * Attempts to sign up a new user with the provided email, password, and name.
     *
     * If any of the required fields (email, password, or name) are empty, [authState] is updated
     * with an [AuthState.Error]. Otherwise, the operation is initiated with [AuthState.Loading]
     * until the task completes successfully or fails.
     *
     * This implementation now waits for the user's profile to be updated (with the display name)
     * before ensuring the user document exists in Firestore.
     *
     * @param email The email address to register.
     * @param password The password for the new account.
     * @param name The name of the user.
     */
    fun signUp(email: String, password: String, name: String) {
        if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
            _authState.value = AuthState.Error("Fields cannot be empty")
            return
        }
        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build()
                        // Wait for the profile update to complete
                        user.updateProfile(profileUpdates)
                            .addOnCompleteListener { updateTask ->
                                if (updateTask.isSuccessful) {
                                    // Now that the displayName is updated, ensure a user document exists.
                                    ensureUserDocumentExists()
                                    _authState.value = AuthState.Authenticated
                                } else {
                                    _authState.value = AuthState.Error(updateTask.exception?.message
                                        ?: "Profile update failed")
                                }
                            }
                    } else {
                        _authState.value = AuthState.Error("User creation succeeded but user is null")
                    }
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Sign-up failed")
                }
            }
    }

    /**
     * Signs out the current user and updates [authState] to [AuthState.Unauthenticated].
     */
    fun signOut() {
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }

    /**
     * Ensures that a Firestore document exists for the current user in the "users" collection.
     *
     * If the document does not exist, it creates a new document with basic user information.
     */
    private fun ensureUserDocumentExists() {
        val user = auth.currentUser ?: return
        val userDocRef = firestore.collection("users").document(user.uid)
        userDocRef.get().addOnSuccessListener { document ->
            if (!document.exists()) {
                val userData = hashMapOf(
                    "email" to user.email,
                    "displayName" to user.displayName, // This should now reflect the correct display name
                    "createdAt" to Timestamp.now()
                )
                userDocRef.set(userData)
            }
        }
    }
}

/**
 * Represents the authentication state of the user.
 */
sealed class AuthState {
    /**
     * Indicates that the user is successfully authenticated.
     */
    object Authenticated : AuthState()

    /**
     * Indicates that the user is not authenticated.
     */
    object Unauthenticated : AuthState()

    /**
     * Indicates that an authentication operation is in progress.
     */
    object Loading : AuthState()

    /**
     * Represents an authentication error.
     *
     * @property message A descriptive error message.
     */
    data class Error(val message: String) : AuthState()
}