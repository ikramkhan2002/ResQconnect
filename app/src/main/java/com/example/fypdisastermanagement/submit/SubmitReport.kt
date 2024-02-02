package com.example.fypdisastermanagement.submit

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.*

import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.fypdisastermanagement.R
import com.example.fypdisastermanagement.destinations.MyReports
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import java.io.ByteArrayOutputStream
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubmitReport(context : Context, navController: NavController) {
    val reportTitle = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    val markerLatitude = remember { mutableStateOf(0.0) }
    val markerLongitude = remember { mutableStateOf(0.0) }

    var selectedImageUris by remember {
        mutableStateOf<List<Uri>>(emptyList())
    }
    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { selectedImageUris = it }
    )

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
        }
    DisposableEffect(Unit) {
        onDispose {
            // Reset the imageUri when the composable is disposed (i.e., leaving the page)
            reportTitle.value = ""
            description.value = ""
            imageUri = null
            bitmap.value = null
            markerLatitude.value = 0.0
            markerLongitude.value = 0.0
            selectedImageUris = emptyList()
        }
    }



    val storage = FirebaseStorage.getInstance()
    val storageRef: StorageReference = storage.reference

    // Obtain the currently logged-in user's UID
    val firebaseAuth = FirebaseAuth.getInstance()
    val currentUser = firebaseAuth.currentUser
    val currentUid = currentUser?.uid ?: ""

    fun uploadImageToStorage() {
        if (imageUri != null) {
            val imageRef: StorageReference =
                storageRef.child("report_images/${System.currentTimeMillis()}_${imageUri!!.lastPathSegment}")

            val stream = ByteArrayOutputStream()
            bitmap.value?.compress(Bitmap.CompressFormat.JPEG, 90, stream)
            val data = stream.toByteArray()

            imageRef.putBytes(data)
                .addOnSuccessListener { uploadTask ->
                    // Get the download URL of the uploaded image
                    uploadTask.storage.downloadUrl.addOnSuccessListener { imageUrl ->
                        // Once you obtain the imageUrl, call `addReportsToFirebase` with this URL
                        val url = imageUrl.toString()
                        addReportsToFirebase(
                            reportTitle.value,
                            description.value,
                            url, // Use url here
                            context,
                            markerLatitude.value,
                            markerLongitude.value,
                            currentUid,
                        )
                        reportTitle.value = ""
                        description.value = ""
                        imageUri = null
                        bitmap.value = null
                        markerLatitude.value = 0.0
                        markerLongitude.value = 0.0
                        selectedImageUris = emptyList()
                    }.addOnFailureListener { e ->
                        // Handle failure to get imageUrl
                        Toast.makeText(context, "Failed to get image URL: $e", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    // Handle the failure to upload the image.
                    Toast.makeText(context, "Failed to upload image: $e", Toast.LENGTH_SHORT).show()
                }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
    )
    {
        Spacer(modifier = Modifier.height(22.dp))
        Text(
            text = "Report an Incident",
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                fontSize = 24.sp,
                color = Color(0xFF000000)

            ),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)

        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Your contribution matters!",
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
                fontSize = 16.sp,
                color = Color(0xFF808080)

            ),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)

        )
        Spacer(modifier = Modifier.height(30.dp))
        Row {
            Text(
                text = "Title of your report",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.poppinsmedium)),
                    fontSize = 14.sp,
                    color = Color(0xFF000000),
                ),
                modifier = Modifier
                    .padding(start = 25.dp)

            )
            Text(
                text = "*",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.poppinsmedium)),
                    fontSize = 12.sp,
                    color = Color(0xFFD34040),
                ),
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = reportTitle.value,
            onValueChange = { reportTitle.value = it },
            placeholder = {
                Text(
                    text = "Enter the title of your report",
                    fontFamily = FontFamily(Font(R.font.poppinsregular)),
                    fontSize = 12.sp,
                    color = Color(0xFF808080)
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(0),
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .background(
                    color = Color(0xFFFFFFFF)
                )
                .padding(start = 20.dp, end = 20.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = Color(0xFFD34040),
                focusedBorderColor = Color(0xFFD34040),
                unfocusedBorderColor = Color(0xFF808080).copy(alpha = 0.45f),
            ),
        )
        Spacer(modifier = Modifier.height(15.dp))
        Row {
            Text(
                text = "Gps Location",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.poppinsmedium)),
                    fontSize = 14.sp,
                    color = Color(0xFF000000),
                ),
                modifier = Modifier
                    .padding(start = 25.dp)

            )
            Text(
                text = "*",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.poppinsmedium)),
                    fontSize = 12.sp,
                    color = Color(0xFFD34040),
                ),
            )
        }

        //MAPS
        Spacer(modifier = Modifier.height(10.dp))
        val pakistan = LatLng(30.3753, 69.3451)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(pakistan, 10f)
        }

        var isMapVisible by remember { mutableStateOf(true) }
        val markerState = remember { mutableStateOf<MarkerState?>(null) }

        Box(
            modifier = Modifier
                .height(300.dp)
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

        Spacer(modifier = Modifier.height(15.dp))
        Row {
            Text(
                text = "Description",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.poppinsmedium)),
                    fontSize = 14.sp,
                    color = Color(0xFF000000),
                ),
                modifier = Modifier
                    .padding(start = 25.dp)

            )
            Text(
                text = "*",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.poppinsmedium)),
                    fontSize = 12.sp,
                    color = Color(0xFFD34040),
                ),
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = description.value,
            onValueChange = { description.value = it },
            placeholder = {
                Text(
                    text = "Add the description of your fundraiser",
                    fontFamily = FontFamily(Font(R.font.poppinsregular)),
                    fontSize = 12.sp,
                    color = Color(0xFF808080),
                    modifier = Modifier
                        .align(Alignment.Start)
                )
            },
            singleLine = false,
            shape = RoundedCornerShape(0),
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .background(
                    color = Color(0xFFFFFFFF)
                )
                .padding(start = 20.dp, end = 20.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = Color(0xFFD34040),
                focusedBorderColor = Color(0xFFD34040),
                unfocusedBorderColor = Color(0xFF808080).copy(alpha = 0.45f),
            ),
        )
        Spacer(modifier = Modifier.height(15.dp))
        Row {
            Text(
                text = "Add Image",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.poppinsmedium)),
                    fontSize = 14.sp,
                    color = Color(0xFF000000),
                ),
                modifier = Modifier
                    .padding(start = 25.dp)

            )
            Text(
                text = "*",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.poppinsmedium)),
                    fontSize = 12.sp,
                    color = Color(0xFFD34040),
                ),
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .background(Color.White)
                .padding(start = 20.dp, end = 20.dp)
                .border(BorderStroke(width = 1.dp, color = Color(0xFF808080).copy(alpha = 0.45f))),
            contentAlignment = Alignment.Center
        ) {
            if (imageUri == null) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.addphoto),
                        contentDescription = null,
                        modifier = Modifier.size(35.dp),
                        tint = Color(0XFFD34040).copy(0.80f),
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Upload an Image",
                        fontFamily = FontFamily(Font(R.font.poppinsmedium)),
                        fontSize = 14.sp,
                        color = Color(0XFF808080)
                    )
                }
            } else {

                imageUri?.let {
                    if (Build.VERSION.SDK_INT < 28) {
                        bitmap.value = MediaStore.Images
                            .Media.getBitmap(context.contentResolver, it)
                    } else {
                        val source = ImageDecoder.createSource(context.contentResolver, it)
                        bitmap.value = ImageDecoder.decodeBitmap(source)
                    }

                    bitmap.value?.let { btm ->
                        Image(
                            bitmap = btm.asImageBitmap(),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { launcher.launch("image/*") },
            shape = RoundedCornerShape(8),
            colors = ButtonDefaults.buttonColors(Color.Gray.copy(alpha = 0.3f)),
            modifier = Modifier
                .size(width = 150.dp, height = 36.dp)
                .padding(start = 20.dp)
        )
        {
            Text(
                text = "Choose file",
                fontFamily = FontFamily(Font(R.font.poppinsmedium)),
                fontSize = 14.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

      /*  Row {
            Text(
                text = "Add more Photos",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.poppinsmedium)),
                    fontSize = 14.sp,
                    color = Color(0xFF000000),
                ),
                modifier = Modifier
                    .padding(start = 25.dp)

            )
            Text(
                text = "*",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.poppinsmedium)),
                    fontSize = 12.sp,
                    color = Color(0xFFD34040),
                ),
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(){

            Column {
                if (selectedImageUris.isEmpty()) {
                    // Your code for displaying the "Add" button
                    Box(
                        modifier = Modifier
                            .height(80.dp)
                            .width(100.dp)
                            .padding(start = 20.dp)
                            .border(
                                BorderStroke(
                                    width = 1.dp,
                                    color = Color(0xFF808080).copy(alpha = 0.45f)
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = Color(0XFFD34040).copy(0.80f),
                            modifier = Modifier.size(48.dp)
                        )
                    }
                } else {
                    for (i in selectedImageUris.indices step 2) {
                        // Display images in rows of 3
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .padding(start = 20.dp , end = 20.dp),
                            // horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            for (j in i until (i + 2).coerceAtMost(selectedImageUris.size)) {
                                AsyncImage(
                                    model = selectedImageUris[j],
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(160.dp)
                                        .padding(5.dp),
                                    contentScale = ContentScale.FillBounds
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = {
                        multiplePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                    shape = RoundedCornerShape(0),
                    colors = ButtonDefaults.buttonColors(Color(0xFFD34040)),
                    modifier = Modifier
                        .size(width = 160.dp, height = 65.dp)
                        .padding(start = 20.dp, top = 8.dp, bottom = 20.dp)
                ) {
                    Text(
                        text = "Choose file",
                        fontFamily = FontFamily(Font(R.font.poppinsmedium)),
                        fontSize = 14.sp,
                    )
                }
            }
        }*/
        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = {
                // Validate user input and upload banner image to Firebase Storage
                if (TextUtils.isEmpty(reportTitle.value.toString())) {
                    Toast.makeText(context, "Please fill out all fields", Toast.LENGTH_SHORT).show()
                } else if (TextUtils.isEmpty(description.value.toString())) {
                    Toast.makeText(context, "Please fill out all fields", Toast.LENGTH_SHORT).show()
                } else {
                    if (imageUri != null) {
                        uploadImageToStorage()
                    }
                    if (selectedImageUris.isNotEmpty()) { // Access selectedImageUris using .value
                        uploadMultipleImagesToStorage(selectedImageUris, context) // Access selectedImageUris using .value
                    }
                }

            },
            shape = RoundedCornerShape(15),
            colors = ButtonDefaults.buttonColors(Color(0xFFD34040)),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(start = 20.dp, end = 20.dp)
        ) {
            Text(
                text = "Submit",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.robotomedium)),
                color = Color(0xFFFFFFFF)
            )
        }
        Spacer(modifier = Modifier.height(35.dp))
        }
    }


fun addReportsToFirebase(
    reportTitle: String,
    reportDescription: String,
    imageUrl: String,
    context: Context,
    latitude: Double,
    longitude: Double,
    currentUid: String, // Accept current UID as a parameter

) {
    // Initialize Firebase Firestore and collection reference.
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    val dbReports: CollectionReference = db.collection("Reports")

    // Create a Reports object with all the data.
    val reports = Reports(reportTitle, reportDescription, imageUrl, latitude, longitude,currentUid)

    // Add the data to Firestore.
    dbReports.add(reports).addOnSuccessListener {
        // Display a success toast message.
        Toast.makeText(
            context,
            "Report Submitted",
            Toast.LENGTH_SHORT
        ).show()
    }.addOnFailureListener { e ->
        // Display an error toast message if the data addition process fails.
        Toast.makeText(context, "Failed to submit Report \n$e", Toast.LENGTH_SHORT).show()
    }
}
fun uploadMultipleImagesToStorage(images: List<Uri>, context: Context) {
    val storage = FirebaseStorage.getInstance()
    val storageRef: StorageReference = storage.reference

    images.forEachIndexed { index, imageUri ->
        val imageRef: StorageReference = storageRef.child("multiple_images/${System.currentTimeMillis()}_${index}_${imageUri.lastPathSegment}")

        val stream = ByteArrayOutputStream()
        val bitmap = if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
        } else {
            val source = ImageDecoder.createSource(context.contentResolver, imageUri)
            ImageDecoder.decodeBitmap(source)
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream)
        val data = stream.toByteArray()

        imageRef.putBytes(data)
            .addOnSuccessListener {
                // Handle the success of uploading each image here.
                val imageUrl = it.metadata?.reference?.downloadUrl.toString()
                // You can save the URLs of these images to Firestore if needed.
            }
            .addOnFailureListener { e ->
                // Handle the failure to upload each image here.
                Toast.makeText(context, "Failed to upload image: $e", Toast.LENGTH_SHORT).show()
            }
    }
}

data class Reports(
    var reportTitle: String,
    var reportDescription: String,
    var imageUrl: String, // Added imageUrl field
    var latitude: Double,  // Added latitude field
    var longitude: Double , // Added longitude field
    var currentUid: String // Add currentUid to Campaigns data class
)

/*
@Preview
@Composable
fun repEviPRev(){
    ReportEvidenceImages()

}
@Preview
@Composable
fun repSubPRev(){
    fun

}*/

/*
@Preview
@Composable
fun SubmitReportPreview() {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
           // .padding(16.dp)
    ) {
        SubmitReport(context)
    }
}*/
