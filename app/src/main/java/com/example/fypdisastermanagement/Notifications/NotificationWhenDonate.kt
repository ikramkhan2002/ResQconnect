package com.example.fypdisastermanagement.Notifications

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class NotificationsWhenDonate : ViewModel() {
    private val db = Firebase.firestore
    val donations = mutableStateListOf<DonationItem>()

    init {
        listenForDonations()
    }

    private fun listenForDonations() {
        val collectionRef = db.collection("donations")

        collectionRef.orderBy(FieldPath.documentId(), Query.Direction.ASCENDING)
            .limit(15) // Limit to the last 15 documents
            .addSnapshotListener { snapshot, _ ->
                snapshot?.let {
                    viewModelScope.launch {
                        donations.clear() // Clear existing list before adding new items
                        for (doc in it.documents.reversed()) {
                            val donorName = doc.getString("donationDisplayName") ?: "Unknown"
                            val donationAmount = doc.getString("donationAmount") ?:""
                            val fundraiser = doc.getString("fundraiserName") ?:""
                            val donorUid = doc.getString("currentUid")

                            donorUid?.let { uid ->
                                val userDoc = db.collection("users").document(uid).get().await()
                                val profileImageUrl = userDoc.getString("profileImageUrl") ?: ""

                                val donationItem = DonationItem(
                                    "$donorName", "$donationAmount" , "$fundraiser" ,
                                    profileImageUrl
                                )
                                donations.add(donationItem) // Add at the end of the list
                            }
                        }
                    }
                }
            }
    }
}

data class DonationItem(
    val donorName: String,
    val donationAmount: String,
    val fundraiser: String,
    val profileImageUrl: String
)



@OptIn(ExperimentalCoilApi::class)
@Composable
fun NotificationsWhenDonate(viewModel: NotificationsWhenDonate) {
    val donations = viewModel.donations

    LazyColumn(
        modifier = Modifier
        .fillMaxSize()
        .padding(start = 15.dp, end = 15.dp),
    ) {
        itemsIndexed(donations) { index, donation ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
               verticalAlignment = Alignment.CenterVertically
            ) {
                // Display profile picture or default image if URL is empty
                val painter: Painter = if (donation.profileImageUrl.isNotEmpty()) {
                    rememberImagePainter(donation.profileImageUrl)
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
                // Displaying donation information
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                        ) {
                            append(donation.donorName)
                        }
                        append(" ")
                        withStyle(
                            style = SpanStyle(
                                fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                        ) {
                            append("donated Rs." + donation.donationAmount + " in")
                        }
                        append(" ")
                        withStyle(
                            style = SpanStyle(
                                fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                        ) {
                            append(donation.fundraiser + ".")
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
    }
}