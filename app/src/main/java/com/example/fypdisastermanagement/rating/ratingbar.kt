package com.example.fypdisastermanagement.rating



import android.view.MotionEvent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.remember
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.example.fypdisastermanagement.R
import com.example.fypdisastermanagement.Reports.getCurrentUserId
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await


@ExperimentalComposeUiApi
@Composable
fun RatingBar(onRatingSelected: (Int) -> Unit){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "RATE THIS",
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                fontSize = 18.sp,
                color = Color(0xFF000000)
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Ratings(rating = 0, onRatingSelected = onRatingSelected)
    }
}

@ExperimentalComposeUiApi
@Composable
fun Ratings(
    modifier: Modifier = Modifier,
    rating: Int,
    onRatingSelected: (Int) -> Unit
) {
    var ratingState by remember {
        mutableStateOf(rating)
    }

    var selected by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 1..5) {
            Icon(
                // imageVector = Icons.Default.Star,
                painter =
                if (i <= ratingState) painterResource(id = R.drawable.star) else painterResource(id = R.drawable.star_outline),
                contentDescription = "star",
                modifier = modifier
                    .size(50.dp)
                    .pointerInteropFilter {
                        when (it.action) {
                            MotionEvent.ACTION_DOWN -> {
                                selected = true
                                ratingState = if (i == ratingState) 0 else i
                                onRatingSelected(ratingState) // Notify the parent about the selected rating
                            }

                            MotionEvent.ACTION_UP -> {
                                selected = false
                            }
                        }
                        true
                    },
                tint = Color(0xFFD34040)
            )
        }
    }
}

suspend fun submitRatingToFirebase(rating: Int, reportTitle: String) {
    val firestore = Firebase.firestore
    val collection = firestore.collection("Ratings")

    val currentUserId = getCurrentUserId()

    if (currentUserId != null) {
        // Check if the user has already rated for the same report
        val existingRatingQuery = collection
            .whereEqualTo("reportTitle", reportTitle)
            .whereEqualTo("userId", currentUserId)
            .limit(1)

        val existingRatingSnapshot = existingRatingQuery.get().await()

        if (!existingRatingSnapshot.isEmpty) {
            // If the user has already rated, update the existing rating document
            val existingRatingDocument = existingRatingSnapshot.documents[0]
            try {
                existingRatingDocument.reference.update("rating", rating).await()
            } catch (e: Exception) {
                // Handle any potential exceptions or errors during update
                e.printStackTrace()
            }
        } else {
            // If the user hasn't rated for this report yet, add a new rating document
            val ratingData = hashMapOf(
                "reportTitle" to reportTitle,
                "userId" to currentUserId,
                "rating" to rating
            )

            try {
                collection.add(ratingData).await()
            } catch (e: Exception) {
                // Handle any potential exceptions or errors during submission
                e.printStackTrace()
            }
        }
    }
}



