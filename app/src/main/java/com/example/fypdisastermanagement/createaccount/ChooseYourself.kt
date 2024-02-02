package com.example.fypdisastermanagement.createaccount


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.ui.Alignment
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*
import androidx.compose.material.*
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Card
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.fypdisastermanagement.R
import com.example.fypdisastermanagement.destinations.LoginScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
// Initialize Firebase Authentication
val auth = FirebaseAuth.getInstance()

// Function to update the user's role in Firestore
fun updateUserRole(role: String) {
    val user = auth.currentUser
    val userId = user?.uid

    if (userId != null) {
        val db = FirebaseFirestore.getInstance()
        val userDocRef = db.collection("users").document(userId)

        userDocRef
            .update("role", role)
            .addOnSuccessListener {
                // Role updated successfully
                // Handle success if needed
            }
            .addOnFailureListener { e ->
                // Handle failure
                // Log.e(TAG, "Error updating role", e)
            }
    }
}
@Composable
fun ChooseYourself(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())

    )
    {
        Spacer(modifier = Modifier.height(120.dp))
        Text(
            text = "Choose Yourself",
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                fontSize = 28.sp,
                color = Color(0xFF000000)
            ),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(60.dp))
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 40.dp)
        ){
            BecomeRescuer(navController)
            Spacer(modifier = Modifier.width(18.dp))
            BecomeProblemReporter(navController)
            Spacer(modifier = Modifier.width(18.dp))
            BecomeSocialWorker(navController)
        }
    }
}

@Composable
fun BecomeRescuer(navController: NavController){
    var isButtonClicked by remember { mutableStateOf(false) }
    val backgroundColor = if (isButtonClicked) Color(0XFFD34040) else Color.White
    val iconColor = if (isButtonClicked) Color.White else Color(0xFFD34040)

    Card(
        shape = RoundedCornerShape(topStart = 100.dp ,topEnd = 100.dp, bottomStart = 100.dp, bottomEnd = 100.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
        modifier = Modifier
            .height(350.dp)
            .background(Color.White)
            .width(180.dp),

    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),

            ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(R.drawable.choose1),
                    contentDescription = "Your Image",
                    modifier = Modifier
                        .aspectRatio(16f/13f)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Rescuer",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                        fontSize = 22.sp,
                        color = Color(0xFF000000)
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Join as a\nRescuer",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
                        fontSize = 16.sp,
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center
                    )
                )
                Spacer(modifier = Modifier.height(22.dp))
                Button(
                    onClick = {
                        isButtonClicked = !isButtonClicked
                        updateUserRole("Rescuer")
                        navController.navigate(LoginScreen.route)
                    },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor),
                    border = BorderStroke(1.dp, Color(0xFFD34040)),
                    modifier = Modifier
                        .size(40.dp),
                    contentPadding = PaddingValues(0.dp),

                    ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.check),
                            modifier = Modifier.size(30.dp),
                            contentDescription = "Check",
                            tint = iconColor,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BecomeProblemReporter(navController: NavController){

    var isButtonClicked by remember { mutableStateOf(false) }
    val backgroundColor = if (isButtonClicked) Color(0XFFD34040) else Color.White
    val iconColor = if (isButtonClicked) Color.White else Color(0xFFD34040)

    Card(
        shape = RoundedCornerShape(topStart = 100.dp ,topEnd = 100.dp, bottomStart = 100.dp, bottomEnd = 100.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
        modifier = Modifier
            .height(350.dp)
            .background(Color.White)
            .width(180.dp),

        ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),

            ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(R.drawable.choose2),
                    contentDescription = "Your Image",
                    modifier = Modifier
                        .aspectRatio(16f/13f)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Incident Notifier",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                        fontSize = 22.sp,
                        color = Color(0xFF000000)
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Be a reporting\nvolunteer",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
                        fontSize = 16.sp,
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center
                    )
                )
                Spacer(modifier = Modifier.height(25.dp))
                Button(
                    onClick = {
                       isButtonClicked = !isButtonClicked
                        updateUserRole("Problem Reporter")
                        navController.navigate(LoginScreen.route)
                    },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor),
                    border = BorderStroke(1.dp, Color(0xFFD34040)),
                    modifier = Modifier
                        .size(40.dp),
                    contentPadding = PaddingValues(0.dp),

                    ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.check),
                            modifier = Modifier.size(30.dp),
                            contentDescription = "Check",
                            tint = iconColor,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BecomeSocialWorker(navController: NavController){

   var isButtonClicked by remember { mutableStateOf(false) }
   val backgroundColor = if (isButtonClicked) Color(0XFFD34040) else Color.White
    val iconColor = if (isButtonClicked) Color.White else Color(0xFFD34040)

    Card(
        shape = RoundedCornerShape(topStart = 100.dp ,topEnd = 100.dp, bottomStart = 100.dp, bottomEnd = 100.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
        modifier = Modifier
            .height(350.dp)
            .background(Color.White)
            .width(180.dp),

        ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),

            ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(R.drawable.choose3),
                    contentDescription = "Your Image",
                    modifier = Modifier
                        .aspectRatio(16f/13f)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Social Worker",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                        fontSize = 22.sp,
                        color = Color(0xFF000000)
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Join as a\nNGO volunteer",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
                        fontSize = 16.sp,
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center
                    )
                )
                Spacer(modifier = Modifier.height(25.dp))
                Button(
                    onClick = {
                       isButtonClicked = !isButtonClicked
                        updateUserRole("Social Worker")
                        navController.navigate(LoginScreen.route)
                    },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor),
                    border = BorderStroke(1.dp, Color(0xFFD34040)),
                    modifier = Modifier
                        .size(40.dp),
                    contentPadding = PaddingValues(0.dp),

                    ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.check),
                            modifier = Modifier.size(30.dp),
                            contentDescription = "Check",
                            tint = iconColor,
                        )
                    }
                }
            }
        }
    }
}

//@Composable
//@Preview
//fun choosePrev(){
//    ChooseYourself()
//}