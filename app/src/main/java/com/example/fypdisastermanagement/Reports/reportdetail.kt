package com.example.fypdisastermanagement.Reports



import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import coil.compose.rememberImagePainter
import com.example.fypdisastermanagement.R
import com.example.fypdisastermanagement.destinations.AllReports
import com.example.fypdisastermanagement.destinations.Campaigns
import com.example.fypdisastermanagement.destinations.LoginScreen
import com.example.fypdisastermanagement.destinations.SubmitFundraiser
import com.example.fypdisastermanagement.rating.RatingBar
import com.example.fypdisastermanagement.rating.submitRatingToFirebase
import com.example.fypdisastermanagement.reportview.Report
import com.example.fypdisastermanagement.reportview.calculateAverageRating
import com.example.myapplication.FundraisersCreated
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.tasks.await


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ReportView(navController: NavController, reportTitle: String) {
    var headerImage by rememberSaveable { mutableStateOf((R.drawable.floodreport)) }
    var ratings by rememberSaveable { mutableStateOf(("0.0")) }
    var views by rememberSaveable { mutableStateOf(("40")) }
    var name by rememberSaveable { mutableStateOf(("Hira Raheel")) }
    var profileImage by rememberSaveable { mutableStateOf((R.drawable.profile)) }
    var reportHeading by rememberSaveable { mutableStateOf("Heavy rain cause flood in\n" + "Karachi") }
    var description by rememberSaveable {
        mutableStateOf(
            (
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
                            "Aenean ut felis sed ex finibus lobortis. Mauris nec eros" +
                            "finibus, mattis lectus eget, placerat odio. Aliquam" +
                            "vehicula imperdiet sollicitudin. Praesent auctor justo" +
                            "ullamcorper lobortis ornare. In sed varius lorem." +
                            "Pellentesque blandit cursus sodales. Integer sed" +
                            "aliquam ligula, vel ultrices eros")
        )
    }
    // var photo1 by rememberSaveable { mutableStateOf((R.drawable.floodreport1)) }
    // var photo2 by rememberSaveable { mutableStateOf((R.drawable.floodreport2)) }
    // var photo3 by rememberSaveable { mutableStateOf((R.drawable.floodreport3)) }
    var selectedReport: Report? by remember { mutableStateOf(null) }
    var userRole: String? by remember { mutableStateOf(null) }
    var ratingState by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        selectedReport = fetchReportsDetailsFromFirebase(reportTitle = reportTitle)
        userRole = getUserRoleFromFirebase()
        val averageRating = calculateAverageRating(reportTitle)
        ratings = averageRating
    }
    LaunchedEffect(ratingState) {
        // Launch a coroutine to submit the rating to Firebase when the rating is greater than zero
        if (ratingState > 0) {
            submitRatingToFirebase(ratingState, reportTitle)
        }
    }
    selectedReport?.let { report ->

        var averageRating by remember { mutableStateOf("0.0") }

        LaunchedEffect(report.reportTitle) {
            averageRating = calculateAverageRating(report.reportTitle)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState()),
        )
        {
            Spacer(modifier = Modifier.height(14.dp))
            Row(
                modifier = Modifier
                    .padding(start = 12.dp)
            )
            {
                Button(
                    onClick = {
                        // Handle button click here
                    },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(Color(0xFF808080).copy(0.1f)),
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { navController.navigate(AllReports.route) },
                    contentPadding = PaddingValues(0.dp),

                    ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrowback),
                            modifier = Modifier
                                .size(20.dp),
                            contentDescription = "Icon-Back",
                            tint = Color.Black,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)
                    .clip(RoundedCornerShape(5))
                    .background(Color.White)

            )
            {
                Image(
                    painter = rememberImagePainter(
                        data = report.imageUrl),
                    contentDescription = "null",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, end = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.star),
                    contentDescription = "Star",
                    tint = Color(0XFFD34040),
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "$averageRating",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                        fontSize = 14.sp,
                        color = Color(0xFF808080)
                    )
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier
                    .padding(start = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = rememberImagePainter(report.profileImageUrl),
                    contentDescription = "null",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(shape = CircleShape)
                        .clickable {
                            navController.navigate("incidentNotifierPublic/${report.userId}")

                        },
                    contentScale = ContentScale.Crop,
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "By ${report.userName}",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                        fontSize = 16.sp,
                        color = Color(0xFF808080)
                    ),
                    modifier = Modifier.clickable {
                        navController.navigate("incidentNotifierPublic/${report.userId}")

                    },
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = report.reportTitle,
                modifier = Modifier.padding(start = 20.dp),
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.sfprodisplaybold)),
                    fontSize = 22.sp,
                    color = Color(0xFF000000)
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Live Location",
                modifier = Modifier.padding(start = 20.dp),
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                    fontSize = 16.sp,
                    color = Color(0xFF808080)
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .height(270.dp)
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
                    .background(color = Color(0xFFFFFFFF))
                  //  .border(BorderStroke(width = 0.5.dp, color = Color.Black))
            ) {
                //map

                val location = LatLng(report.latitude, report.longitude)
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = rememberCameraPositionState {
                        position = CameraPosition.fromLatLngZoom(location, 10f)
                    }
                ) {
                    Marker(
                        state = MarkerState(position = location),
                        title = "Campaign Location",
                    )
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = report.description,
                modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
                    fontSize = 15.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Justify,
                    lineHeight = 22.sp
                )
            )
            Spacer(modifier = Modifier.height(35.dp))
            Divider(
                thickness = 1.5.dp,
                color = Color(0xFF707070).copy(alpha = 0.16f),
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Fundraisers",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                    fontSize = 18.sp,
                    color = Color.Black
                ),
                modifier = Modifier
                    .padding(start = 20.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            FundraisersCreated(navController,reportTitle)
            Spacer(modifier = Modifier.height(40.dp))
            RatingBar(onRatingSelected = { rating ->
                ratingState = rating
            })
            Spacer(modifier = Modifier.height(50.dp))
            userRole?.let { role ->
                if (role == "Rescuer") {
                    Button(
                        onClick = {
                            // Handle button click here
                        },
                        shape = RoundedCornerShape(15),
                        colors = ButtonDefaults.buttonColors(Color(0xFFD34040)),
                        modifier = Modifier
                            .padding(start = 20.dp, end = 20.dp)
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text(
                            text = "Start a fundraiser",
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.robotomedium)),
                            color = Color(0xFFFFFFFF),
                            modifier = Modifier.clickable { navController.navigate(SubmitFundraiser.route) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(35.dp))
        }
    }
}

// Function to fetch campaign details from Firebase using campaignTitle

suspend fun fetchReportsDetailsFromFirebase(reportTitle: String): Report? {
    val firestore = Firebase.firestore
    val collection = firestore.collection("Reports")

    try {
        val querySnapshot = collection
            .whereEqualTo("reportTitle", reportTitle) // Query for repTitle
            .get()
            .await()

        val document = querySnapshot.documents.firstOrNull()

        document?.let {
            val reportHeading = it.getString("reportTitle") ?: "Default Title"
            val imageUrl = it.getString("imageUrl") ?: ""
            val description = it.getString("reportDescription") ?: ""
            val latitude = it.getDouble("latitude") ?: 0.0
            val longitude = it.getDouble("longitude") ?: 0.0
            val currentUid = it.getString("currentUid") ?: ""
            // Fetch user name using UID
            val userDocument = firestore.collection("users").document(currentUid).get().await()
            val firstName = userDocument.getString("firstName") ?: "Unknown"
            val lastName = userDocument.getString("lastName") ?: "User"

            // Combine first name and last name to form the user's full name
            val userName = "$firstName $lastName"
            val profileImageUrl = userDocument.getString("profileImageUrl") ?: ""

            return Report(reportHeading, imageUrl,description, latitude, longitude,currentUid, userName , profileImageUrl)
        }
    } catch (e: Exception) {
        // Handle any potential exceptions or errors during fetching
        // Logging, error reporting, or fallback logic can be added here
        e.printStackTrace()
    }

    return null
}

// Function to get the current user's ID from Firebase Authentication
fun getCurrentUserId(): String? {
    val currentUser = FirebaseAuth.getInstance().currentUser
    return currentUser?.uid
}

// Function to get the current user's role from Firebase using the current user's ID
suspend fun getUserRoleFromFirebase(): String? {
    val currentUserId = getCurrentUserId()

    currentUserId?.let { userId ->
        val firestore = Firebase.firestore
        val userDocument = firestore.collection("users").document(userId).get().await()

        return userDocument.getString("role")
    }

    return null
}



//
//@Preview
//@Composable
//fun ReportViewPrev(){
//    ReportView()
//}
//

