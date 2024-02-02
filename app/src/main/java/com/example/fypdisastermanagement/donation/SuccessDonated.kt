package com.example.fypdisastermanagement.donation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fypdisastermanagement.R
import com.example.fypdisastermanagement.destinations.Fundraisers
import com.example.fypdisastermanagement.destinations.ProblemReporterLandingScreen
import com.example.fypdisastermanagement.destinations.RescuerHome
import com.example.fypdisastermanagement.destinations.RescuerLandingScreen
import com.example.fypdisastermanagement.destinations.SocialWorkerLandingScreen
import com.example.fypdisastermanagement.login.signInWithEmailAndPassword
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun SuccessDonated(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Image(
            painter = painterResource(R.drawable.successdonated),
            contentDescription = "null",
            modifier = Modifier
                .padding(top = 120.dp)
        )
        Text(
            text = "Success",
            modifier = Modifier
                .padding(top = 24.dp),
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                fontSize = 24.sp,
                color = Color(0xFF000000)
            )
        )
        Text(
            text = "Thank you for your contribution",
            modifier = Modifier
                .padding(top = 2.dp),
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
                fontSize = 14.sp,
                color = Color(0xFF808080)
            )
        )
        Spacer(modifier = Modifier.height(50.dp))
        Button(
            onClick = {
                CoroutineScope(Dispatchers.Main).launch {
                  navigateBasedOnRole(navController)
                }
            },
            shape = RoundedCornerShape(15),
            colors = ButtonDefaults.buttonColors(Color(0xFFD34040)),
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp)
        ) {
            Text(
                text = "Continue",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.robotomedium)),
                color = Color(0xFFFFFFFF),
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .height(6.dp)
                .width(140.dp)
                .background(color = Color(0xFF000000), shape = RoundedCornerShape(100))
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}

suspend fun navigateBasedOnRole(navController: NavController) {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val currentUserId = currentUser?.uid

    currentUserId?.let { userId ->
        val userDocument = Firebase.firestore.collection("users").document(userId).get().await()
        val userRole = userDocument?.getString("role")

        when (userRole) {
            "Rescuer" -> navController.navigate(RescuerLandingScreen.route)
            "Social Worker" -> navController.navigate(SocialWorkerLandingScreen.route)
            "Problem Reporter" -> navController.navigate(ProblemReporterLandingScreen.route)
            else -> navController.navigate(ProblemReporterLandingScreen.route)
        }
    }
}

