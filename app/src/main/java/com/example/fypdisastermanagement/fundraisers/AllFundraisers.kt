package com.example.fypdisastermanagement.fundraisers

import android.app.DownloadManager
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.fypdisastermanagement.R
import com.example.fypdisastermanagement.campaignview.fetchTotalCampaignCount
import com.example.fypdisastermanagement.destinations.RescuerHome
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.Query



// Function to fetch all fundraiser titles and image URLs from Firestore
suspend fun fetchAllFundraisersInfo(): List<FundraiserInfo> {
    val firestore = Firebase.firestore
    val collection = firestore.collection("Fundraiser")

    val querySnapshot = collection.get().await()

    val fundraisersList = mutableListOf<FundraiserInfo>()

    for (document in querySnapshot.documents) {
        val title = document.getString("fundraiserTitle") ?: "Default Title"
        val imageUrl = document.getString("fundraiserImageUrl") ?: ""
        val userId = document.getString("userId") ?: "" // Assuming you have a field for userId
        val startDate = document.getString("startDate") ?: "" // Assuming you have a field for userId
        val endDate = document.getString("endDate") ?: "" // Assuming you have a field for userId
        val description = document.getString("description") ?: "" // Assuming you have a field for userId
        val linkreport = document.getString("linkReport") ?: "" // Assuming you have a field for userId
        val amount = document.getString("amount") ?: "" // Assuming you have a field for userId
        val currentUid = document.getString("currentUid") ?: ""


        val userDocument = firestore.collection("users").document(currentUid).get().await()
        val firstName = userDocument.getString("firstName") ?: "Unknown"
        val lastName = userDocument.getString("lastName") ?: "User"

        // Combine first name and last name to form the user's full name
        val userName = "$firstName $lastName"
        val profileImageUrl = userDocument.getString("profileImageUrl") ?: ""
        val fundraiserInfo = FundraiserInfo(title, imageUrl,userId, userName, profileImageUrl,description,linkreport,
            amount,startDate,endDate)
        fundraisersList.add(fundraiserInfo)
    }

    return fundraisersList
}
// Function to fetch total count of fundraisers from Firestore
suspend fun fetchTotalFundraiserCount(): Int {
    val firestore = Firebase.firestore
    val collection = firestore.collection("Fundraiser")

    val querySnapshot = collection.get().await()

    return querySnapshot.size()
}




//// Function to fetch the top-most fundraiser title and image URL from Firestore
//suspend fun fetchTopMostFundraiserInfo(): FundraiserInfo {
//    val firestore = Firebase.firestore
//    val collection = firestore.collection("Fundraiser")
//
//    // Query the collection to get the top-most document
//    val query = collection.limit(1)
//
//    // Execute the query and get the result
//    val querySnapshot = query.get().await()
//
//    if (!querySnapshot.isEmpty) {
//        val document = querySnapshot.documents[0]
//        val title = document.getString("fundraiserTitle") ?: "Default Title"
//        val imageUrl = document.getString("fundraiserImageUrl") ?: ""
//
//        return FundraiserInfo(title, imageUrl)
//    } else {
//        return FundraiserInfo("Default Title", "")
//    }
//}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Fundraisers(navController: NavController) {
    var search by rememberSaveable { mutableStateOf("") }
   // var profileImage by rememberSaveable { mutableStateOf((R.drawable.profile)) }
 //   var name by rememberSaveable { mutableStateOf(("Hira Raheel")) }
    var fundraiserHeading by rememberSaveable { mutableStateOf("Loading...") }
    var fundraisersImageURL by rememberSaveable { mutableStateOf("") }
    var results by rememberSaveable { mutableStateOf("0") }
    var fundraisersList by rememberSaveable { mutableStateOf(emptyList<FundraiserInfo>()) }
    var fundraisersCount by rememberSaveable { mutableStateOf(0) }
    LaunchedEffect(Unit) {
        fundraisersList = fetchAllFundraisersInfo()
        val totalCount = fetchTotalFundraiserCount()
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
                text = "Fundraisers",
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

        if (  fundraisersList.isEmpty()) {
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
                        .clickable {
                            navController.navigate("fundraiserView/${fundraiser.fundraiserTitle}")
                        }

                        .padding(start = 20.dp, end = 20.dp)
                        .height(275.dp)
                        .background(Color.White)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(CornerSize(0.dp)),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 1.dp
                    ),
                )
                {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White),
                        ) {
                            // Use the fetched image URL to load the image
                            if (fundraiser.fundraiserImageUrl.isNotEmpty()) {
                                Image(
                                    painter = rememberImagePainter(data = fundraiser.fundraiserImageUrl),
                                    contentDescription = "null",
                                    modifier = Modifier
                                        .height(130.dp)
                                        .fillMaxWidth()
                                        .background(Color.White),
                                    contentScale = ContentScale.Crop,
                                )
                            } else {
                                // Handle the case where the image URL is empty (e.g., show a placeholder image)
                            }
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
                                        painter = rememberImagePainter(data = fundraiser.profileImageUrl),
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
                                        .padding(start = 260.dp)
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
                }
                Spacer(modifier = Modifier.height(15.dp))
            }
        }
    }
}

@Preview()
@Composable
fun fundraisersPrev(){
    Fundraisers(navController= rememberNavController())
}