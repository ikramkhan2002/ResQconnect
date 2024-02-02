package com.example.forgotpasswordsetup

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.runtime.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextButton
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import androidx.navigation.NavController
import com.example.fypdisastermanagement.R
import com.example.fypdisastermanagement.destinations.ForgotPassword
import com.example.fypdisastermanagement.destinations.ResetPassword

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerifyCodeScreen(navController: NavController) {
    var enteredText1 by remember { mutableStateOf("") }
    var enteredText2 by remember { mutableStateOf("") }
    var enteredText3 by remember { mutableStateOf("") }
    var enteredText4 by remember { mutableStateOf("") }


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
                modifier = Modifier .clickable { navController.navigate(ForgotPassword.route) }
            )
        }
        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Verification Code",
            modifier = Modifier
                .padding(start = 36.dp),
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.sfprodisplaybold)),
                fontSize = 24.sp,
                color = Color(0xFF000000)
            )
        )
        Text(
            text = "Please enter the 4-digit verification code\n" +
                    "sent to you.",
            modifier = Modifier
                .padding(start = 36.dp, top = 5.dp),
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
                fontSize = 14.sp,
                color = Color(0xFF808080)
            )
        )
        Spacer(modifier = Modifier.height(50.dp))

        Row(
            modifier = Modifier.
            fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Top
        )
        {
            Box(
                modifier = Modifier
                    .border(1.5.dp, Color(0xFF808080), shape = RoundedCornerShape(20))
                    .width(64.dp)
                    .height(48.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color.White),
                contentAlignment = Alignment.Center
            )
            {
                BasicTextField(
                    value = enteredText1.take(1),
                    onValueChange = {enteredText1 = it.take(1)},
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                        /* Handle the keyboard Done action if needed */
                        }
                    ),
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    ),
                    singleLine = true,

                )
            }

            Box(
                modifier = Modifier
                    .border(1.5.dp, Color(0xFF808080), shape = RoundedCornerShape(20))
                    .width(64.dp)
                    .height(48.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color.White),
                contentAlignment = Alignment.Center
            )
            {
                BasicTextField(
                    value = enteredText2.take(1),
                    onValueChange = {enteredText2 = it.take(1)},
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { /* Handle the keyboard Done action if needed */ }
                    ),
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    ),
                    singleLine = true,

                    )
            }

            Box(
                modifier = Modifier
                    .border(1.5.dp, Color(0xFF808080), shape = RoundedCornerShape(20))
                    .width(64.dp)
                    .height(48.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color.White),
                contentAlignment = Alignment.Center
            )
            {
                BasicTextField(
                    value = enteredText3.take(1),
                    onValueChange = {enteredText3 = it.take(1)},
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { /* Handle the keyboard Done action if needed */ }
                    ),
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    ),
                    singleLine = true,

                    )
            }

            Box(
                modifier = Modifier
                    .border(1.5.dp, Color(0xFF808080), shape = RoundedCornerShape(20))
                    .width(64.dp)
                    .height(48.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color.White),
                contentAlignment = Alignment.Center
            )
            {
                BasicTextField(
                    value = enteredText4.take(1),
                    onValueChange = {enteredText4 = it.take(1)},
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { /* Handle the keyboard Done action if needed */ }
                    ),
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    ),
                    singleLine = true,

                    )
            }

        }
        Spacer(modifier = Modifier.height(45.dp))
        Button(
            onClick = {
                // Handle button click here
            },
            shape = RoundedCornerShape(12),
            colors = ButtonDefaults.buttonColors(Color(0xFFD34040)),
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp),
        ) {
            Text(
                text = "Verify",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.robotomedium)),
                color = Color(0xFFFFFFFF),
                modifier = Modifier .clickable { navController.navigate(ResetPassword.route) }
            )
        }

        Text(
            modifier = Modifier
                .offset(x = 50.dp)
                .padding(top = 12.dp),
            text = "Haven't received the code yet?",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
            color = Color(0xFF808080),
        )
            Text(
                modifier = Modifier
                    .offset(x = 120.dp)
                    .padding(top = 1.dp)
                    .clickable {
                },
                text = "Resend Code",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.sfprodisplaybold)),
                color = Color(0xFFD34040),
            )
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










