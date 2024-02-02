package com.example.fypdisastermanagement.fundraisers
/*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.fypdisastermanagement.R
import com.example.fypdisastermanagement.destinations.MyFundraisers
import com.example.fypdisastermanagement.destinations.withdrawMoney
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

suspend fun fetchFundraiserDetailsFromFirebase(fundraiserTitle: String): FundraiserInfo? {
    val firestore = Firebase.firestore
    val collection = firestore.collection("Fundraiser")

    try {
        val querySnapshot = collection
            .whereEqualTo("fundraiserTitle", fundraiserTitle) // Query for campaignTitle
            .get()
            .await()

        val document = querySnapshot.documents.firstOrNull()

        document?.let {
            val heading = it.getString("fundraiserTitle") ?: "Default Heading"
           // val description = it.getString("campaignDescription") ?: ""
            val image = it.getString("fundraiserImageUrl") ?: ""
            //val date = it.getString("campaignStartDate") ?: ""
            //val latitude = it.getDouble("latitude") ?: 0.0
            //val longitude = it.getDouble("longitude") ?: 0.0
            //val contactNumber = it.getString("contactNo") ?: ""
            val currentUid = it.getString("currentUid") ?: ""
            val startDate = it.getString("startDate") ?: "" // Assuming you have a field for userId
            val endDate = it.getString("endDate") ?: "" // Assuming you have a field for userId
            val description = it.getString("description") ?: "" // Assuming you have a field for userId
            val linkreport = it.getString("linkReport") ?: "" // Assuming you have a field for userId
            val amount = it.getString("amount") ?: "" // Assuming you have a field for userId

            val userDocument = firestore.collection("users").document(currentUid).get().await()
            val userName = userDocument.getString("firstName") ?: "Unknown User"
            val profileImageUrl = userDocument.getString("profileImageUrl") ?: ""

            return FundraiserInfo(heading, image,currentUid, userName , profileImageUrl,description,linkreport,
                amount,startDate,endDate)
        }
    } catch (e: Exception) {
        // Handle any potential exceptions or errors during fetching
        // Logging, error reporting, or fallback logic can be added here
        e.printStackTrace()
    }

    return null
}

@Composable
fun MyFundraiserView(navController: NavController, fundraiserTitle: String) {
    var headerImage by rememberSaveable { mutableStateOf((R.drawable.myfundraisersheader)) }
    var fundraiserHeading by rememberSaveable { mutableStateOf("Raising 100,000 for the victims of flood in karachi") }
    var amount by rememberSaveable { mutableStateOf("70,000") }
   // var totalAmount by rememberSaveable { mutableStateOf("100,000") }
    var status by rememberSaveable { mutableStateOf("Ongoing") }
    //var startDate by rememberSaveable { mutableStateOf("10 July, 2023") }
    //var endDate by rememberSaveable { mutableStateOf("2 Aug, 2023") }
    var noOfDonations by rememberSaveable { mutableStateOf("20") }
    var totalFundraiserAmount by rememberSaveable { mutableStateOf(0) }
    var totalDonationAmount by rememberSaveable { mutableStateOf(0) }

    var selectedFundraiser: FundraiserInfo? by remember { mutableStateOf(null) }


    LaunchedEffect(Unit) {
        selectedFundraiser?.let { fundraiser ->
            selectedFundraiser = fetchFundraiserDetailsFromFirebase(fundraiserTitle)
            totalDonationAmount = fetchTotalDonationAmount(fundraiserTitle)
        }
    }
    selectedFundraiser?.let { fundraiser ->
        totalFundraiserAmount = fundraiser.amount.toInt()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState()),
        ) {
            Spacer(modifier = Modifier.height(14.dp))
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
                        .clickable { navController.navigate(MyFundraisers.route) },
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
                        data = fundraiser.fundraiserImageUrl,),
                    contentDescription = "null",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = fundraiser.fundraiserTitle,
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.sfprodisplaybold)),
                    fontSize = 20.sp,
                    color = Color(0xFF000000)
                ),
                modifier = Modifier.padding(start = 20.dp, end = 20.dp)
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                            fontSize = 16.sp,
                            color = Color(0xFFD34040)
                        )
                    ) {
                        append("Raised")
                    }
                    append(" ")
                    withStyle(
                        style = SpanStyle(
                            fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                            fontSize = 16.sp,
                        )
                    ) {
                        append("Rs." + amount)
                    }
                },
                modifier = Modifier.padding(start = 20.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Surface(
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp)
            ) {
                LinearProgressIndicator(
                    progress = 0.65f,
                    modifier = Modifier
                        .height(10.dp)
                        .fillMaxWidth()
                        .background(Color.White),
                    color = Color(0xFF34CA96)
                )
            }
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                            fontSize = 16.sp,
                            color = Color(0xFFD34040)
                        )
                    ) {
                        append("Goal")
                    }
                    append(" ")
                    withStyle(
                        style = SpanStyle(
                            fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                            fontSize = 16.sp,
                        )
                    ) {
                        append("Rs." + fundraiser.amount)
                    }
                },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 20.dp)
            )
            Spacer(modifier = Modifier.height(35.dp))
            Surface(
                shadowElevation = 1.dp,
                modifier = Modifier
                    .height(140.dp)
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
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
                            verticalAlignment = Alignment.CenterVertically
                        )
                        {
                            // Calculate the status based on the fundraiser's total amount and total donation amount
                            status = if (totalDonationAmount >= totalFundraiserAmount) {
                                "Completed"
                            } else {
                                "Ongoing"
                            }
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                                            fontSize = 16.sp,
                                            color = Color(0xFF707070)
                                        )
                                    ) {
                                        append("Status\n")
                                    }
                                    append("")
                                    withStyle(
                                        style = SpanStyle(
                                            fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
                                            fontSize = 16.sp,
                                        )
                                    ) {
                                        append(status)
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
                                        append("Donations\n")
                                    }
                                    append("")
                                    withStyle(
                                        style = SpanStyle(
                                            fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
                                            fontSize = 16.sp,
                                        )
                                    ) {
                                        append(noOfDonations)
                                    }
                                },
                                modifier = Modifier
                                    .padding(top = 10.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
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
                                        append("Start Date\n")
                                    }
                                    append("")
                                    withStyle(
                                        style = SpanStyle(
                                            fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
                                            fontSize = 16.sp,
                                        )
                                    ) {
                                        append(fundraiser.startDate)
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
                                            fontSize = 16.sp,
                                            color = Color(0xFF707070)
                                        )
                                    ) {
                                        append("End Date\n")
                                    }
                                    append("")
                                    withStyle(
                                        style = SpanStyle(
                                            fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
                                            fontSize = 16.sp,
                                        )
                                    ) {
                                        append(fundraiser.endDate)
                                    }
                                },
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(35.dp))
            Divider(
                thickness = 1.5.dp,
                color = Color(0xFF707070).copy(alpha = 0.16f),
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(40.dp))
            Button(
                onClick = {
                    navController.navigate("withdraw/${fundraiser.fundraiserTitle}")

                },
                shape = RoundedCornerShape(15),
                colors = ButtonDefaults.buttonColors(Color(0xFFD34040)),
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
                    .clickable { navController.navigate(withdrawMoney.route) }
            ) {
                Text(
                    text = "Withdraw",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.robotomedium)),
                    color = Color(0xFFFFFFFF)
                )
            }
            Spacer(modifier = Modifier.height(35.dp))
        }
    }
}

//@Composable
//@Preview
//fun Myfundprev(){
//    MyFundraiserView()
//}
*/
