package com.example.myapplication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import com.example.fypdisastermanagement.Campaigns.CampaignInfo
import com.example.fypdisastermanagement.Campaigns.fetchCampaignDetailsFromFirebase
import com.example.fypdisastermanagement.R
import com.example.fypdisastermanagement.destinations.MyCampaigns
import com.example.fypdisastermanagement.destinations.MyReports
import com.example.fypdisastermanagement.destinations.SocialWorkerLandingScreen
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun MyCampaignView(navController: NavController,campaignTitle: String) {
    var status by rememberSaveable { mutableStateOf("Ongoing") }
    var selectedCampaign: CampaignInfo? by remember { mutableStateOf(null) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState()),
        ) {
            Spacer(modifier = Modifier.height(14.dp))
            MyCamapignViewHeader(navController,campaignTitle)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = status,
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                    fontSize = 16.sp,
                    color = Color(0xFFD34040)
                ),
                modifier = Modifier
                    .padding(start = 20.dp)
            )
            Spacer(modifier = Modifier.height(25.dp))
            GeneralStatsForCampaign(campaignTitle)
            Spacer(modifier = Modifier.height(35.dp))
            Divider(
                thickness = 1.5.dp,
                color = Color(0xFF707070).copy(alpha = 0.16f),
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Volunteers",
                modifier = Modifier.padding(start = 20.dp),
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                    fontSize = 20.sp,
                    color = Color(0xFF000000)
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Volunteers(campaignTitle)
            Spacer(modifier = Modifier.height(30.dp))
        }

    }

    @Composable
    fun MyCamapignViewHeader(navController: NavController,campaignTitle: String) {
        var headerImage by rememberSaveable { mutableStateOf((R.drawable.campaignheader)) }
        var campaignHeading by rememberSaveable { mutableStateOf("Blood Donation Camp") }
        var selectedCampaign: CampaignInfo? by remember { mutableStateOf(null) }

        LaunchedEffect(Unit) {
            selectedCampaign = fetchCampaignDetailsFromFirebase(campaignTitle)

        }

        selectedCampaign?.let { campaign ->
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
                        .clickable { navController.navigate(MyCampaigns.route) },
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
                                .clickable { navController.navigate(
                                   MyCampaigns.route) },
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
                    painter = rememberImagePainter(data = campaign.campaignImage),
                    contentDescription = "null",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = campaign.campaignHeading,
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
    fun GeneralStatsForCampaign(campaignTitle: String) {
        var createdDate by rememberSaveable { mutableStateOf(("10 July, 2023")) }
        var startDate by rememberSaveable { mutableStateOf(("15 July, 2023")) }
        var endDate by rememberSaveable { mutableStateOf(("20 July, 2023")) }
        var noOfVolunteers by rememberSaveable { mutableStateOf(("5")) }
        var selectedCampaign: CampaignInfo? by remember { mutableStateOf(null) }

        LaunchedEffect(Unit) {
            selectedCampaign = fetchCampaignDetailsFromFirebase(campaignTitle)
            fetchVolunteersCountFromFirebase(campaignTitle) { count ->
                noOfVolunteers = count.toString()
            }
        }

        selectedCampaign?.let { campaign ->
            Surface(
                shadowElevation = 1.dp,
                modifier = Modifier
                    .height(130.dp)
                    .fillMaxWidth()
                    .padding(start = 25.dp, end = 25.dp)
                    .background(color = Color(0xFFFFFFFF))
                    .border(
                        BorderStroke(
                            width = 1.dp,
                            color = Color(0xFF707070).copy(alpha = 0.16f)
                        )
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color(0xFFFFFFFF))
                ) {
                    Column {
                        Row(
                            modifier = Modifier
                                .padding(top = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        )
                        {
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                                            fontSize = 14.sp,
                                            color = Color(0xFF707070)
                                        )
                                    ) {
                                        append("Created\n")
                                    }
                                    append("")
                                    withStyle(
                                        style = SpanStyle(
                                            fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
                                            fontSize = 14.sp,
                                        )
                                    ) {
                                        append(campaign.startDate)
                                    }
                                },
                                modifier = Modifier
                                    .padding(start = 15.dp)
                            )
                            Spacer(modifier = Modifier.width(50.dp))
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                                            fontSize = 14.sp,
                                            color = Color(0xFF707070)
                                        )
                                    ) {
                                        append("Start Date\n")
                                    }
                                    append("")
                                    withStyle(
                                        style = SpanStyle(
                                            fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
                                            fontSize = 14.sp,
                                        )
                                    ) {
                                        append(campaign.startDate)
                                    }
                                },
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            // modifier = Modifier.padding(top = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                                            fontSize = 14.sp,
                                            color = Color(0xFF707070)
                                        )
                                    ) {
                                        append("End Date\n")
                                    }
                                    append("")
                                    withStyle(
                                        style = SpanStyle(
                                            fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
                                            fontSize = 14.sp,
                                        )
                                    ) {
                                        append(campaign.endDate)
                                    }
                                },
                                modifier = Modifier
                                    .padding(start = 15.dp)
                            )
                            Spacer(modifier = Modifier.width(50.dp))
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                                            fontSize = 14.sp,
                                            color = Color(0xFF707070)
                                        )
                                    ) {
                                        append("Volunteers\n")
                                    }
                                    append("")
                                    withStyle(
                                        style = SpanStyle(
                                            fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
                                            fontSize = 14.sp,
                                        )
                                    ) {
                                        append(noOfVolunteers)
                                    }
                                },
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun Volunteers(campaignTitle: String) {
        var profileImage by rememberSaveable { mutableStateOf((R.drawable.profile)) }
        var name by rememberSaveable { mutableStateOf("Hira Raheel") }
        val volunteers = remember { mutableStateListOf<Volunteer>() }

        LaunchedEffect(Unit) {
            fetchVolunteersFromFirebase(campaignTitle) { volunteer ->
                volunteers.addAll(volunteer)
            }
        }

        if (volunteers.isEmpty()) {
            // Display a message when no volunteers are present
            Text(
                text = "No volunteers to show",
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

            volunteers.forEach { volunteer ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = rememberImagePainter(data = volunteer.profileImage),
                                contentDescription = "null",
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(shape = CircleShape),
                                contentScale = ContentScale.Crop,
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = volunteer.name,
                                style = TextStyle(
                                    fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                                    fontSize = 16.sp,
                                    color = Color(0xFF000000)
                                )
                            )
                        }
                    }

                }
            }
        }
    }

data class Volunteer(
    val name: String,
    val profileImage: String
)

private suspend fun fetchVolunteersFromFirebase(
    campaignTitle: String,
    onVolunteerFetched: (List<Volunteer>) -> Unit
) {
    val firestore = Firebase.firestore
    val volunteersCollection = firestore.collection("volunteer")

    volunteersCollection
        .whereEqualTo("campaignTitle", campaignTitle)
        .get()
        .addOnSuccessListener { documents ->
            val volunteers = mutableListOf<Volunteer>()
            for (document in documents) {
                val name = document.getString("volunteerName") ?: ""
                val profileImage = document.getString("profileImageUrl") ?: ""
                volunteers.add(Volunteer(name, profileImage))
            }
            onVolunteerFetched(volunteers)
        }
        .addOnFailureListener { exception ->
            // Handle failures
            exception.printStackTrace()
        }
}


private fun fetchVolunteersCountFromFirebase(
    campaignTitle: String,
    onVolunteersCountFetched: (Int) -> Unit
) {
    val firestore = Firebase.firestore
    val volunteersCollection = firestore.collection("volunteer")

    volunteersCollection
        .whereEqualTo("campaignTitle", campaignTitle)
        .get()
        .addOnSuccessListener { documents ->
            val count = documents.size() // Count the number of documents retrieved
            onVolunteersCountFetched(count)
        }
        .addOnFailureListener { exception ->
            // Handle failures
            exception.printStackTrace()
        }
}