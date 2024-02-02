package com.example.fypdisastermanagement.Campaigns

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
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.fypdisastermanagement.R
import com.example.fypdisastermanagement.destinations.Campaigns
import com.example.fypdisastermanagement.destinations.ForgotPassword
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.tasks.await
import android.content.Context
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext

// Function to add data to the Firebase collection "volunteer"
fun addToVolunteerCollection(currentUid: String, campaignTitle: String, context: Context) {
    val firestore = Firebase.firestore
    val volunteerCollection = firestore.collection("volunteer")

    // Query to check if the user has already volunteered for the same campaign
    val query = volunteerCollection
        .whereEqualTo("currentUid", currentUid)
        .whereEqualTo("campaignTitle", campaignTitle)

    query.get()
        .addOnSuccessListener { querySnapshot ->
            if (querySnapshot.isEmpty) {
                // User has not volunteered for the same campaign, proceed to add data
                val userCollection = firestore.collection("users").document(currentUid)

                userCollection.get()
                    .addOnSuccessListener { document ->
                        if (document.exists()) {
                            val firstName = document.getString("firstName") ?: ""
                            val lastName = document.getString("lastName") ?: ""
                            val userName = "$firstName $lastName"
                            val profileImageUrl = document.getString("profileImageUrl") ?: ""

                            // Create data map with user details
                            val data = hashMapOf(
                                "currentUid" to currentUid,
                                "campaignTitle" to campaignTitle,
                                "volunteerName" to userName,
                                "profileImageUrl" to profileImageUrl
                                // Add any other relevant data you want to store
                            )

                            // Add data to the "volunteer" collection
                            volunteerCollection.add(data)
                                .addOnSuccessListener {
                                    // Handle success if needed
                                }
                                .addOnFailureListener { e ->
                                    // Handle failures
                                    e.printStackTrace()
                                }
                        } else {
                            // Document doesn't exist
                        }
                    }
                    .addOnFailureListener { e ->
                        // Handle failures
                        e.printStackTrace()
                    }
            } else {
                // User has already volunteered for the same campaign
                Toast.makeText(context, "You have already volunteered for this campaign", Toast.LENGTH_SHORT).show()
            }
        }
        .addOnFailureListener { e ->
            // Handle failures
            e.printStackTrace()
        }
}

// Function to get the current user's UID from Firebase Authentication
fun getCurrentUserUid(): String? {
    val currentUser = FirebaseAuth.getInstance().currentUser
    return currentUser?.uid
}

@Composable
fun CampaignView(navController: NavController, campaignTitle: String) {
    var headerImage by rememberSaveable { mutableStateOf((R.drawable.campaignheader)) }
    var name by rememberSaveable { mutableStateOf(("Hira Raheel")) }
    var profileImage by rememberSaveable { mutableStateOf((R.drawable.profile)) }
    var campaignHeading by rememberSaveable { mutableStateOf("Blood Donation Camp") }
    var date by rememberSaveable { mutableStateOf(("10 July, 2023")) }
    var contactNo by rememberSaveable { mutableStateOf(("0333-1905200")) }
    var description by rememberSaveable {
        mutableStateOf(
            ("As we all are aware that Blood is the key to save lives for critically ill patients. Many thalassemia patients depend on blood transfusions (as often as twice monthly) to survive and thrive. Al-Mustafa Welfare Society provides free treatment including blood transfusion services to the children affected with thalassemia. Our blood bank keeps a reservoir of blood, as this blood is the insurance of their lives. We have yet provided 10,171 times blood transfusions since the department was established.\n" +
                    "For this purpose, we drive blood donation campaigns. Our team of volunteers arranges blood donation camps in different universities, colleges and community centers etc. We encourage people especially youngsters and students to donate blood by explaining to them several health benefits of blood donation. Our team always records the data of the Blood Donor and Al-Mustafa’s blood bank department mails them the report of all the necessary tests performed for the screening of blood.")
        )
    }
    val currentUid = getCurrentUserUid()
    var selectedCampaign: CampaignInfo? by remember { mutableStateOf(null) }


    LaunchedEffect(Unit) {
        selectedCampaign = fetchCampaignDetailsFromFirebase(campaignTitle)
    }

    selectedCampaign?.let { campaign ->


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
                        .clickable { navController.navigate(Campaigns.route) },
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
                        data = campaign.campaignImage,),
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
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = rememberImagePainter(campaign.profileImageUrl),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(shape = CircleShape)
                        .clickable {
                            navController.navigate("socialWorkerPublic/${campaign.userId}")
                                   },
                    contentScale = ContentScale.Crop,
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "By ${campaign.userName}",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                        fontSize = 16.sp,
                        color = Color(0xFF808080)
                    ),
                    modifier = Modifier.clickable {
                        navController.navigate("socialWorkerPublic/${campaign.userId}")
                    },
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = campaign.campaignHeading,
                modifier = Modifier.padding(start = 20.dp),
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.sfprodisplaybold)),
                    fontSize = 22.sp,
                    color = Color(0xFF000000)
                )
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Location",
                modifier = Modifier.padding(start = 20.dp),
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                    fontSize = 16.sp,
                    color = Color(0xFF808080)
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .height(270.dp)
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
                    .background(color = Color(0xFFFFFFFF))
                  //  .border(BorderStroke(width = 0.5.dp, color = Color.Black))
            ) {
                //map

                val location = LatLng(campaign.latitude, campaign.longitude)
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = rememberCameraPositionState {
                        position = CameraPosition.fromLatLngZoom(location, 10f)
                    }
                ) {
                    Marker(
                        state = MarkerState(position = location),
                        title = "Campaign Location",
                    )
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = campaign.campaignDes,
                modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
                    fontSize = 15.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Justify,
                    lineHeight = 22.sp,
                )
            )
            Spacer(modifier = Modifier.height(35.dp))
            Divider(
                thickness = 1.5.dp,
                color = Color(0xFF707070).copy(alpha = 0.16f),
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.padding(start = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.calender),
                    contentDescription = "Calender",
                    tint = Color(0XFFD34040),
                    modifier = Modifier
                        .size(40.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = campaign.startDate,
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
                        fontSize = 16.sp,
                        color = Color.Black
                    ),
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.padding(start = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "phone",
                    tint = Color(0XFFD34040),
                    modifier = Modifier
                        .size(40.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = campaign.contactNumber,
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
                        fontSize = 16.sp,
                        color = Color.Black
                    ),
                )
            }
            Spacer(modifier = Modifier.height(35.dp))
            Divider(
                thickness = 1.5.dp,
                color = Color(0xFF707070).copy(alpha = 0.16f),
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(60.dp))
            val context = LocalContext.current
            Button(
                onClick = {
                    currentUid?.let { uid ->
                        addToVolunteerCollection(uid, campaign.campaignHeading,context)
                    }
                    Toast.makeText(
                        context,
                        "Confirmed Voluteered",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                shape = RoundedCornerShape(15),
                colors = ButtonDefaults.buttonColors(Color(0xFFD34040)),
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp)
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(
                    text = "Volunteer",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.robotomedium)),
                    color = Color(0xFFFFFFFF)
                )
            }

            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}

// Function to fetch campaign details from Firebase using campaignTitle
suspend fun fetchCampaignDetailsFromFirebase(campaignTitle: String): CampaignInfo? {
    val firestore = Firebase.firestore
    val collection = firestore.collection("Campaigns")

    try {
        val querySnapshot = collection
            .whereEqualTo("campaignTitle", campaignTitle) // Query for campaignTitle
            .get()
            .await()

        val document = querySnapshot.documents.firstOrNull()

        document?.let {
            val heading = it.getString("campaignTitle") ?: "Default Heading"
            val description = it.getString("campaignDescription") ?: ""
            val image = it.getString("imageUrl") ?: ""
            val startDate = it.getString("campaignStartDate") ?: ""
            val endDate = it.getString("campaignEndDate") ?: ""
            val latitude = it.getDouble("latitude") ?: 0.0
            val longitude = it.getDouble("longitude") ?: 0.0
            val contactNumber = it.getString("contactNo") ?: ""
            val currentUid = it.getString("currentUid") ?: ""

            // Fetch user name using UID
            val userDocument = firestore.collection("users").document(currentUid).get().await()
            val firstName = userDocument.getString("firstName") ?: ""
            val lastName = userDocument.getString("lastName") ?: ""
            val userName = "$firstName $lastName"
            val profileImageUrl = userDocument.getString("profileImageUrl") ?: ""
            return CampaignInfo(
                heading, description, image, startDate,endDate, latitude, longitude, contactNumber,
                currentUid, userName , profileImageUrl
            )
        }
    } catch (e: Exception) {
        // Handle exceptions
        e.printStackTrace()
    }

    return null
}
//@Preview
//@Composable
//fun CampaignViewPrev(){
//    CampaignView()
//}