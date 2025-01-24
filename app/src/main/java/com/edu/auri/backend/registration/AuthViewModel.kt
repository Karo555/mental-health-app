package com.edu.auri.backend.registration

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore

private const val TAG = "AuthViewModel"

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
        Log.d(TAG, "AuthViewModel initialized")
        checkAuthState()
    }

    /**
     * Checks the current authentication state and updates [authState] accordingly.
     *
     * If there is no current user, the state is set to [AuthState.Unauthenticated];
     * otherwise, it is set to [AuthState.Authenticated].
     */
    private fun checkAuthState() {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Log.d(TAG, "No authenticated user found")
            _authState.value = AuthState.Unauthenticated
        } else {
            Log.d(TAG, "Authenticated user found: ${currentUser.email}")
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
        Log.d(TAG, "Attempting to log in with email: $email")
        if (email.isEmpty() || password.isEmpty()) {
            Log.w(TAG, "Login failed: Fields cannot be empty")
            _authState.value = AuthState.Error("Fields cannot be empty")
            return
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Login successful for user: ${auth.currentUser?.email}")
                    // Ensure that a user document exists when login is successful.
                    ensureUserDocumentExists()
                    _authState.value = AuthState.Authenticated
                } else {
                    Log.e(TAG, "Login failed: ${task.exception?.message}")
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
        Log.d(TAG, "Attempting to sign up with email: $email and name: $name")
        if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
            Log.w(TAG, "Sign-up failed: Fields cannot be empty")
            _authState.value = AuthState.Error("Fields cannot be empty")
            return
        }
        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User creation successful for email: $email")
                    val user = auth.currentUser
                    if (user != null) {
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build()
                        // Wait for the profile update to complete
                        user.updateProfile(profileUpdates)
                            .addOnCompleteListener { updateTask ->
                                if (updateTask.isSuccessful) {
                                    Log.d(TAG, "User profile updated with name: $name")
                                    // Now that the displayName is updated, ensure a user document exists.
                                    ensureUserDocumentExists()
                                    _authState.value = AuthState.Authenticated
                                } else {
                                    Log.e(TAG, "Profile update failed: ${updateTask.exception?.message}")
                                    _authState.value = AuthState.Error(updateTask.exception?.message
                                        ?: "Profile update failed")
                                }
                            }
                    } else {
                        Log.e(TAG, "User creation succeeded but user is null")
                        _authState.value = AuthState.Error("User creation succeeded but user is null")
                    }
                } else {
                    Log.e(TAG, "Sign-up failed: ${task.exception?.message}")
                    _authState.value = AuthState.Error(task.exception?.message ?: "Sign-up failed")
                }
            }
    }

    /**
     * Signs out the current user and updates [authState] to [AuthState.Unauthenticated].
     */
    fun signOut() {
        Log.d(TAG, "Signing out user: ${auth.currentUser?.email}")
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }

    /**
     * Ensures that a Firestore document exists for the current user in the "users" collection.
     *
     * If the document does not exist, it creates a new document with basic user information.
     */
    private fun ensureUserDocumentExists() {
        val user = auth.currentUser
        if (user == null) {
            Log.w(TAG, "ensureUserDocumentExists called but user is null")
            return
        }
        val userDocRef = firestore.collection("users").document(user.uid)
        userDocRef.get().addOnSuccessListener { document ->
            if (!document.exists()) {
                Log.d(TAG, "Creating Firestore document for user: ${user.uid}")
                val userData = hashMapOf(
                    "email" to user.email,
                    "displayName" to user.displayName, // This should now reflect the correct display name
                    "createdAt" to Timestamp.now()
                )
                userDocRef.set(userData)
                    .addOnSuccessListener {
                        Log.d(TAG, "User document created successfully for user: ${user.uid}")
                    }
                    .addOnFailureListener { exception ->
                        Log.e(TAG, "Failed to create user document for user: ${user.uid}, error: ${exception.message}")
                    }
            } else {
                Log.d(TAG, "User document already exists for user: ${user.uid}")
            }
        }.addOnFailureListener { exception ->
            Log.e(TAG, "Failed to fetch user document for user: ${user.uid}, error: ${exception.message}")
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