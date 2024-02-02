package com.example.fypdisastermanagement.individualprofile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.example.fypdisastermanagement.individualprofile.ProfileHeader
import com.example.fypdisastermanagement.individualprofile.ProfileNames
import com.example.fypdisastermanagement.individualprofile.fetchDonationCount
import com.example.fypdisastermanagement.individualprofile.fetchFundraiserCount
import com.example.fypdisastermanagement.individualprofile.fetchVolunteerCount
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

@Composable
fun PublicProfileOfIncidentNotifier( navController: NavController,userId:String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White)
    ){
        ProfileHeader(userId)
        Spacer(modifier = Modifier.height(100.dp))
            ProfileNames(userId)
        Spacer(modifier = Modifier.height(20.dp))
        ProfileStatsForIncidentNotifier(userId)
        Spacer(modifier = Modifier.height(35.dp))
        ReportsCreatedByIncidentNotifier(userId)
        Spacer(modifier = Modifier.height(20.dp))
    }
}



@Composable
fun ProfileStatsForIncidentNotifier(userId: String){
    var noOfreports by rememberSaveable { mutableStateOf("2") }
    var noOfDonations by rememberSaveable { mutableStateOf("10") }
    var volunteerd by rememberSaveable { mutableStateOf("14") } //This show the amount of time user has aggreed to volunteer different campaigns

    LaunchedEffect(userId) {
        volunteerd = fetchVolunteerCount(userId).toString()
        noOfDonations = fetchDonationCount(userId).toString()
        noOfreports = fetchReportCount(userId).toString()
    }
    Surface(
        shadowElevation = 3.5.dp,
        modifier = Modifier
            .height(120.dp)
            .fillMaxWidth()
            .padding(start = 25.dp, end = 25.dp)
            .background(color = Color.White),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White),
            contentAlignment = Alignment.Center
        ) {
            Row {
                Column( horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = noOfreports,
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.sfprodisplaybold)),
                            fontSize = 24.sp,
                            color = Color(0xFF808080),
                            textAlign = TextAlign.Center
                        )
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "Reports",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                            fontSize = 16.sp,
                            color = Color(0xFFEA7970).copy(alpha= 0.8f)
                        )
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = noOfDonations,
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.sfprodisplaybold)),
                            fontSize = 24.sp,
                            color = Color(0xFF808080)
                        )
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "Donations",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                            fontSize = 16.sp,
                            color = Color(0xFFEA7970).copy(alpha= 0.8f)
                        )
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = volunteerd,
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.sfprodisplaybold)),
                            fontSize = 24.sp,
                            color = Color(0xFF808080)
                        )
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "Volunteered",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                            fontSize = 16.sp,
                            color = Color(0xFFEA7970).copy(alpha= 0.8f)
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun ReportsCreatedByIncidentNotifier(userId: String) { //This will show all the report created by specific IncidentNotifier
    var reportImage by rememberSaveable { mutableStateOf((R.drawable.myfundraisersheader)) }
    var reportHeading by rememberSaveable { mutableStateOf("Earthquake-Induced House Damage Report") }
    var reports by remember { mutableStateOf<List<Report>>(emptyList()) }

    LaunchedEffect(userId) {
        reports = fetchReports(userId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .padding(start = 25.dp),
            text = "Reports",
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.sfprodisplaybold)),
                fontSize = 18.sp,
                color = Color.Black
            )
        )
        Spacer(modifier = Modifier.height(12.dp))

        if ( reports.isEmpty()) {
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

            // Display the list of reports once fetched
            reports.forEach { report ->
                Card(
                    shape = RoundedCornerShape(0),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 2.5.dp,
                    ),
                    modifier = Modifier
                        .padding(start = 25.dp, end = 25.dp)
                        .height(100.dp)
                        .fillMaxWidth()
                        .background(color = Color.White)
                        .clickable {}
                )
                {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                //navController.navigate("reportView/${report.reportTitle}")
                            }
                            .background(Color.White),
                    )
                    {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = rememberImagePainter(data = report.reportImage),
                                contentDescription = "null",
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(start = 15.dp, top = 15.dp, bottom = 15.dp),
                                contentScale = ContentScale.Crop,
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = if (report.reportTitle.length > 40) "${
                                    report.reportTitle.take(
                                        40
                                    )
                                }.." else report.reportTitle,
                                style = TextStyle(
                                    fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                                    fontSize = 16.sp,
                                    color = Color(0xFF000000)
                                ),
                                modifier = Modifier.padding(end = 12.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
            }
        }
    }
}
// Function to fetch user details from Firebase using userId
suspend fun fetchPublicUserDetailsFromFirebase(userId: String): PublicUser? {
    val firestore = Firebase.firestore
    val userDocument = firestore.collection("users").document(userId).get().await()

    return if (userDocument.exists()) {
        val firstName = userDocument.getString("firstName") ?: "Unknown"
        val lastName = userDocument.getString("lastName") ?: "User"
        val profileImageUrl = userDocument.getString("profileImageUrl") ?: ""

        PublicUser(firstName, lastName, profileImageUrl)
    } else {
        null
    }
}
data class PublicUser(
    val firstName: String,
    val lastName: String,
    val profileImageUrl: String
)
// Function to fetch the number of reports for a specific user from the "reports" collection
suspend fun fetchReportCount(userId: String): Int {
    val firestore = Firebase.firestore

    try {
        // Query the "reports" collection where "CurrentUid" is equal to the provided userId
        val querySnapshot = firestore.collection("Reports")
            .whereEqualTo("currentUid", userId)
            .get()
            .await()

        // Return the count of documents in the query result
        return querySnapshot.size()
    } catch (e: Exception) {
        // Handle exceptions, such as network errors or Firestore errors
        return 0
    }
}
// Function to fetch all reports created by a specific IncidentNotifier from the "reports" collection
suspend fun fetchReports(userId: String): List<Report> {
    val firestore = Firebase.firestore

    try {
        // Query the "reports" collection where "CurrentUid" is equal to the provided userId
        val querySnapshot = firestore.collection("Reports")
            .whereEqualTo("currentUid", userId)
            .get()
            .await()

        // Map the documents to Report objects
        return querySnapshot.documents.mapNotNull { document ->
            Report(
                reportTitle = document.getString("reportTitle") ?: "",
                reportImage = document.getString("imageUrl") ?: ""
            )
        }
    } catch (e: Exception) {
        // Handle exceptions, such as network errors or Firestore errors
        return emptyList()
    }
}

// Data class to represent a Report
data class Report(
    val reportTitle: String,
    val reportImage: String
)
