package com.example.fypdisastermanagement.Notifications

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.fypdisastermanagement.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
class NotificationWhenCreateFundraiser : ViewModel() {
    private val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()
    val fundraisers = mutableStateListOf<FundraiserItem>()

    init {
        listenForFundraisers()
    }

    private fun listenForFundraisers() {
        val collectionRef = db.collection("Fundraiser")

        collectionRef.addSnapshotListener { snapshot, _ ->
            snapshot?.let {
                viewModelScope.launch {
                    for (doc in it) {
                        val fundraiserUid = doc.getString("currentUid")
                        fundraiserUid?.let { fundraiserUid ->
                            // Fetch user's name and profile image URL from the "users" collection
                            val userDoc = db.collection("users").document(fundraiserUid).get().await()
                            val firstName = userDoc.getString("firstName") ?: "Unknown"
                            val lastName = userDoc.getString("lastName") ?: "Unknown"
                            val userName = "$firstName $lastName"
                            val profileImageUrl = userDoc.getString("profileImageUrl") ?: ""

                            // Fetch the report name directly from the "Fundraiser" collection
                            val reportName = doc.getString("linkReport") ?: "Unknown"

                            val fundraiserItem = FundraiserItem(userName, profileImageUrl, reportName)
                            fundraisers.add(fundraiserItem)
                        }
                    }
                }
            }
        }
    }

}

data class FundraiserItem(
    val userName: String,
    val profileImageUrl: String,
    val reportName: String
)

@OptIn(ExperimentalCoilApi::class)
@Composable
fun NotificationWhenCreateFundraiser(viewModel: NotificationWhenCreateFundraiser) {
    val fundraisers = viewModel.fundraisers

    if (fundraisers.isNotEmpty()) {
        fundraisers.forEach { fundraiser ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Display profile picture or default image if URL is empty
                val painter: Painter = if (fundraiser.profileImageUrl.isNotEmpty()) {
                    rememberImagePainter(fundraiser.profileImageUrl)
                } else {
                    painterResource(id = R.drawable.profileicon1)
                }
                Image(
                    painter = painter,
                    contentDescription = "null",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(50.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                // Display text with the fundraiser information
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                        ) {
                            append("${fundraiser.userName}")
                        }
                        append(" ")
                        withStyle(
                            style = SpanStyle(
                                fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                        ) {
                            append("created a fundraiser for")
                        }
                        append(" ")
                        withStyle(
                            style = SpanStyle(
                                fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                        ){
                            append("${fundraiser.reportName}.")
                        }
                    },
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Divider(
                thickness = 1.5.dp,
                color = Color(0xFF707070).copy(alpha = 0.16f),
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    } else {
        // Display a message when there are no fundraisers
        // androidx.compose.material.Text(text = "Waiting for fundraisers...")
    }
}
