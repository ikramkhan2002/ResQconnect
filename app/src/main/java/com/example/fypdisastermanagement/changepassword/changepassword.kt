package com.example.fypdisastermanagement.changeprofile

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import android.widget.Toast
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.Email
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


@Composable
fun ChangeEmailPasswordScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }

    var passwordVisibility by remember { mutableStateOf(false) }
    var newPasswordVisibility by remember { mutableStateOf(false) }

    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    val authViewModel: AuthViewModel = viewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = VisualTransformation.None, // Always show clear text
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    // Perform action when done button is clicked on the keyboard
                    // For example, you can call a function to change email and password here
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )


        OutlinedTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = { Text("New Password") },
            visualTransformation = VisualTransformation.None, // Always show clear text
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    // Perform action when done button is clicked on the keyboard
                    // For example, you can call a function to change email and password here
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        val context = LocalContext.current
        Button(
            onClick = {
                authViewModel.changeEmailAndPassword(context, email, password, newPassword)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            if (loading) {
                CircularProgressIndicator()
            } else {
                Text("Change Password")
            }
        }

        if (error != null) {
            Text(
                text = error!!,
                color = Color.Red,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}


class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()


    fun changeEmailAndPassword(context: Context, email: String, password: String, newPassword: String) {
        if (email.isEmpty()) {
            showToast(context, "Enter current email")
            return
        }

        if (password.isEmpty()) {
            showToast(context, "Enter current password")
            return
        }

        if (newPassword.isEmpty()) {
            showToast(context, "Enter new password")
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { signInTask ->
                if (signInTask.isSuccessful) {
                    // User successfully signed in, now change the email and password
                    val user = auth.currentUser
                    user?.updateEmail(email)?.addOnCompleteListener { updateEmailTask ->
                        if (updateEmailTask.isSuccessful) {
                            // Email updated successfully, now update the password
                            user.updatePassword(newPassword).addOnCompleteListener { updatePasswordTask ->
                                if (updatePasswordTask.isSuccessful) {
                                    // Password updated successfully, now update the password in Firestore
                                    updatePasswordInFirestore(user.uid, newPassword)
                                    showToast(context, "Email and password changed successfully")
                                    // You can perform additional actions or navigate to another screen
                                } else {
                                    // Handle password update failure
                                    showToast(context, "Failed to update password")
                                }
                            }
                        } else {
                            // Handle email update failure
                            showToast(context, "Failed to update email")
                        }
                    }
                } else {
                    // Handle sign-in failure
                    showToast(context, "Authentication failed")
                }
            }
    }

    private fun showToast(context: Context, message: String) {
        // Use Toast to show a message to the user
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


    private fun updatePasswordInFirestore(userId: String, newPassword: String) {
        // Update the password field in the 'users' collection in Firestore
        val userRef = firestore.collection("users").document(userId)
        userRef.update("password", newPassword)
            .addOnSuccessListener {
                // Password in Firestore updated successfully
            }
            .addOnFailureListener {
                // Handle failure to update password in Firestore
            }
    }
}

@Preview
@Composable
fun editPrev(){
    ChangeEmailPasswordScreen()
}