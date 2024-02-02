package com.example.safetytips

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.fypdisastermanagement.Campaigns.CampaignInfo
import com.example.fypdisastermanagement.R
import com.example.fypdisastermanagement.campaignview.fetchAllCampaignsInfo
import com.example.fypdisastermanagement.destinations.AllReports
import com.example.fypdisastermanagement.destinations.Campaigns
import com.example.fypdisastermanagement.destinations.Fundraisers
import com.example.fypdisastermanagement.destinations.LoginScreen
import com.example.fypdisastermanagement.destinations.SafetyTips
import com.example.fypdisastermanagement.destinations.SignUp
import com.example.fypdisastermanagement.destinations.SubmitFundraiser
import com.example.fypdisastermanagement.fundraisers.FundraiserInfo
import com.example.fypdisastermanagement.fundraisers.fetchAllFundraisersInfo
import com.example.fypdisastermanagement.fundraisers.fetchTotalDonationAmount
import com.example.fypdisastermanagement.homescreen.fetchLimitedCampaignsInfo
import com.example.fypdisastermanagement.homescreen.fetchLimitedFundraisersInfo
import com.example.fypdisastermanagement.homescreen.fetchLimitedReportsFromFirestore
import com.example.fypdisastermanagement.reportview.Report
import com.example.fypdisastermanagement.reportview.fetchReportsFromFirestore
import com.example.fypdisastermanagement.submit.Reports
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(){
    var search by rememberSaveable { mutableStateOf("") }
    OutlinedTextField(
        value = search,
        onValueChange = { search = it },
        leadingIcon = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color(0xFF808080).copy(alpha = 0.7f),
                    modifier = Modifier
                        .size(20.dp)
                        .padding(top = 2.5.dp)
                )
            }
        },
        placeholder = {
            Text(
                text = "Search Here",
                fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                fontSize = 14.sp,
                color = Color(0xFF808080).copy(alpha = 0.5f)
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(15),
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp)
            .background(
                color = Color(0xFFE2E2E9).copy(alpha = 0.60f),
                shape = RoundedCornerShape(15)
            ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = Color(0xFFD34040),
            focusedTextColor = Color.Black,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
        ),
    )
}

@Composable
fun AddNewFundarsier(navController: NavController) {
    Card(
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp)
            .height(110.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(CornerSize(15.dp)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.5.dp
        )
    ) {
        Box(
            modifier = Modifier
                .background(Color(0XFFD34040))
                .padding(start = 15.dp, end = 15.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp)
            ) {
                Text(
                    text = "Start New\nFundraising",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.sfprodisplaybold)),
                        fontSize = 17.sp,
                        color = Color.White,
                        letterSpacing = 1. sp,
                        lineHeight = 25.sp,
                    ),
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { /* TODO */ },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(Color.White),
                    modifier = Modifier.width(120.dp).height(50.dp)
                ) {
                    Text(
                        text = "Start now",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.poppinssemibold)),
                            fontSize = 14.sp,
                            color = Color(0xFFD34040),
                        ),
                        modifier = Modifier.clickable { navController.navigate(SubmitFundraiser.route) }
                    )
                }
            }
        }
    }
}

@Composable
fun ClickableTextViewAll(onClick: () -> Unit, navController: NavController) {
   var clickState by remember { mutableStateOf(false) }

    val textSize by animateFloatAsState(
        targetValue = if (clickState) 14.sp.value else 12.sp.value
    )

    Text(
        text = "View all>",
        style = TextStyle(
            color = Color(0XFFD34040),
            fontSize = textSize.sp,
            fontFamily = FontFamily(Font(R.font.poppinssemibold))
        ),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(align = Alignment.TopEnd)
            .padding(end = 15.dp)
            .clickable {
                clickState = !clickState
                onClick()
            }
    )
}

@Composable
fun LatestReports(navController: NavController) {
    var reportHeading by rememberSaveable { mutableStateOf("Heavy rain cause flood in Karachi") }
    var reports by rememberSaveable { mutableStateOf(emptyList<Report>()) }

    LaunchedEffect(Unit) {
        reports = fetchLimitedReportsFromFirestore()
    }

    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 15.dp)

    ) {
        reports.forEach { report ->

            Card(   //Report no 1 Card
                modifier = Modifier
                    .width(205.dp)
                    .height(160.dp)
                    .clickable {
                        navController.navigate("reportView/${report.reportTitle}")


                    },
                shape = RoundedCornerShape(CornerSize(10.dp)),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 1.dp
                )
            )
            {
                Box(
                    modifier = Modifier
                        .width(205.dp)
                        .height(160.dp)
                ) {
                    Image(
                        painter = rememberImagePainter(data = report.imageUrl),
                        contentDescription = "null",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.fillMaxSize()
                    )
                    Text(
                        text = if (report.reportTitle.length > 35) "${report.reportTitle.take(35)}.." else report.reportTitle,
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.poppinsbold)),
                            fontSize = 14.sp,
                            color = Color.White
                        ),
                        modifier = Modifier
                            .padding(top = 95.dp, start = 6.dp, end = 4.dp)
                            .fillMaxWidth()
                    )
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}
@Composable
fun CampaignsSection(navController: NavController) {
   // var campaignHeading by rememberSaveable { mutableStateOf("Blood Donation Camp") }
   // var campaignDes by rememberSaveable { mutableStateOf(("Al-Mustafa Welfare society provides free treatment including")) }
   // var campaignImage by rememberSaveable { mutableStateOf((R.drawable.campaignheader)) }
   // var date by rememberSaveable { mutableStateOf(("10 July, 2023")) }
    var campaignsList by rememberSaveable { mutableStateOf(emptyList<CampaignInfo>()) }

    LaunchedEffect(Unit) {
        campaignsList = fetchLimitedCampaignsInfo()
    }
    Column(modifier = Modifier.fillMaxSize()){
        campaignsList.forEach { campaign ->

        Card( //Campaign No 1
            shape = RoundedCornerShape(0),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.5.dp,
            ),
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp)
                .height(100.dp)
                .fillMaxWidth()
                .clickable {
                    navController.navigate("campaignView/${campaign.campaignHeading}")

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
                            text = if (campaign.campaignHeading.length > 20) "${campaign.campaignHeading.take(20)}.." else campaign.campaignHeading,
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                                fontSize = 16.sp,
                                color = Color(0xFF000000)
                            ),
                            modifier = Modifier
                                .padding(top = 8.dp, end = 2.dp)
                        )
                        Text(
                            text = if (campaign.campaignDes.length > 45) "${campaign.campaignDes.take(45)}.." else campaign.campaignDes,
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
                                fontSize = 12.sp,
                                color = Color(0xFF707070)
                            ),
                            modifier = Modifier
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

@Composable
fun FundraisersSection(navController: NavController) {
    //var fundraiserHeading by rememberSaveable { mutableStateOf("Help me raise Rs.50,000 to build the house for the earthquake victim") }
    //var fundraisersImage by rememberSaveable { mutableStateOf((R.drawable.myfundraisersheader)) }
    //var profileImage by rememberSaveable { mutableStateOf((R.drawable.profile)) }
    //var name by rememberSaveable { mutableStateOf(("Hira Raheel")) }
    var fundraisersList by rememberSaveable { mutableStateOf(emptyList<FundraiserInfo>()) }
    LaunchedEffect(Unit) {
        fundraisersList = fetchLimitedFundraisersInfo()
    }
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 15.dp)
       ) {
        fundraisersList.forEach { fundraiser ->
            var totalDonationAmount by rememberSaveable { mutableStateOf(0) }
            var totalFundraiserAmount by rememberSaveable { mutableStateOf(0) }
            LaunchedEffect(fundraiser.fundraiserTitle) {
                fetchTotalDonationAmount(fundraiser.fundraiserTitle)
                totalDonationAmount = fetchTotalDonationAmount(fundraiser.fundraiserTitle)
                totalFundraiserAmount = fundraiser.amount.toInt()

            }

            Card(
                modifier = Modifier
                    .width(250.dp)
                    .clickable {
                        navController.navigate("fundraiserView/${fundraiser.fundraiserTitle}")
                    }
                    .height(275.dp)
                    .background(Color.White),
                shape = RoundedCornerShape(CornerSize(0.dp)),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 1.dp
                ),
            )
            {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),

                    ) {
                    Image(
                        painter = rememberImagePainter(data = fundraiser.fundraiserImageUrl),
                        contentDescription = "null",
                        modifier = Modifier
                            .height(130.dp)
                            .fillMaxWidth()
                            .background(Color.White),
                        contentScale = ContentScale.Crop,
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp, top = 140.dp, end = 12.dp),
                    )
                    {
                        Text(
                            text = if (fundraiser.fundraiserTitle.length > 70) "${
                                fundraiser.fundraiserTitle.take(
                                    70
                                )
                            }.." else fundraiser.fundraiserTitle,
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                                fontSize = 16.sp,
                                color = Color(0xFF000000)
                            ),
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = rememberImagePainter(fundraiser.profileImageUrl),
                                contentDescription = "null",
                                modifier = Modifier
                                    .size(35.dp)
                                    .clip(shape = CircleShape),
                                contentScale = ContentScale.Crop,
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = fundraiser.userName,
                                style = TextStyle(
                                    fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                                    fontSize = 14.sp,
                                    color = Color(0xFF808080)
                                )
                            )
                        }
                        // Calculate progress based on the total donation amount and total fundraiser amount
                        val progress = if (totalFundraiserAmount > 0) {
                            (totalDonationAmount.toFloat() / totalFundraiserAmount.toFloat()).coerceIn(
                                0f,
                                1f
                            )
                        } else {
                            0f
                        }
                        val progressPercentage = (progress * 100).toInt()
                        Text(
                            text = "$progressPercentage%",
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                                fontSize = 14.sp,
                                color = Color(0xFF808080)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 200.dp)
                        )
                        Surface(
                            shape = RoundedCornerShape(4.dp),

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
                    }
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}

data class UsersData(
    val firstName: String,
    val profileImageUrl: String,
)

suspend fun fetchUserData(): UsersData {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val defaultData = UsersData(
        "Default Username",
        "Default Email",
    )

    currentUser?.let { user ->
        val userDocument = FirebaseFirestore.getInstance().collection("users").document(user.uid)

        try {
            val document = userDocument.get().await()
            if (document.exists()) {
                val firstName = document.getString("firstName") ?: ""
                val profileImageUrl = document.getString("profileImageUrl") ?: ""

                return UsersData(firstName,profileImageUrl)
            }
        } catch (e: Exception) {
            // Handle exceptions
        }
    }

    return defaultData
}











