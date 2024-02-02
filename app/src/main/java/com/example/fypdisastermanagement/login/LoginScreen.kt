package com.example.fypdisastermanagement.login

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fypdisastermanagement.R
import com.example.fypdisastermanagement.destinations.ForgotPassword
import com.example.fypdisastermanagement.destinations.ProblemReporterHome
import com.example.fypdisastermanagement.destinations.ProblemReporterLandingScreen
import com.example.fypdisastermanagement.destinations.RescuerHome
import com.example.fypdisastermanagement.destinations.RescuerLandingScreen
import com.example.fypdisastermanagement.destinations.SignUp
import com.example.fypdisastermanagement.destinations.SocialWorkerHome
import com.example.fypdisastermanagement.destinations.SocialWorkerLandingScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

suspend fun signInWithEmailAndPassword(email: String, password: String, navController: NavController): Result<Unit> {
    return try {
        val authResult = FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
        val userId = authResult.user?.uid

        // Assuming you have a Firestore collection named "users" where user roles are stored
        val userDocument = userId?.let { FirebaseFirestore.getInstance().collection("users").document(it).get().await() }
        val userRole = userDocument?.getString("role")

        when (userRole) {
            "Rescuer" -> {
                navController.navigate(RescuerLandingScreen.route)
                Result.success(Unit)
            }
            "Social Worker" -> {
                navController.navigate(SocialWorkerLandingScreen.route)
                Result.success(Unit)
            }
            "Problem Reporter" -> {
                navController.navigate(ProblemReporterLandingScreen.route)
                Result.success(Unit)
            }
            else -> {
                navController.navigate(ProblemReporterLandingScreen.route)
                Result.success(Unit)
            }
        }
    } catch (e: FirebaseAuthInvalidUserException) {
        Result.failure(Exception("User not found."))
    } catch (e: FirebaseAuthInvalidCredentialsException) {
        Result.failure(Exception("Invalid email or password."))
    } catch (e: Exception) {
        Result.failure(e)
    }
}



@Composable
fun LoginScreen(navController: NavController) {
    Column(
        Modifier
            .fillMaxHeight()
            .background(color = Color.White)
    ) {
        Spacer(modifier = Modifier.height(0.dp))
        LoginUpperPanel()
        LoginLowerPanel(navController = navController )
    }
}

@Composable
fun LoginUpperPanel(){
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(R.drawable.splashscreen),
            contentDescription = "null",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(300.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginLowerPanel(navController: NavController){
    var username  by remember{ mutableStateOf("") }
    var password  by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val icon =
        if(passwordVisibility)
            painterResource(id = R.drawable.passwordvisibility)
        else
            painterResource(id = R.drawable.passwordvisibilityoff)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color(0xFFFFFFFF),
            )
    ) {
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            leadingIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Search",
                            tint = Color(0xFF808080).copy(alpha = 0.4f),
                            modifier = Modifier
                                .size(25.dp)
                        )
                    }
            },
            placeholder = {
                Text(
                    text = "hiraraheel@gmail.com",
                    fontFamily = FontFamily(Font(R.font.robotoregular)),
                    fontSize = 16.sp,
                    color = Color(0xFFCCCCCC)
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(15),
            modifier = Modifier
                .height(55.dp)
                .fillMaxWidth()
                .background(
                    color = Color(0xFFFFFFFF)
                )
                .padding(start = 30.dp, end = 30.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = Color(0xFFD34040),
                focusedTextColor = Color.Black,
                focusedBorderColor = Color(0xFFD34040),
                unfocusedBorderColor = Color(0xFFE2E2E9)
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            leadingIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Search",
                            tint = Color(0xFF808080).copy(alpha = 0.4f),
                            modifier = Modifier
                                .size(25.dp)
                        )
                    }
            },
            placeholder = {
                Text(
                    text = "Enter your Password",
                    fontFamily = FontFamily(Font(R.font.robotoregular)),
                    fontSize = 16.sp,
                    color = Color(0xFFCCCCCC)
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(15),
            modifier = Modifier
                .height(55.dp)
                .fillMaxWidth()
                .background(
                    color = Color(0xFFFFFFFF)
                )
                .padding(start = 30.dp, end = 30.dp),
            trailingIcon = {
                if (isFocused) {
                    IconButton(onClick = {
                        passwordVisibility = !passwordVisibility
                    }) {
                        Icon(
                            painter = icon,
                            contentDescription = "Visibility Icon"
                        )
                    }
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = Color(0xFFD34040),
                focusedTextColor = Color.Black,
                focusedBorderColor = Color(0xFFD34040),
                unfocusedBorderColor = Color(0xFFE2E2E9)
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            interactionSource = interactionSource,
            visualTransformation =
            if (passwordVisibility)
                VisualTransformation.None
            else
                PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(30.dp))
        val context = LocalContext.current
        Button(
            onClick = {
                CoroutineScope(Dispatchers.Main).launch {
                    val result = signInWithEmailAndPassword(username, password, navController)
                    if (result.isSuccess) {
                        // Navigation is handled inside signInWithEmailAndPassword function based on user role
                    } else {
                        val errorMessage = result.exceptionOrNull()?.message ?: "Invalid email or password"
                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(15))
                .padding(start = 30.dp, end = 30.dp),
            shape = RoundedCornerShape(15),
            colors = ButtonDefaults.buttonColors(Color(0xFFD34040)),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 2.dp
            ),
        ) {
            Text(
                text = "Sign In",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.robotomedium)),
                    fontSize = 20.sp,
                    color = Color.White
                )
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Forget Password?",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.robotomedium)),
                    fontSize = 16.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .clickable { navController.navigate(ForgotPassword.route) }
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Divider(
                thickness = 2.dp,
                color = Color.Black,
               modifier = Modifier
                  .width(110.dp).padding(top = 12.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "OR",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.robotomedium)),
                    fontSize = 16.sp,
                    color = Color.Black
                ),
            )
            Spacer(modifier = Modifier.width(4.dp))
                Divider(
                    thickness = 2.dp,
                    color = Color.Black,
                   modifier = Modifier
                       .width(110.dp).padding(top = 12.dp)
                )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = {},
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp)
                .border(1.5.dp, Color(0xFFD34040), shape = RoundedCornerShape(15)),
            colors = ButtonDefaults.buttonColors(Color.White),
        ) {
            Text(
                text = "Sign Up",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.robotomedium)),
                    fontSize = 16.sp,
                    color = Color(0xFFD34040)
                ),
                modifier = Modifier.clickable { navController.navigate(SignUp.route) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPrev(){
    LoginScreen(navController = rememberNavController())
}

suspend fun signInWithEmailAndPassword(email: String, password: String): Result<Unit> {
    return try {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
        Result.success(Unit)
    } catch (e: FirebaseAuthInvalidUserException) {
        Result.failure(Exception("User not found."))
    } catch (e: FirebaseAuthInvalidCredentialsException) {
        Result.failure(Exception("Invalid email or password."))
    } catch (e: Exception) {
        Result.failure(e)
    }
}






