package com.example.fypdisastermanagement.fundraisers

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
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
import com.example.fypdisastermanagement.Campaigns.CampaignInfo
import com.example.fypdisastermanagement.R
import com.example.fypdisastermanagement.destinations.RescuerHome
import com.example.fypdisastermanagement.destinations.RescuerLandingScreen
import com.example.fypdisastermanagement.destinations.SubmitFundraiser
import com.example.fypdisastermanagement.destinations.SubmitReports
import com.example.safetytips.fetchMyCampaignsInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

suspend fun fetchMyAllFundraisersInfo(): List<FundraiserInfo> {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val currentUserId = currentUser?.uid
    val firestore = Firebase.firestore
    val collection = firestore.collection("Fundraiser")

    return currentUserId?.let { userId ->
        val querySnapshot = collection.whereEqualTo("currentUid", userId).get().await()

        val fundraisersList = mutableListOf<FundraiserInfo>()

        for (document in querySnapshot.documents) {
            val title = document.getString("fundraiserTitle") ?: "Default Title"
            val imageUrl = document.getString("fundraiserImageUrl") ?: ""
            val userId = document.getString("userId") ?: ""
            val startDate = document.getString("startDate") ?: ""
            val endDate = document.getString("endDate") ?: ""
            val description = document.getString("description") ?: ""
            val linkreport = document.getString("linkReport") ?: ""
            val amount = document.getString("amount") ?: ""
            val name = ""
            val profileImg = ""
            val fundraiserInfo = FundraiserInfo(
                title, imageUrl, userId, name, profileImg, description, linkreport,
                amount, startDate, endDate
            )
            fundraisersList.add(fundraiserInfo)
        }

        fundraisersList // Return the populated list
    } ?: emptyList() // Return an empty list if currentUserId is null
}
suspend fun fetchMyFundraisersCount(): Int {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val currentUserId = currentUser?.uid
    val firestore = Firebase.firestore
    val collection = firestore.collection("Fundraiser")

    return currentUserId?.let { userId ->
        val querySnapshot = collection.whereEqualTo("currentUid", userId).get().await()
        querySnapshot.size()
    } ?: 0
}

@Composable
fun MyFundraisers(navController: NavController) {
    var fundraiserHeading by rememberSaveable { mutableStateOf("Raising 100,000 for the\nvictims of flood in karachi") }
    var fundraiserImage by rememberSaveable { mutableStateOf((R.drawable.myfundraisersheader)) }
    var results by rememberSaveable { mutableStateOf("2") }
    var amount by rememberSaveable { mutableStateOf("70,000") }
    var fundraiserList by rememberSaveable { mutableStateOf(emptyList<FundraiserInfo>()) }
    var fundraisersCount by rememberSaveable { mutableStateOf(0) }
    LaunchedEffect(Unit) {
        fundraiserList = fetchMyAllFundraisersInfo()
        fundraisersCount = fetchMyFundraisersCount()
        results = "$fundraisersCount"
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
                    modifier = Modifier.clickable { navController.navigate(RescuerLandingScreen.route) }
                )
            }
            Spacer(modifier = Modifier.width(60.dp))
            Text(
                text = "My Fundraisers",
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
                        modifier = Modifier.clickable { navController.navigate(SubmitFundraiser.route) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(55.dp))
        if (fundraiserList.isEmpty()) {
            Text(
                text = "No Fundraisers to show",
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
            fundraiserList.forEach { fundraiser ->
                var totalDonationAmount by rememberSaveable { mutableStateOf(0) }
                var totalFundraiserAmount by rememberSaveable { mutableStateOf(0) }
                LaunchedEffect(fundraiser.fundraiserTitle) {
                    fetchTotalDonationAmount(fundraiser.fundraiserTitle)
                    totalDonationAmount = fetchTotalDonationAmount(fundraiser.fundraiserTitle)
                    totalFundraiserAmount = fundraiser.amount.toInt()

                }
                Box(
                    modifier = Modifier
                        .background(Color.White)
                        .clickable {
                            navController.navigate("myFundraiserView/${fundraiser.fundraiserTitle}")

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
                            painter = rememberImagePainter(data = fundraiser.fundraiserImageUrl),
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
                                text = if (fundraiser.fundraiserTitle.length > 60) "${
                                    fundraiser.fundraiserTitle.take(
                                        60
                                    )
                                }.." else fundraiser.fundraiserTitle,
                                style = TextStyle(
                                    fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                                    fontSize = 14.sp,
                                    color = Color(0xFF000000)
                                ),
                            )
                            // Calculate progress based on the total donation amount and total fundraiser amount
                            val progress = if (totalFundraiserAmount > 0) {
                                (totalDonationAmount.toFloat() / totalFundraiserAmount.toFloat()).coerceIn(
                                    0f,
                                    1f
                                )
                            } else {
                                0f
                            }
                            Spacer(modifier = Modifier.height(15.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(
                                            style = SpanStyle(
                                                fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                                                fontSize = 12.sp
                                            )
                                        ) {
                                            append("Rs." + fundraiser.amount)
                                        }
                                        append(" ")
                                        withStyle(
                                            style = SpanStyle(
                                                fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
                                                fontSize = 12.sp,
                                                color = Color(0xFF808080)
                                            )
                                        ) {
                                            append("goal")
                                        }
                                    }
                                )
                                Spacer(modifier = Modifier.width(70.dp))

                                val progressPercentage = (progress * 100).toInt()
                                Text(
                                    text = "$progressPercentage%",
                                    style = TextStyle(
                                        fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
                                        fontSize = 12.sp,
                                        color = Color(0xFF808080)
                                    ),
                                )
                            }
                            Spacer(modifier = Modifier.height(5.dp))
                            Surface(
                                shape = RoundedCornerShape(4.dp),

                                ) {
                                LinearProgressIndicator(
                                    progress = progress,
                                    modifier = Modifier
                                        .height(10.dp)
                                        .width(200.dp)
                                        .background(Color.White),
                                    color = Color(0xFFE67070)
                                )
                            }
                        }
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

@Composable
@Preview
fun MyfunPrev(){
    MyFundraisers(navController = rememberNavController())
}