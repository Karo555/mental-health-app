package com.edu.auri.backend.registration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

/**
 * ViewModel responsible for managing user authentication using Firebase Authentication.
 *
 * This ViewModel provides functions to log in, sign up, and sign out users. It exposes a
 * [MutableLiveData] called [authState] that represents the current authentication status.
 */
class AuthViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

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
                    _authState.value = AuthState.Authenticated
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