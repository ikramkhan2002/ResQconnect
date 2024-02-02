package com.example.safetytips

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.runtime.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fypdisastermanagement.R
import com.example.fypdisastermanagement.destinations.MyCampaigns
import com.example.fypdisastermanagement.destinations.SocialWorkerHome
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubmitCampaign(navController: NavController) {
    val campaignTitle= remember{ mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val contactNo = remember { mutableStateOf("") }
    val startDate = remember { mutableStateOf("") }
    val endDate = remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var startDateValue by rememberSaveable { mutableStateOf("") }
    var endDateValue by rememberSaveable { mutableStateOf("") }
    // Obtain the currently logged-in user's UID
    val firebaseAuth = FirebaseAuth.getInstance()
    val currentUser = firebaseAuth.currentUser
    val currentUid = currentUser?.uid ?: ""

    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
    )
    {
        Spacer(modifier = Modifier.height(22.dp))
        Text(
            text = "Submit your Campaign",
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
                text = "Title of your campaign",
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
            value = campaignTitle.value,
            onValueChange = { campaignTitle.value = it },
            placeholder = {
                Text(
                    text = "Enter the title of your campaign",
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
                .padding(start = 20.dp, end = 20.dp)
                .background(
                    color = Color(0xFFFFFFFF)
                ),
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
        Spacer(modifier = Modifier.height(10.dp))
        val markerLatitude = remember { mutableStateOf(0.0) }
        val markerLongitude = remember { mutableStateOf(0.0) }
        //MAPS
        Spacer(modifier = Modifier.height(15.dp))
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
        Spacer(modifier = Modifier.height(20.dp))
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
                    text = "Enter the description of your campaign",
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
                .padding(start = 20.dp, end = 20.dp)
                .background(
                    color = Color(0xFFFFFFFF)
                ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = Color(0xFFD34040),
                focusedBorderColor = Color(0xFFD34040),
                unfocusedBorderColor = Color(0xFF808080).copy(alpha = 0.45f),
            ),
        )
        Spacer(modifier = Modifier.height(15.dp))
        Row {
            Text(
                text = "Contact No",
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
            value = contactNo.value,
            onValueChange = { contactNo.value = it },
            placeholder = {
                Text(
                    text = "+92-3331905200",
                    fontFamily = FontFamily(Font(R.font.poppinsregular)),
                    fontSize = 12.sp,
                    color = Color(0xFF808080),
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.None
            ),
            singleLine = true,
            shape = RoundedCornerShape(0),
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
                .background(
                    color = Color(0xFFFFFFFF)
                ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = Color(0xFFD34040),
                focusedBorderColor = Color(0xFFD34040),
                unfocusedBorderColor = Color(0xFF808080).copy(alpha = 0.45f),
            ),
        )
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = Modifier
                .padding(start = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            StartDate(startDate = startDate, onStartDateSelected = { startDateValue = it })
            Spacer(modifier = Modifier.width(18.dp))
            EndDate(endDate = endDate, onEndDateSelected = { endDateValue = it })
        }
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
        var imageUri by remember { mutableStateOf<Uri?>(null) }

        val context = LocalContext.current
        val bitmap = remember { mutableStateOf<Bitmap?>(null) }

        val launcher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
                imageUri = uri
            }

        DisposableEffect(Unit) {
            onDispose {
                // Reset the imageUri when the composable is disposed (leaving the page)
                campaignTitle.value = ""
                description.value = ""
                contactNo.value = ""
                startDateValue = ""
                endDateValue = ""
                markerLatitude.value = 0.0
                markerLongitude.value = 0.0
                imageUri = null
                bitmap.value = null
            }
        }

        Column(
        ) {
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
                    .background(Color.White)
                    .border(
                        BorderStroke(
                            width = 1.dp,
                            color = Color(0xFF808080).copy(alpha = 0.45f)
                        )
                    ),
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
                                contentScale = ContentScale.Crop,
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
        }
        Spacer(modifier = Modifier.height(50.dp))
        Button(
            onClick = {
                // Validate input fields and upload image to Firebase Storage
                if (TextUtils.isEmpty(campaignTitle.value)) {
                    Toast.makeText(context, "Please fill out the campaign title", Toast.LENGTH_SHORT).show()
                } else if (TextUtils.isEmpty(description.value)) {
                    Toast.makeText(context, "Please fill out the description", Toast.LENGTH_SHORT).show()
                } else if (TextUtils.isEmpty(contactNo.value)) {
                    Toast.makeText(context, "Please fill out the contact number", Toast.LENGTH_SHORT).show()
                } else if (imageUri == null) {
                    Toast.makeText(context, "Please select an image", Toast.LENGTH_SHORT).show()
                } else {
                    // Upload the image to Firebase Storage
                    uploadImageToFirebaseStorage(
                        imageUri!!, campaignTitle.value, context
                    ) { downloadUrl ->

                        // Store campaign details in Firebase Firestore
                        addCampaignsToFirebase(
                            campaignTitle.value,
                            description.value,
                            contactNo.value,
                            startDateValue,
                            endDateValue,
                            markerLatitude.value, // Latitude of the marker
                            markerLongitude.value, // Longitude of the marker
                            downloadUrl,
                            currentUid,
                            context
                        )
                        // Reset all the state variables to their initial values or empty values
                        campaignTitle.value = ""
                        description.value = ""
                        contactNo.value = ""
                        startDateValue = ""
                        endDateValue = ""
                        markerLatitude.value = 0.0
                        markerLongitude.value = 0.0
                        imageUri = null
                        bitmap.value = null
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

@SuppressLint("SuppressLint")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StartDate(
    startDate: MutableState<String>,
    onStartDateSelected: (String) -> Unit
) {
    val context = LocalContext.current
    var pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("dd MMM, yyyy")
                .format(pickedDate)
        }
    }
    val dateDialogState = rememberMaterialDialogState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                dateDialogState.show()
            },
            shape = RoundedCornerShape(0),
            colors = ButtonDefaults.buttonColors(Color(0xFFD34040)),
            modifier = Modifier
                .size(width = 152.dp, height = 40.dp)
        ) {
            Text(
                text = "Select Start Date",
                fontFamily = FontFamily(Font(R.font.poppinsmedium)),
                fontSize = 12.sp,
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = formattedDate)
        Spacer(modifier = Modifier.height(16.dp))
        MaterialDialog(
            dialogState = dateDialogState,
            buttons = {
                positiveButton(text = "Ok") {
                    // When 'Ok' is clicked, update the startDate value
                    startDate.value = formattedDate
                    onStartDateSelected(formattedDate)
                    Toast.makeText(
                        context,
                        "Selected date: $formattedDate",
                        Toast.LENGTH_LONG
                    ).show()
                }
                negativeButton(text = "Cancel")
            }
        ) {
            datepicker(
                initialDate = LocalDate.now(),
                title = "Pick a date"
            ) {
                pickedDate = it
            }
        }
    }
}

@SuppressLint("SuppressLint")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EndDate(
    endDate: MutableState<String>,
    onEndDateSelected: (String) -> Unit
) {
    val context = LocalContext.current
    var pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("dd MMM, yyyy")
                .format(pickedDate)
        }
    }
    val dateDialogState = rememberMaterialDialogState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                dateDialogState.show()
            },
            shape = RoundedCornerShape(0),
            colors = ButtonDefaults.buttonColors(Color(0xFFD34040)),
            modifier = Modifier
                .size(width = 150.dp, height = 40.dp)
        ) {
            Text(
                text = "Select End Date",
                fontFamily = FontFamily(Font(R.font.poppinsmedium)),
                fontSize = 12.sp,
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = formattedDate)
        Spacer(modifier = Modifier.height(16.dp))
        MaterialDialog(
            dialogState = dateDialogState,
            buttons = {
                positiveButton(text = "Ok") {
                    // When 'Ok' is clicked, update the endDate value
                    endDate.value = formattedDate
                    onEndDateSelected(formattedDate)
                    Toast.makeText(
                        context,
                        "Selected date: $formattedDate",
                        Toast.LENGTH_LONG
                    ).show()
                }
                negativeButton(text = "Cancel")
            }
        ) {
            datepicker(
                initialDate = LocalDate.now(),
                title = "Pick a date"
            ) {
                pickedDate = it
            }
        }
    }
}

fun addCampaignsToFirebase(
    campaignTitle: String,
    campaignDescription: String,
    contactNo: String,
    campaignStartDate: String,
    campaignEndDate: String,
    latitude: Double,
    longitude: Double,
    imageUrl: String, // Add imageUrl parameter for storing image URL
    currentUid: String, // Accept current UID as a parameter
    context: Context
) {
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    val dbCampaigns: CollectionReference = db.collection("Campaigns")

    val campaigns = Campaigns(
        campaignTitle,
        campaignDescription,
        contactNo,
        campaignStartDate,
        campaignEndDate,
        latitude,
        longitude,
        imageUrl, // Assign the image URL to the Campaigns data class
        currentUid
    )

    dbCampaigns.add(campaigns)
        .addOnSuccessListener {
            Toast.makeText(
                context,
                "Campaign Submitted",
                Toast.LENGTH_SHORT
            ).show()
        }.addOnFailureListener { e ->
            Toast.makeText(context, "Fail to submit Campaign \n$e", Toast.LENGTH_SHORT).show()
        }
}



data class Campaigns(
    var campaignTitle: String,
    var campaignDescription: String,
    var contactNo: String,
    var campaignStartDate: String,
    var campaignEndDate: String,
    var latitude: Double,
    var longitude: Double,
    var imageUrl: String,
    var currentUid: String // Add currentUid to Campaigns data class
)
private fun uploadImageToFirebaseStorage(
    imageUri: Uri,
    campaignTitle: String,
    context: Context,
    onImageUploadComplete: (String) -> Unit // Callback to pass the download URL
) {
    val storage = Firebase.storage
    val storageReference = storage.reference.child("campaign_images/$campaignTitle.jpg")
    val uploadTask = storageReference.putFile(imageUri)

    uploadTask.addOnSuccessListener { taskSnapshot ->
        // Image uploaded successfully
        storageReference.downloadUrl.addOnSuccessListener { uri ->
            // Retrieve the download URL
            val downloadUrl = uri.toString()

            // Pass the download URL through the callback
            onImageUploadComplete(downloadUrl)

            // Display a success toast message
            Toast.makeText(context, "Image uploaded to Firebase Storage", Toast.LENGTH_SHORT).show()
        }
    }.addOnFailureListener { e ->
        // Handle the error
        Toast.makeText(context, "Image upload failed: $e", Toast.LENGTH_SHORT).show()
    }
}


@Composable
@Preview
fun SubmitCampaignPreview() {
    val context = LocalContext.current
    MaterialTheme {
        SubmitCampaign( navController = rememberNavController())
    }
}