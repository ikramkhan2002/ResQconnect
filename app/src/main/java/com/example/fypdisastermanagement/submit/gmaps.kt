package com.example.fypdisastermanagement.submit

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun GoogleMapWithLongClickMarker(
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState = rememberCameraPositionState(),
    markerState: MutableState<MarkerState?> = remember { mutableStateOf(null) }
) {
    // Set the initial camera position to a location in Pakistan
    val pakistan = LatLng(30.3753, 69.3451)
    val cameraPositionStateInternal = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(pakistan, 10f)
    }
    val context = LocalContext.current // Get the current context


    // Use GoogleMap composable as before, but add an onMapLongClick callback
    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionStateInternal,
        onMapLongClick = { longClickLatLng ->
            // Create a marker at the long-clicked location
            val marker = MarkerState(
                position = longClickLatLng,
                // You can customize marker options here
                // For example: title, icon, etc.
            )
            // Update the marker state
            markerState.value = marker
        }
    ) {
        // Add any additional map content here

        // Display the marker using the Marker composable
        markerState.value?.let { marker ->
            Marker(
                state = marker,
                // You can customize the marker appearance here
            )
        }
    }

    val db = Firebase.firestore

    // Add a "Save" button below the map
    Button(
        onClick = {
            // Handle the save action here
            val selectedMarker = markerState.value
            if (selectedMarker != null) {
                val latitude = selectedMarker.position.latitude
                val longitude = selectedMarker.position.longitude

                // Create a data object to store in Firestore
                val markerData = hashMapOf(
                    "latitude" to latitude,
                    "longitude" to longitude

                )

                // Add the data to Firestore
                db.collection("markers")
                    .add(markerData)
                    .addOnSuccessListener { documentReference ->
                        // Handle the successful save, e.g., display a success toast
                        Toast.makeText(context, "Location saved to Firebase", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        // Handle the failure to save, e.g., display an error toast
                        Toast.makeText(context, "Error saving location to Firebase", Toast.LENGTH_SHORT).show()
                    }
            }
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Save")
    }
}

@Composable
fun GoogleMaps(){
    val markerLatitude = remember { mutableStateOf(0.0) }
    val markerLongitude = remember { mutableStateOf(0.0) }
    val pakistan = LatLng(30.3753, 69.3451)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(pakistan, 10f)
    }

    var isMapVisible by remember { mutableStateOf(true) }
    val markerState = remember { mutableStateOf<MarkerState?>(null) }

    Box(
        modifier = Modifier
            .height(270.dp)
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
            .background(color = Color(0xFFFFFFFF))
            //.border(BorderStroke(width = 0.5.dp, color = Color.Black))
            .clickable {
                isMapVisible = true
            }
    ) {
        if (isMapVisible) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                onMapLongClick = { longClickLatLng ->
                    // Create a marker at the long-clicked location
                    val marker = MarkerState(
                        position = longClickLatLng
                    )
                    // Update the marker state
                    markerState.value = marker

                    // Update the latitude and longitude values
                    markerLatitude.value = longClickLatLng.latitude
                    markerLongitude.value = longClickLatLng.longitude
                }

            ) {
                // Add any additional map content here

                // Display the marker using the Marker composable
                markerState.value?.let { marker ->
                    Marker(
                        state = marker,
                        // You can customize the marker appearance here
                    )
                }
            }
        } else {
            Text(
                text = "Add your Location Here",
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}


@Composable
@Preview
fun gmapsprevv(){
  GoogleMapWithLongClickMarker()
}