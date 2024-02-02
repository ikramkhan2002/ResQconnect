package com.example.forgotpasswordsetup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.SemanticsProperties.Password
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fypdisastermanagement.destinations.ForgotPassword
import com.example.fypdisastermanagement.destinations.LoginScreen
import com.example.fypdisastermanagement.destinations.SuccessResetPassword
import com.example.fypdisastermanagement.destinations.VerifyCodeScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPassword(navController: NavController) {
    var password by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val icon =
        if (passwordVisibility)
            painterResource(id = com.example.fypdisastermanagement.R.drawable.passwordvisibility)
        else
            painterResource(id = com.example.fypdisastermanagement.R.drawable.passwordvisibilityoff)
    val borderColor = if (isFocused) Color(0xFF808080) else Color.Black.copy(alpha = 0.15f)

    val labelStyle = TextStyle(
        fontFamily = if (isFocused) FontFamily(Font(com.example.fypdisastermanagement.R.font.sfprodisplaymedium)) else FontFamily(
            Font(com.example.fypdisastermanagement.R.font.sfprodisplayregular)
        ),
        fontSize = if (isFocused) 12.sp else 16.sp,
        color = if (isFocused) Color(0xFF808080) else Color(0xFFCCCCCC)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    )
    {
        IconButton(onClick = { /* Handle back button click */ }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier .clickable { navController.navigate(VerifyCodeScreen.route) }
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Reset Password?",
            modifier = Modifier
                .padding(start = 36.dp),
            style = TextStyle(
                fontFamily = FontFamily(Font(com.example.fypdisastermanagement.R.font.sfprodisplaybold)),
                fontSize = 24.sp,
                color = Color(0xFF000000)
            )
        )
        Text(
            text = "Your new password must be different from\n" +
                    "previous used passwords.",
            modifier = Modifier
                .padding(start = 36.dp, top = 5.dp),
            style = TextStyle(
                fontFamily = FontFamily(Font(com.example.fypdisastermanagement.R.font.sfprodisplayregular)),
                fontSize = 14.sp,
                color = Color(0xFF808080)
            )
        )
        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = {
                Text(
                    text = "Current Password",
                    style = labelStyle
                )
            },
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
            shape = RoundedCornerShape(15),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 36.dp, end = 36.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = borderColor,
                unfocusedBorderColor = borderColor,
                cursorColor = Color.Black
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
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = {
                Text(
                    text = "New Password",
                    style = labelStyle
                )
            },
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
            shape = RoundedCornerShape(15),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 36.dp, end = 36.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = borderColor,
                unfocusedBorderColor = borderColor,
                cursorColor = Color.Black
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
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = {
                Text(
                    text = "Confirm Password",
                    style = labelStyle
                )
            },
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
            shape = RoundedCornerShape(15),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 36.dp, end = 36.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = borderColor,
                unfocusedBorderColor = borderColor,
                cursorColor = Color.Black
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

        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = {
                // Handle button click here
            },
            shape = RoundedCornerShape(15),
            colors = ButtonDefaults.buttonColors(Color(0xFFD34040)),
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .padding(start = 36.dp, end = 36.dp),
        ) {
            Text(
                text = "Reset",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(com.example.fypdisastermanagement.R.font.robotomedium)),
                color = Color(0xFFFFFFFF),
                modifier = Modifier .clickable { navController.navigate(SuccessResetPassword.route) }
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .height(6.dp)
                    .width(140.dp)
                    .background(color = Color(0xFF000000), shape = RoundedCornerShape(100))
                    .align(Alignment.BottomCenter)
            )
        }
    }
}
