package com.example.fypdisastermanagement.individualprofile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.fypdisastermanagement.individualprofile.fetchVolunteerCount
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

@Composable
fun PublicProfileOfSocialWorker(navController: NavController, userId:String) {
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
        ProfileStatsForSocialWorker(userId)
        Spacer(modifier = Modifier.height(35.dp))
        CampaignsCreatedBySocialWorker(userId)
        Spacer(modifier = Modifier.height(20.dp))
    }
}


@Composable
fun  ProfileStatsForSocialWorker(userId:String){
    var noOfcampaigns by rememberSaveable { mutableStateOf("2") }
    var noOfDonations by rememberSaveable { mutableStateOf("10") }
    var volunteerd by rememberSaveable { mutableStateOf("14") } //This show the amount of time user has aggreed to volunteer different campaigns
    LaunchedEffect(userId) {
        volunteerd = fetchVolunteerCount(userId).toString()
        noOfDonations = fetchDonationCount(userId).toString()
        noOfcampaigns = fetchCampaignCount(userId).toString()
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
                        text = noOfcampaigns,
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.sfprodisplaybold)),
                            fontSize = 24.sp,
                            color = Color(0xFF808080),
                        )
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "Campaigns",
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
fun CampaignsCreatedBySocialWorker(userId: String) { //This will show all the report created by specific Social worker
    var campaignHeading by rememberSaveable { mutableStateOf("Blood Donation Camp") }
    var campaignDes by rememberSaveable { mutableStateOf(("Al-Mustafa Welfare society provides free treatment including")) }
    var campaignImage by rememberSaveable { mutableStateOf((R.drawable.campaignheader)) }
    var date by rememberSaveable { mutableStateOf(("10 July, 2023")) }
    var campaigns by remember { mutableStateOf<List<Campaign>>(emptyList()) }

    LaunchedEffect(userId) {
        campaigns = fetchCampaigns(userId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .padding(start = 25.dp),
            text = "Campaigns",
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.sfprodisplaybold)),
                fontSize = 18.sp,
                color = Color.Black
            )
        )
        Spacer(modifier = Modifier.height(12.dp))

        if (campaigns.isEmpty()) {
            Text(
                text = "No Campaigns to show",
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

            // Display the list of campaigns once fetched
            campaigns.forEach { campaign ->

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
                            //  navController.navigate("campaignView/${campaign.campaignHeading}")
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
                        Row {
                            Image(
                                painter = rememberImagePainter(data = campaign.campaignImage),
                                contentDescription = "null",
                                modifier = Modifier
                                    .size(110.dp)
                                    .padding(top = 10.dp, start = 5.dp, bottom = 10.dp)
                                    .clip(RoundedCornerShape(4)),
                                contentScale = ContentScale.Crop,
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Row {
                                    Icon(
                                        painter = painterResource(id = R.drawable.calender),
                                        contentDescription = "Calender",
                                        tint = Color(0XFFD34040),
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                    Spacer(modifier = Modifier.width(5.dp))
                                    Text(
                                        text = campaign.date,
                                        style = TextStyle(
                                            fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
                                            fontSize = 12.sp,
                                            color = Color(0xFF808080)
                                        ),
                                        modifier = Modifier
                                            .padding(top = 8.dp)
                                    )
                                }
                                Text(
                                    text = if (campaign.campaignHeading.length > 20) "${
                                        campaign.campaignHeading.take(
                                            20
                                        )
                                    }.." else campaign.campaignHeading,
                                    style = TextStyle(
                                        fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                                        fontSize = 16.sp,
                                        color = Color(0xFF000000)
                                    ),
                                    modifier = Modifier
                                        .padding(top = 8.dp, end = 2.dp)
                                )
                                Text(
                                    text = if (campaign.campaignDes.length > 45) "${
                                        campaign.campaignDes.take(
                                            45)
                                    }.." else campaign.campaignDes,
                                    style = TextStyle(
                                        fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
                                        fontSize = 12.sp,
                                        color = Color(0xFF707070)
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 4.dp, end = 4.dp)
                                )

                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))

            }
        }
    }
}
// Function to fetch the number of campaigns for a specific user from the "campaigns" collection
suspend fun fetchCampaignCount(userId: String): Int {
    val firestore = Firebase.firestore

    try {
        // Query the "campaigns" collection where "CurrentUid" is equal to the provided userId
        val querySnapshot = firestore.collection("Campaigns")
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
// Function to fetch all campaigns created by a specific Social Worker from the "campaigns" collection
suspend fun fetchCampaigns(userId: String): List<Campaign> {
    val firestore = Firebase.firestore

    try {
        // Query the "campaigns" collection where "CreatorUid" is equal to the provided userId
        val querySnapshot = firestore.collection("Campaigns")
            .whereEqualTo("currentUid", userId)
            .get()
            .await()

        // Map the documents to Campaign objects
        return querySnapshot.documents.mapNotNull { document ->
            Campaign(
                campaignHeading = document.getString("campaignTitle") ?: "",
                campaignDes = document.getString("campaignDescription") ?: "",
                campaignImage = document.getString("imageUrl") ?: "",
                date = document.getString("campaignStartDate") ?: ""
            )
        }
    } catch (e: Exception) {
        // Handle exceptions, such as network errors or Firestore errors
        return emptyList()
    }
}

// Data class to represent a Campaign
data class Campaign(
    val campaignHeading: String,
    val campaignDes: String,
    val campaignImage: String,
    val date: String
)
