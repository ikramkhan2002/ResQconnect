package com.example.safetytips

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.fypdisastermanagement.R
import com.example.fypdisastermanagement.destinations.ProblemReporterHome
import com.example.fypdisastermanagement.destinations.ProblemReporterLandingScreen
import com.example.fypdisastermanagement.destinations.SubmitReports
import com.example.fypdisastermanagement.fundraisers.fetchMyFundraisersCount
import com.example.fypdisastermanagement.reportview.Report
import com.example.fypdisastermanagement.reportview.calculateAverageRating
import com.example.fypdisastermanagement.reportview.fetchReportsFromFirestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

// Function to fetch reports from Firestore
suspend fun fetchMyReportsFromFirestore(): List<Report> {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val currentUserId = currentUser?.uid
    val firestore = Firebase.firestore
    val collection = firestore.collection("Reports")

    return currentUserId?.let { userId ->
        val querySnapshot = collection.whereEqualTo("currentUid", userId).get().await()

        val reportsList = mutableListOf<Report>()

        for (document in querySnapshot.documents) {
            val reportTitle = document.getString("reportTitle") ?: "Default Title"
            val imageUrl = document.getString("imageUrl")
                ?: "" // Adjust field names as per your Firestore schema
            val description = document.getString("reportDescription") ?: ""
            val latitude = document.getDouble("latitude") ?: 0.0
            val longitude = document.getDouble("longitude") ?: 0.0
            val userId = document.getString("userId") ?: "" // Assuming you have a field for userId
            val name = ""
            val profileImg = ""
            val report = Report(
                reportTitle,
                imageUrl,
                description,
                latitude,
                longitude,
                userId,
                name,
                profileImg
            )
            reportsList.add(report)
        }

        return reportsList
    }
        ?: emptyList() // Return empty list if currentUserId is null
}
suspend fun fetchMyReportsCount(): Int {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val currentUserId = currentUser?.uid
    val firestore = Firebase.firestore
    val collection = firestore.collection("Reports")

    return currentUserId?.let { userId ->
        val querySnapshot = collection.whereEqualTo("currentUid", userId).get().await()
        querySnapshot.size()
    } ?: 0
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyReports(navController: NavController) {
    // var reportHeading by rememberSaveable { mutableStateOf("Heavy rain cause flood in\nKarachi") }
    // var reportImage   by rememberSaveable { mutableStateOf((R.drawable.floodreport)) }
    var results by rememberSaveable { mutableStateOf("2") }
    var ratings by rememberSaveable { mutableStateOf(("4.5")) }
    var views by rememberSaveable { mutableStateOf(("40")) }
    var reports by rememberSaveable { mutableStateOf(emptyList<Report>()) }
    var reportCount by rememberSaveable { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        reports = fetchMyReportsFromFirestore()
        reportCount = fetchMyReportsCount()
        results = "$reportCount"
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, top = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            IconButton(onClick = { /* Handle back button click */ })
            {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier.clickable {
                        navController.navigate(
                            ProblemReporterLandingScreen.route
                        )
                    }
                )
            }
            Spacer(modifier = Modifier.width(70.dp))
            Text(
                text = "My Reports",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                    fontSize = 22.sp,
                    color = Color(0xFF000000)
                )
            )

        }
        Spacer(modifier = Modifier.height(35.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(
                text = results + " Results",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                    fontSize = 18.sp,
                    color = Color(0xFF000000).copy(0.6f)
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    // Handle button click here
                    //          navController.navigate(SubmitReport.route)
                },
                shape = RoundedCornerShape(0),
                colors = ButtonDefaults.buttonColors(Color(0xFFD34040)),
                modifier = Modifier
                    .size(width = 135.dp, height = 35.dp)
                    .padding(end = 20.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "Add New",
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.clickable { navController.navigate(SubmitReports.route) }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(55.dp))

        if (reports.isEmpty()) {
            Text(
                text = "No Reports to show",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
                    fontSize = 14.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
        } else {

            reports.forEach { report ->

                // Fetch and display the average rating for each report
                var averageRating by remember { mutableStateOf("0.0") }

                LaunchedEffect(report.reportTitle) {
                    averageRating = calculateAverageRating(report.reportTitle)
                }

                Box(
                    modifier = Modifier
                        .background(Color.White)
                        .clickable {
                            navController.navigate("myReportView/${report.reportTitle}")

                        }
                )
                {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
                        Image(
                            painter = rememberImagePainter(report.imageUrl),
                            contentDescription = "null",
                            modifier = Modifier
                                .height(75.dp)
                                .width(100.dp)
                                .clip(RoundedCornerShape(4)),
                            contentScale = ContentScale.Crop,
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = if (report.reportTitle.length > 50) "${
                                    report.reportTitle.take(
                                        50
                                    )
                                }.." else report.reportTitle,
                                style = TextStyle(
                                    fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                                    fontSize = 14.sp,
                                    color = Color(0xFF000000),
                                ),
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.star),
                                    contentDescription = "Star",
                                    tint = Color(0XFFD34040),
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "$averageRating",
                                    style = TextStyle(
                                        fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                                        fontSize = 14.sp,
                                        color = Color(0xFF808080)
                                    )
                                )
                            }
                        }
                        //  Spacer(modifier = Modifier.width(10.dp))
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Divider(
                    thickness = 1.5.dp,
                    color = Color(0xFF707070).copy(alpha = 0.16f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}