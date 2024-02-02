package com.example.fypdisastermanagement.reportview

import android.os.Parcel
import android.os.Parcelable
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
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
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
import com.example.fypdisastermanagement.campaignview.fetchTotalCampaignCount
import com.example.fypdisastermanagement.destinations.RescuerHome
import com.example.fypdisastermanagement.destinations.SignUp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Reports(navController: NavController) {
    var search by rememberSaveable { mutableStateOf("") }
    var reportHeading by rememberSaveable { mutableStateOf("Heavy rain cause flood in\nKarachi") }
    var reportImage by rememberSaveable { mutableStateOf((R.drawable.floodreport)) }
  //  var profileImage by rememberSaveable { mutableStateOf((R.drawable.profile)) }
   // var name by rememberSaveable { mutableStateOf(("Hira Raheel")) }
    var results by rememberSaveable { mutableStateOf("0") }
    var ratings by rememberSaveable { mutableStateOf(("4.5")) }
    var views by rememberSaveable { mutableStateOf(("40")) }

    var reports by rememberSaveable { mutableStateOf(emptyList<Report>()) }

    LaunchedEffect(Unit) {
        reports = fetchReportsFromFirestore()
        val totalCount = fetchTotalReportCount()
        results = "$totalCount"
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
    )
    {
        Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Reports",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                    fontSize = 22.sp,
                    color = Color(0xFF000000)
                ),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        Spacer(modifier = Modifier.height(35.dp))
        Text(
            text = results + " Results",
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                fontSize = 18.sp,
                color = Color(0xFF000000).copy(0.6f)
            ),
            modifier = Modifier.padding(start= 20.dp)
        )
        Spacer(modifier = Modifier.height(55.dp))

        if (  reports.isEmpty()) {
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
                            navController.navigate("reportView/${report.reportTitle}")
                        }
                )
                {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = rememberImagePainter(data = report.imageUrl),
                            contentDescription = null,
                            modifier = Modifier
                                .height(70.dp)
                                .width(80.dp)
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
                                    color = Color(0xFF000000)
                                )
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = rememberImagePainter(data = report.profileImageUrl),
                                    contentDescription = "null",
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clip(shape = CircleShape),
                                    contentScale = ContentScale.Crop,
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = report.userName,
                                    style = TextStyle(
                                        fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                                        fontSize = 12.sp,
                                        color = Color(0xFF808080)
                                    )
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Star",
                                    tint = Color(0XFFD34040),
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "$averageRating",
                                    style = TextStyle(
                                        fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                                        fontSize = 12.sp,
                                        color = Color(0xFF808080)
                                    )
                                )
                            }
                        }

                    }
                    //Spacer(modifier = Modifier.height(12.dp))
                }
                Spacer(modifier = Modifier.height(12.dp))
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

// Function to fetch reports from Firestore
suspend fun fetchReportsFromFirestore(): List<Report> {
    val firestore = Firebase.firestore
    val collection = firestore.collection("Reports")

    val querySnapshot = collection.get().await()

    val reportsList = mutableListOf<Report>()

    for (document in querySnapshot.documents) {
        val reportTitle = document.getString("reportTitle") ?: "Default Title"
        val imageUrl = document.getString("imageUrl") ?: "" // Adjust field names as per your Firestore schema
        val description = document.getString("reportDescription") ?: ""
        val latitude = document.getDouble("latitude") ?: 0.0
        val longitude = document.getDouble("longitude") ?: 0.0
        val userId = document.getString("userId") ?: "" // Assuming you have a field for userId
        val currentUid = document.getString("currentUid") ?: ""

        val userDocument = firestore.collection("users").document(currentUid).get().await()
        val firstName = userDocument.getString("firstName") ?: "Unknown"
        val lastName = userDocument.getString("lastName") ?: "User"

        // Combine first name and last name to form the user's full name
        val userName = "$firstName $lastName"
        val profileImageUrl = userDocument.getString("profileImageUrl") ?: ""
        val report = Report(reportTitle, imageUrl, description, latitude , longitude,userId, userName, profileImageUrl)
        reportsList.add(report)
    }

    return reportsList
}


data class Report(
    val reportTitle: String,
    val imageUrl: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val userId: String,
    val userName: String,
    val profileImageUrl: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(reportTitle)
        parcel.writeString(imageUrl)
        parcel.writeString(description)
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
        parcel.writeString(userId)
        parcel.writeString(userName)
        parcel.writeString(profileImageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Report> {
        override fun createFromParcel(parcel: Parcel): Report {
            return Report(parcel)
        }

        override fun newArray(size: Int): Array<Report?> {
            return arrayOfNulls(size)
        }
    }
}


suspend fun calculateAverageRating(reportTitle: String): String {
    val firestore = Firebase.firestore
    val collection = firestore.collection("Ratings")

    val querySnapshot = collection
        .whereEqualTo("reportTitle", reportTitle)
        .get()
        .await()

    if (querySnapshot.isEmpty) {
        return "0.0"
    }

    var totalRating = 0
    var totalRatingsCount = 0

    for (document in querySnapshot.documents) {
        val rating = document.getLong("rating")?.toInt() ?: 0
        totalRating += rating
        totalRatingsCount++
    }

    val averageRating = if (totalRatingsCount > 0) {
        totalRating.toDouble() / totalRatingsCount
    } else {
        0.0
    }

    return String.format("%.1f", averageRating)
}
// Function to fetch total count of reports from Firestore
suspend fun fetchTotalReportCount(): Int {
    val firestore = Firebase.firestore
    val collection = firestore.collection("Reports")

    val querySnapshot = collection.get().await()

    return querySnapshot.size()
}

/*
@Composable
@Preview
fun RepPrev(){
    Reports()
}*/