package com.example.myapplication

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.fypdisastermanagement.R
import com.example.fypdisastermanagement.Reports.fetchReportsDetailsFromFirebase
import com.example.fypdisastermanagement.destinations.Campaigns
import com.example.fypdisastermanagement.destinations.MyReports
import com.example.fypdisastermanagement.destinations.SubmitReports
import com.example.fypdisastermanagement.fundraisers.FundraiserInfo
import com.example.fypdisastermanagement.fundraisers.fetchAllFundraisersInfo
import com.example.fypdisastermanagement.reportview.Report
import com.example.fypdisastermanagement.reportview.calculateAverageRating
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@Composable
fun MyReportView(navController: NavController,reportTitle: String) {
    var noOfFundraisers by rememberSaveable { mutableStateOf("1") }
    var selectedReport: Report? by remember { mutableStateOf(null) }
    LaunchedEffect(Unit) {
        selectedReport = fetchReportsDetailsFromFirebase(reportTitle = reportTitle)
    }
    selectedReport?.let { report ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState()),
        )
        {
            Spacer(modifier = Modifier.height(14.dp))
            MyReportViewHeader(navController,reportTitle)
            Spacer(modifier = Modifier.height(25.dp))
            //GeneralStats()
            // Spacer(modifier = Modifier.height(16.dp))
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
            Spacer(modifier = Modifier.height(30.dp))


        }
    }
}
@Composable
fun MyReportViewHeader(navController: NavController,reportTitle: String) {
  //  var headerImage by rememberSaveable { mutableStateOf((R.drawable.floodreport)) }
   // var reportHeading by rememberSaveable { mutableStateOf("Heavy Rain cause flood in karachi") }
    var ratings   by rememberSaveable { mutableStateOf(("4.5")) }
    var views  by rememberSaveable { mutableStateOf(("40")) }
    var selectedReport: Report? by remember { mutableStateOf(null) }

    LaunchedEffect(Unit) {
        selectedReport = fetchReportsDetailsFromFirebase(reportTitle = reportTitle)
    }
    selectedReport?.let { report ->

        var averageRating by remember { mutableStateOf("0.0") }

        LaunchedEffect(report.reportTitle) {
            averageRating = calculateAverageRating(report.reportTitle)
        }

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
                .clickable { navController.navigate(MyReports.route) },
            contentPadding = PaddingValues(0.dp),

            ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrowback),
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { navController.navigate(MyReports.route) },
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
            painter = rememberImagePainter(report.imageUrl),
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
           // text = ratings,
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                fontSize = 14.sp,
                color = Color(0xFF808080)
            )
        )
    }
    Spacer(modifier = Modifier.height(14.dp))
    Text(
        text = report.reportTitle,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp),
        style = TextStyle(
            fontFamily = FontFamily(Font(R.font.sfprodisplaybold)),
            fontSize = 22.sp,
            color = Color(0xFF000000)
        )
    )
}
}


@Composable
fun GeneralStats(reportTitle: String){
    var views by rememberSaveable { mutableStateOf(("40")) }
    var ratings by rememberSaveable { mutableStateOf(("4.5")) }
    var noOfFundraisers by rememberSaveable { mutableStateOf(("")) }

    var selectedReport: Report? by remember { mutableStateOf(null) }

    LaunchedEffect(Unit) {
        selectedReport = fetchReportsDetailsFromFirebase(reportTitle = reportTitle)
        noOfFundraisers = countFundraisersForReport(reportTitle)
    }
    selectedReport?.let { report ->
    Surface(
        shadowElevation = 1.dp,
        modifier = Modifier
            .height(120.dp)
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp)
            .background(color = Color(0xFFFFFFFF))
            .border(BorderStroke(width = 1.dp, color = Color(0xFF707070).copy(alpha = 0.16f)))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFFFFFFFF))
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                                    fontSize = 16.sp,
                                    color = Color(0xFF707070)
                                )
                            ) {
                                append("Ratings\n")
                            }
                            append("")
                            withStyle(
                                style = SpanStyle(
                                    fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
                                    fontSize = 16.sp,
                                )
                            ) {
                                append(ratings)
                            }
                        },
                        modifier = Modifier
                            .padding(top = 10.dp, start = 15.dp)
                    )
                    Spacer(modifier = Modifier.width(90.dp))
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                                    fontSize = 16.sp,
                                    color = Color(0xFF707070)
                                )
                            ) {
                                append("Views\n")
                            }
                            append("")
                            withStyle(
                                style = SpanStyle(
                                    fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
                                    fontSize = 16.sp,
                                )
                            ) {
                                append(views)
                            }
                        },
                        modifier = Modifier
                            .padding(top = 10.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                                fontSize = 16.sp,
                                color = Color(0xFF707070)
                            )
                        ) {
                            append("Fundraisers\n")
                        }
                        append("")
                        withStyle(
                            style = SpanStyle(
                                fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
                                fontSize = 16.sp,
                            )
                        ) {
                            append(noOfFundraisers)
                        }
                    },
                    modifier = Modifier
                        .padding(start = 15.dp)
                )
            }
        }
    }
}
}

@Composable
fun FundraisersCreated(navController: NavController,reportTitle: String) {
    var fundraiserImage by rememberSaveable { mutableStateOf((R.drawable.floodreport)) }
    var fundraiserHeading by rememberSaveable { mutableStateOf(("Raising 100,000 for the victims of flood in karachi")) }
    var fundraisersList by remember { mutableStateOf(emptyList<FundraiserForReport>()) }

    LaunchedEffect(Unit) {
        fundraisersList = fetchFundraisersForReports(reportTitle)
    }
    if (fundraisersList.isEmpty()) {
        Text(
            text = "No fundrasiers to show",
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
    }
    else {
        fundraisersList.forEach { fundraiser ->
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
                        navController.navigate("fundraiserView/${fundraiser.fundraiserTitle}")

                    }
                    .background(color = Color.White)
            )
            {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                )
                {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = rememberImagePainter(data = fundraiser.imageUrl),
                            contentDescription = "null",
                            modifier = Modifier
                                .size(100.dp)
                                .padding(start = 15.dp, top = 15.dp, bottom = 15.dp),
                            contentScale = ContentScale.Crop,
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = if (fundraiser.fundraiserTitle.length > 55) "${
                                fundraiser.fundraiserTitle.take(
                                    55
                                )
                            }.." else fundraiser.fundraiserTitle,
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
        }
    }
}
// Function to fetch fundraiser details based on report title (report link)

suspend fun fetchFundraisersForReports(reportTitle: String): List<FundraiserForReport> {
    val fundraisersList = mutableListOf<FundraiserForReport>()

    try {
        val fundraisersSnapshot = FirebaseFirestore.getInstance()
            .collection("Fundraiser")
            .whereEqualTo("linkReport", reportTitle)
            .get()
            .await()

        for (document in fundraisersSnapshot.documents) {
            val imageUrl = document.getString("fundraiserImageUrl") ?: ""
            val fundraiserTitle = document.getString("fundraiserTitle") ?: ""

            val fundraiser = FundraiserForReport(imageUrl, fundraiserTitle)
            fundraisersList.add(fundraiser)
        }
    } catch (e: Exception) {
        // Handle any potential exceptions or errors here
        Log.e("Firebase", "Error fetching fundraisers: ${e.message}")
    }

    return fundraisersList
}
data class FundraiserForReport(
    val imageUrl: String,
    val fundraiserTitle: String
    // Other properties related to fundraiser info if any
)


suspend fun countFundraisersForReport(reportTitle: String): String {
    var totalCount = 0

    try {
        val fundraisersSnapshot = FirebaseFirestore.getInstance()
            .collection("Fundraiser")
            .whereEqualTo("linkReport", reportTitle)
            .get()
            .await()

        totalCount = fundraisersSnapshot.size()
    } catch (e: Exception) {
        // Handle any potential exceptions or errors here
        Log.e("Firebase", "Error counting fundraisers: ${e.message}")
    }

    return totalCount.toString()
}
