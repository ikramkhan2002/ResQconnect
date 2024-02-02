package com.example.fypdisastermanagement.fundraisers

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.fypdisastermanagement.R
import com.example.fypdisastermanagement.destinations.Campaigns
import com.example.fypdisastermanagement.destinations.DonateMoney
import com.example.fypdisastermanagement.destinations.ForgotPassword
import com.example.fypdisastermanagement.destinations.Fundraisers
import com.example.fypdisastermanagement.fundraisers.FundraiserInfo
import com.example.fypdisastermanagement.fundraisers.fetchFundraiserDetailsFromFirebase
import com.example.fypdisastermanagement.reportview.Report
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

suspend fun fetchFundraiserReportsFromFirestore(reportTitle: String): List<Report> {
    val firestore = Firebase.firestore
    val collection = firestore.collection("Reports")

    try {
        val querySnapshot = collection
            .whereEqualTo("reportTitle", reportTitle) // Filter by the report title
            .get()
            .await()

        val reportsList = mutableListOf<Report>()

        for (document in querySnapshot.documents) {
            val fetchedReportTitle = document.getString("reportTitle") ?: "Default Title"
            val imageUrl = document.getString("imageUrl") ?: ""
            val description = document.getString("reportDescription") ?: ""
            val latitude = document.getDouble("latitude") ?: 0.0
            val longitude = document.getDouble("longitude") ?: 0.0
            val userId = document.getString("userId") ?: ""
            val currentUid = document.getString("currentUid") ?: ""


            val userDocument = firestore.collection("users").document(currentUid).get().await()
            val firstName = userDocument.getString("firstName") ?: ""
            val lastName = userDocument.getString("lastName") ?: ""
            val userName = "$firstName $lastName"
            val profileImageUrl = userDocument.getString("profileImageUrl") ?: ""
            val report = Report(fetchedReportTitle, imageUrl, description, latitude, longitude, userId, userName, profileImageUrl)
            reportsList.add(report)
        }

        return reportsList
    } catch (e: Exception) {
        // Handle exceptions or errors here
        e.printStackTrace()
    }

    return emptyList() // Return an empty list if there are no reports or in case of errors
}


@Composable
fun FundraiserView(navController: NavController,fundraiserTitle: String ) {
    var headerImage by rememberSaveable { mutableStateOf((R.drawable.fundraisersheader)) }
    var reportImage by rememberSaveable { mutableStateOf((R.drawable.myfundraisersheader)) }
    var fundraiserHeading by rememberSaveable { mutableStateOf("Raising 100,000 for the victims of flood in karachi") }
    var name by rememberSaveable { mutableStateOf(("Hira Raheel")) }
    var profileImage by rememberSaveable { mutableStateOf((R.drawable.profile)) }
    var amount by rememberSaveable { mutableStateOf("32,500") }
    var description by rememberSaveable { mutableStateOf("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the.") }
    var reportHeading by rememberSaveable { mutableStateOf("Earthquake-Induced House Damage Report") }

    var selectedFundraiser by remember { mutableStateOf<FundraiserInfo?>(null) }
    var selectedReport by remember { mutableStateOf<Report?>(null) }
    var totalFundraiserAmount by rememberSaveable { mutableStateOf(0) }
    var totalDonationAmount by rememberSaveable { mutableStateOf(0) }
    var status by rememberSaveable { mutableStateOf("Ongoing") }
    LaunchedEffect(Unit) {
        selectedFundraiser = fetchFundraiserDetailsFromFirebase(fundraiserTitle)
        selectedFundraiser?.let { fundraiser ->
            val reportsList = fetchFundraiserReportsFromFirestore(fundraiser.linkReport)
            selectedReport = reportsList.firstOrNull { it.reportTitle == fundraiser.linkReport }

            totalFundraiserAmount = fundraiser.amount.toInt()
            totalDonationAmount = fetchTotalDonationAmount(fundraiserTitle)
        }
    }

    selectedFundraiser?.let { fundraiser ->
        selectedReport?.let { report ->
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
                        // .fillMaxWidth()
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
                            .clickable { navController.navigate(Fundraisers.route) },
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
                            data = fundraiser.fundraiserImageUrl,
                        ),
                        contentDescription = "null",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .padding(start = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = rememberImagePainter(
                            data = fundraiser.profileImageUrl,
                        ),
                        contentDescription = "null",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(shape = CircleShape)
                            .clickable {
                                navController.navigate("rescuerPublic/${fundraiser.userId}")

                            },
                        contentScale = ContentScale.Crop,
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = fundraiser.userName,
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                            fontSize = 16.sp,
                            color = Color(0xFF808080)
                        ),
                        modifier = Modifier
                            .clickable {
                                navController.navigate("rescuerPublic/${fundraiser.userId}")

                            },
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = fundraiser.fundraiserTitle,
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.sfprodisplaybold)),
                        fontSize = 20.sp,
                        color = Color(0xFF000000)
                    ),
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Calculate the status based on the fundraiser's total amount and total donation amount
                status = if (totalDonationAmount >= totalFundraiserAmount) {
                    "Completed"
                } else {
                    "Ongoing"
                }
                Text(
                    text = status,
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                        fontSize = 14.sp,
                        color = if (status == "Completed") Color(0xFF34CA96) else Color(0xFFD34040)
                    ),
                    modifier = Modifier
                        .padding(start = 20.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = fundraiser.description,
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
                        fontSize = 14.sp,
                        color = Color(0xFF707070),
                        textAlign = TextAlign.Justify
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp)
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Rs. " + fundraiser.amount,
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.sfprodisplaybold)),
                        fontSize = 18.sp,
                        color = Color(0xFF000000)
                    ),
                    modifier = Modifier.padding(start = 20.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                // Calculate progress based on the total donation amount and total fundraiser amount
                val progress = if (totalFundraiserAmount > 0) {
                    (totalDonationAmount.toFloat() / totalFundraiserAmount.toFloat()).coerceIn(0f, 1f)
                } else {
                    0f
                }
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp)
                ) {
                    LinearProgressIndicator(
                        progress = progress,
                        modifier = Modifier
                            .height(10.dp)
                            .fillMaxWidth()
                            .background(Color.White),
                        color = Color(0xFFE67070)
                    )
                }
                val progressPercentage = (progress * 100).toInt()
                Text(
                    text = "$progressPercentage%",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                        fontSize = 14.sp,
                        color = Color(0xFFD34040)
                    ),
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 20.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Fundraiser For",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.sfprodisplaybold)),
                        fontSize = 16.sp,
                        color = Color(0xFF000000)
                    ),
                    modifier = Modifier
                        .padding(start = 20.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    shape = RoundedCornerShape(0),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 2.5.dp,
                    ),
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp)
                        .height(100.dp)
                        .fillMaxWidth()
                        .clickable {


                        }
                        .background(color = Color.White)

                )
                {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                navController.navigate("reportView/${report.reportTitle}")
                            }
                            .background(Color.White),
                    )
                    {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = rememberImagePainter(data = report.imageUrl),
                                contentDescription = "null",
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(start = 15.dp, top = 15.dp, bottom = 15.dp),
                                contentScale = ContentScale.Crop,
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = if (report.reportTitle.length > 40) "${report.reportTitle.take(40)}.." else report.reportTitle,
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
                Spacer(modifier = Modifier.height(50.dp))
                Button(
                    onClick = {
                        navController.navigate("donation/${fundraiser.fundraiserTitle}")

                    },
                    shape = RoundedCornerShape(15),
                    colors = ButtonDefaults.buttonColors(Color(0xFFD34040)),
                    modifier = Modifier
                        .height(48.dp)
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp)
                        .clickable {

                        }
                ) {
                    Text(
                        text = "Donate",
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.robotomedium)),
                        color = Color(0xFFFFFFFF),
                    )
                }
                Spacer(modifier = Modifier.height(35.dp))
            }
        }
    }
}

// Function to fetch the total donation amount for a specific fundraiser from Firebase
suspend fun fetchTotalDonationAmount(fundraiserTitle: String): Int {
    val firestore = Firebase.firestore
    val donationCollection = firestore.collection("donations")

    return try {
        val querySnapshot = donationCollection
            .whereEqualTo("fundraiserName", fundraiserTitle)
            .get()
            .await()

        var totalAmount = 0

        for (document in querySnapshot.documents) {
            val donationAmount = document.getString("donationAmount")?.toIntOrNull() ?: 0
            totalAmount += donationAmount
        }

        totalAmount
    } catch (e: Exception) {
        // Handle exceptions or errors here
        e.printStackTrace()
        0
    }
}



//@Preview
//@Composable
//fun fundView(){
//    FundraiserView(navController = rememberNavController(), fundraiserTitle = )
//}