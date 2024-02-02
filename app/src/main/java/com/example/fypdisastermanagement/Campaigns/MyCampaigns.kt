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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.fypdisastermanagement.Campaigns.CampaignInfo
import com.example.fypdisastermanagement.R
import com.example.fypdisastermanagement.campaignview.fetchAllCampaignsInfo
import com.example.fypdisastermanagement.destinations.RescuerHome
import com.example.fypdisastermanagement.destinations.SocialWorkerHome
import com.example.fypdisastermanagement.destinations.SocialWorkerLandingScreen
import com.example.fypdisastermanagement.destinations.SubmitCampaign
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await


suspend fun fetchMyCampaignsInfo(): List<CampaignInfo> {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val currentUserId = currentUser?.uid
    val firestore = Firebase.firestore
    val collection = firestore.collection("Campaigns")

    return currentUserId?.let { userId ->
        val querySnapshot = collection.whereEqualTo("currentUid", userId).get().await()

        val campaignsList = mutableListOf<CampaignInfo>()

        for (document in querySnapshot.documents) {
            val id = document.id // Use the document ID as campaignId
            val heading = document.getString("campaignTitle") ?: "Default Heading"
            val description = document.getString("campaignDescription") ?: ""
            val image = document.getString("imageUrl") ?: ""
            val startDate = document.getString("campaignStartDate") ?: ""
            val endDate = document.getString("campaignEndDate") ?: ""
            val latitude = document.getDouble("latitude") ?: 0.0
            val longitude = document.getDouble("longitude") ?: 0.0
            val contactNumber = document.getString("contactNo") ?: ""
            val userId = document.getString("currentUid") ?: ""
            val name = "" // You might fetch this information from user data
            val profileImg = "" // You might fetch this information from user data

            val campaignInfo = CampaignInfo(
                heading,
                description,
                image,
                startDate,
                endDate,
                latitude,
                longitude,
                contactNumber,
                userId,
                name,
                profileImg
            )
            campaignsList.add(campaignInfo)
        }

        campaignsList
    } ?: emptyList() // Return empty list if currentUserId is null
}
suspend fun fetchMyCampaignsCount(): Int {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val currentUserId = currentUser?.uid
    val firestore = Firebase.firestore
    val collection = firestore.collection("Campaigns")

    return currentUserId?.let { userId ->
        val querySnapshot = collection.whereEqualTo("currentUid", userId).get().await()
        querySnapshot.size()
    } ?: 0
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCampaigns(navController: NavController) {
    var results by rememberSaveable { mutableStateOf("2") }
    //var campaignHeading by rememberSaveable { mutableStateOf("Blood Donation Camp") }
   // var campaignDes by rememberSaveable { mutableStateOf(("Al-Mustafa Welfare society provides free treatment including")) }
    var campaignImage by rememberSaveable { mutableStateOf((R.drawable.campaignheader)) }
    var date by rememberSaveable { mutableStateOf(("10 July, 2023")) }

    var campaignsList by rememberSaveable { mutableStateOf(emptyList<CampaignInfo>()) }
    var campaignCount by rememberSaveable { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        campaignsList = fetchMyCampaignsInfo()
        campaignCount = fetchMyCampaignsCount()
        results = "$campaignCount"
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
                    modifier = Modifier.clickable { navController.navigate(SocialWorkerLandingScreen.route) }
                )
            }
            Spacer(modifier = Modifier.width(60.dp))
            Text(
                text = "My Campaigns",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                    fontSize = 22.sp,
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center
                ),
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
                    .size(width = 130.dp, height = 35.dp)
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
                        modifier = Modifier.clickable { navController.navigate(SubmitCampaign.route) }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(55.dp))
        if ( campaignsList.isEmpty()) {
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
            campaignsList.forEach { campaign ->
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
                            navController.navigate("myCampaignView/${campaign.campaignHeading}")

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
                                painter = rememberImagePainter(
                                    data = campaign.campaignImage
                                ),
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
                                        text = campaign.startDate,
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
                                        .padding(top = 8.dp)
                                )
                                Text(
                                    text = if (campaign.campaignDes.length > 45) "${
                                        campaign.campaignDes.take(
                                            45
                                        )
                                    }.." else campaign.campaignDes,
                                    style = TextStyle(
                                        fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
                                        fontSize = 12.sp,
                                        color = Color(0xFF707070)
                                    ),
                                    modifier = Modifier
                                        .padding(top = 4.dp)
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