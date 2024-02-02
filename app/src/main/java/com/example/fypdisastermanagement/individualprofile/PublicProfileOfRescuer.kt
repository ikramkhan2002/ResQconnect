package com.example.fypdisastermanagement.individualprofile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.fypdisastermanagement.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

@Composable
fun PublicProfileOfRescuer(navController: NavController,userId:String) {
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
        ProfileStatsForRescuer(userId)
        Spacer(modifier = Modifier.height(35.dp))
        FundraisersCreatedByRescuers(userId)
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun ProfileHeader(userId:String){
    var profileImage by rememberSaveable { mutableStateOf(R.drawable.profileimage) }
    var userState by remember { mutableStateOf<User?>(null) }

    LaunchedEffect(userId) {
        userState = fetchUserInfoFromFirebase(userId)
    }

    // Display user information once fetched
    userState?.let { user ->

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.profileheader),
                contentDescription = "null",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
            Image(
                painter = rememberImagePainter(user.profileImageUrl),
                //painterResource(profileImage),
                contentDescription = "null",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .offset(y = 170.dp)
                    .clip(CircleShape)
                    .size(140.dp)
            )
        }
    }
}

@Composable
fun ProfileNames(userId:String) {
    var username by rememberSaveable { mutableStateOf("Hira Raheel") }
    var role by rememberSaveable { mutableStateOf("Rescuer") }
    var userState by remember { mutableStateOf<User?>(null) }

    // Fetch user information when the composable is first recomposed
    LaunchedEffect(userId) {
        userState = fetchUserInfoFromFirebase(userId)
    }

    // Display user information once fetched
    userState?.let { user ->
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = user.username,
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.sfprodisplaybold)),
                    fontSize = 24.sp,
                    color = Color.Black
                )
            )
            Text(
                text = user.role,
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                    fontSize = 18.sp,
                    color = Color(0XFFD34040)
                )
            )
        }
    }
}
@Composable
fun ProfileStatsForRescuer(userId: String){
    var noOfFundraisers by rememberSaveable { mutableStateOf("2") }
    var noOfDonations by rememberSaveable { mutableStateOf("10") }
    var volunteerd by rememberSaveable { mutableStateOf("14") } //This show the amount of time user has aggreed to volunteer different campaigns
    LaunchedEffect(userId) {
        noOfFundraisers = fetchFundraiserCount(userId).toString()
        volunteerd = fetchVolunteerCount(userId).toString()
        noOfDonations = fetchDonationCount(userId).toString()
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
                        text = noOfFundraisers,
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.sfprodisplaybold)),
                            fontSize = 24.sp,
                            color = Color(0xFF808080),
                        )
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "Fundraisers",
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
fun FundraisersCreatedByRescuers(userId: String) { //This will show all the fundraisers created by specific rescuer
    var fundraiserImage by rememberSaveable { mutableStateOf((R.drawable.floodreport)) }
    var fundraiserHeading by rememberSaveable { mutableStateOf(("Raising 100,000 for the victims of flood in karachi")) }
    var fundraisers by remember { mutableStateOf<List<Fundraiser>>(emptyList()) }

    LaunchedEffect(userId) {
        fundraisers = fetchFundraisers(userId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .padding(start = 25.dp),
            text = "Fundraisers",
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.sfprodisplaybold)),
                fontSize = 18.sp,
                color = Color.Black
            )
        )
        Spacer(modifier = Modifier.height(12.dp))

        if (  fundraisers.isEmpty()) {
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

            // Display the list of fundraisers once fetched
            fundraisers.forEach { fundraiser ->
                Card(
                    shape = RoundedCornerShape(0),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 2.5.dp,
                    ),
                    modifier = Modifier
                        .padding(start = 25.dp, end = 25.dp)
                        .height(100.dp)
                        .background(color = Color.White)
                        .fillMaxWidth()
                        .clickable {
                            // navController.navigate("fundraiserView/${fundraiser.fundraiserTitle}")

                        }
                ) {
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
                                painter = rememberImagePainter(data = fundraiser.fundraiserImage),
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
                Spacer(modifier = Modifier.height(15.dp))
            }
        }
    }
}
//@Preview
//@Composable
//fun RescuerPublicPrev(){
//    PublicProfileOfRescuer()
//}

// Example User data class
data class User(
    val userId: String,
    val profileImageUrl: String,
    val username: String,
    val role: String
)

// Function to fetch user information from Firebase using the user ID
suspend fun fetchUserInfoFromFirebase(userId: String): User? {
    val firestore = Firebase.firestore
    val userDocument = firestore.collection("users").document(userId).get().await()

    return if (userDocument.exists()) {
        val firstName = userDocument.getString("firstName") ?: ""
        val lastName = userDocument.getString("lastName") ?: ""
        User(
            userId = userId,
            profileImageUrl = userDocument.getString("profileImageUrl") ?: "",
            username = "$firstName $lastName",
           // username = userDocument.getString("firstName") ?: "Unknown",
            role = userDocument.getString("role") ?: "Unknown"
        )
    } else {
        null
    }
}
// Function to fetch the number of donations for a specific user from the "donations" collection
suspend fun fetchDonationCount(userId: String): Int {
    val firestore = Firebase.firestore

    try {
        // Query the "donations" collection where "CurrentUid" is equal to the provided userId
        val querySnapshot = firestore.collection("donations")
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
// Function to fetch the number of volunteers for a specific user from the "volunteers" collection
suspend fun fetchVolunteerCount(userId: String): Int {
    val firestore = Firebase.firestore

    try {
        // Query the "volunteers" collection where "CurrentUid" is equal to the provided userId
        val querySnapshot = firestore.collection("volunteer")
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
// Function to fetch the number of fundraisers created by a specific user from the "fundraisers" collection
suspend fun fetchFundraiserCount(userId: String): Int {
    val firestore = Firebase.firestore

    try {
        // Query the "fundraisers" collection where "CreatorUid" is equal to the provided userId
        val querySnapshot = firestore.collection("Fundraiser")
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

// Function to fetch all fundraisers created by a specific user from the "fundraisers" collection
suspend fun fetchFundraisers(userId: String): List<Fundraiser> {
    val firestore = Firebase.firestore

    try {
        // Query the "fundraisers" collection where "CreatorUid" is equal to the provided userId
        val querySnapshot = firestore.collection("Fundraiser")
            .whereEqualTo("currentUid", userId)
            .get()
            .await()

        // Map the documents to Fundraiser objects
        return querySnapshot.documents.mapNotNull { document ->
            Fundraiser(
                fundraiserTitle = document.getString("fundraiserTitle") ?: "",
                fundraiserImage = document.getString("fundraiserImageUrl") ?: ""
            )
        }
    } catch (e: Exception) {
        // Handle exceptions, such as network errors or Firestore errors
        return emptyList()
    }
}

// Data class to represent a Fundraiser
data class Fundraiser(
    val fundraiserTitle: String,
    val fundraiserImage: String
)
