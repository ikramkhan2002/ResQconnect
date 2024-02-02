package com.example.fypdisastermanagement.submit

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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.runtime.*
import androidx.compose.material.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fypdisastermanagement.R
import com.example.fypdisastermanagement.destinations.MyFundraisers
import com.example.fypdisastermanagement.destinations.RescuerHome
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubmitFundraiser(navController: NavController) {
    val fundraiserTitle = remember{ mutableStateOf("") }
    val description = remember{ mutableStateOf("") }
    val linkReport = remember { mutableStateOf("") }
    val amount = remember{ mutableStateOf("") }
    val startDate = remember { mutableStateOf("") }
    val endDate = remember { mutableStateOf("") }
    val fundraiserImageUrl = remember { mutableStateOf("") }

    // Obtain the currently logged-in user's UID
    val firebaseAuth = FirebaseAuth.getInstance()
    val currentUser = firebaseAuth.currentUser
    val currentUid = currentUser?.uid ?: ""

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
    )
    {
        Spacer(modifier = Modifier.height(22.dp))
        Text(
            text = "Create a Fundraiser",
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
            text = "Make a difference!",
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
                text = "Title of your fundraiser",
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
            value = fundraiserTitle.value,
            onValueChange = { fundraiserTitle.value = it },
            placeholder = {
                Text(
                    text = "Enter the title of your fundraiser",
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
            onValueChange = { description.value = it},
            placeholder = {
                Text(
                    text = "Enter the description of your fundraiser",
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
                .height(160.dp)
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
                text = "Link of the Report",
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
            value = linkReport.value,
            onValueChange = { linkReport.value = it },
            placeholder = {
                Text(
                    text = "Enter the exact name of the report you want to raise fund for",
                    fontFamily = FontFamily(Font(R.font.poppinsregular)),
                    fontSize = 12.sp,
                    color = Color(0xFF808080),
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
                text = "Your starting goal",
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
            value = amount.value,
            onValueChange = { amount.value = it },
            placeholder = {
                Text(
                    text = "$ Enter the amount",
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
            SelectStartDate(startDate)
            Spacer(modifier = Modifier.width(16.dp))
            SelectEndDate(endDate)
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
        val fundraiserImageUrl = remember { mutableStateOf("") }
        val launcher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
                imageUri = uri
                fundraiserImageUrl.value = uri?.toString() ?: ""
            }
        DisposableEffect(Unit) {
            onDispose {
                // Reset the imageUri when the composable is disposed (leaving the page)
                fundraiserTitle.value = ""
                description.value = ""
                linkReport.value = ""
                amount.value = ""
                startDate.value = ""
                endDate.value = ""
                imageUri = null
            }
        }

        Column(
        ) {
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(start = 20.dp, end = 20.dp)
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

            /* imageUri?.let { uri ->
                 fundraisersImageToStorage(userId, uri.toString(), context) { imageUrl ->
                     // Handle the uploaded image URL here.
                     fundraiserImageUrl.value = imageUrl
                 }*/
        }
        Spacer(modifier = Modifier.height(50.dp))
        Button(
            onClick = {
                // on below line we are validating user input parameters.
                if (TextUtils.isEmpty(fundraiserTitle.value.toString())) {
                    Toast.makeText(context, "Please fill out all fields", Toast.LENGTH_SHORT).show()
                } else if (TextUtils.isEmpty(description.value.toString())) {
                    Toast.makeText(context, "Please fill out all fields", Toast.LENGTH_SHORT).show()
                } else if (TextUtils.isEmpty(linkReport.value.toString())) {
                    Toast.makeText(context, "Please fill out all fields", Toast.LENGTH_SHORT).show()
                } else if (TextUtils.isEmpty(amount.value.toString())) {
                    Toast.makeText(context, "Please fill out all fields", Toast.LENGTH_SHORT).show()
                } else {
                    if (startDate.value.isEmpty()) {
                        startDate.value = LocalDate.now().toString()
                    }

                    if (endDate.value.isEmpty()) {
                        endDate.value = LocalDate.now().toString()
                    }

                    // Check if the user has already created a fundraiser for this report link.
                    checkExistingFundraiser(linkReport.value, currentUid, context) { hasExistingFundraiser ->
                        if (hasExistingFundraiser) {
                            // Display a toast message indicating that the user has already created a fundraiser for this report.
                            Toast.makeText(
                                context,
                                "You have already created a fundraiser for this report",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            // Upload the image to Firebase Storage and get the download URL
                            uploadImageToFirebaseStorage(context, imageUri) { imageUrl ->
                                // Once the image is uploaded, you can use the `imageUrl` in your `addDataToFirebase` function.
                                addDataToFirebase(
                                    fundraiserTitle.value,
                                    description.value,
                                    linkReport.value,
                                    amount.value,
                                    startDate.value,
                                    endDate.value,
                                    imageUrl, // Use the Firebase Storage URL
                                    currentUid,
                                    context
                                )
                                // Resetting state variables to clear input fields
                                fundraiserTitle.value = ""
                                description.value = ""
                                linkReport.value = ""
                                amount.value = ""
                                startDate.value = ""
                                endDate.value = ""
                                imageUri = null
                            }
                        }
                    }
                }
            },
            //
            shape = RoundedCornerShape(15),
            colors = ButtonDefaults.buttonColors(Color(0xFFD34040)),
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
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


@Composable
fun SelectStartDate(startDate: MutableState<String>) {
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
    )  {
        Button(
            onClick = {
                dateDialogState.show()
            },
            shape = RoundedCornerShape(0),
            colors = ButtonDefaults.buttonColors(Color(0xFFD34040)),
            modifier = Modifier
                .width(152.dp)
                .height(40.dp)
        )
        {
            Text(
                text = "Select Start Date",
                fontFamily = FontFamily(Font(R.font.poppinsmedium)),
                fontSize = 12.sp,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = formattedDate)
        Spacer(modifier = Modifier.height(16.dp))
        MaterialDialog(
            dialogState = dateDialogState,
            buttons = {
                positiveButton(text = "Ok") {
                    // startDate.value = pickedDate.toString()
                    Toast.makeText(
                        context,
                        "Selected",
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

@SuppressLint("SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SelectEndDate(endDate: MutableState<String>) {
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
                .width(152.dp)
                .height(40.dp)
        )
        {
            Text(
                text = "Select End Date",
                fontFamily = FontFamily(Font(R.font.poppinsmedium)),
                fontSize = 12.sp,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = formattedDate)
        Spacer(modifier = Modifier.height(16.dp))
        MaterialDialog(
            dialogState = dateDialogState,
            buttons = {
                positiveButton(text = "Ok") {
                    endDate.value = pickedDate.toString()
                    Toast.makeText(
                        context,
                        "Selected",
                        Toast.LENGTH_LONG
                    ).show()
                }
                negativeButton(text = "Cancel")
            }
        ) {
            datepicker(
                initialDate = LocalDate.now(),
                title = "Pick a date",
            ) {
                pickedDate = it
            }
        }
    }
}

fun uploadImageToFirebaseStorage(context: Context, imageUri: Uri?, callback: (String) -> Unit) {
    if (imageUri != null) {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        val imageRef = storageRef.child("fundraiser_images/${System.currentTimeMillis()}_${imageUri.lastPathSegment}")

        imageRef.putFile(imageUri)
            .addOnSuccessListener {
                // Image upload is successful, get the download URL
                imageRef.downloadUrl
                    .addOnSuccessListener { uri ->
                        callback(uri.toString()) // Pass the download URL to the callback function
                    }
            }
            .addOnFailureListener { e ->
                // Handle the failure of image upload
                Toast.makeText(context, "Failed to upload image: $e", Toast.LENGTH_SHORT).show()
            }
    } else {
        // Handle the case where no image is selected
        callback("") // Pass an empty URL to the callback
    }
}
fun addDataToFirebase(
    fundraiserTitle: String,
    description: String,
    linkReport: String,
    amount: String,
    startDate: String,
    endDate: String,
    fundraiserImageUrl: String,
    currentUid: String, // Accept current UID as a parameter
    context: Context
) {
    // on below line creating an instance of firebase firestore.
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    // creating a collection reference for our Firebase Firestore database.
    val dbFundraiser: CollectionReference = db.collection("Fundraiser")

    // Query to check the count of existing fundraisers with the same report link.
    dbFundraiser
        .whereEqualTo("linkReport", linkReport)
        .get()
        .addOnSuccessListener { querySnapshot ->
            // Check if there are already two fundraisers with the same report link.
            if (querySnapshot.size() >= 2) {
                // Display a toast message indicating that two fundraisers already exist.
                Toast.makeText(
                    context,
                    "Can't add any more , Two fundraisers already created for this report",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // Adding our data to the Fundariser object class.
                val fundraiser = Fundariser(
                    fundraiserTitle, description, linkReport,
                    amount, startDate, endDate, fundraiserImageUrl, currentUid
                )

                // Below method is used to add data to Firebase Firestore.
                dbFundraiser.add(fundraiser)
                    .addOnSuccessListener {
                        // After the data addition is successful, display a success toast message.
                        Toast.makeText(
                            context,
                            "Fundraiser Submitted",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .addOnFailureListener { e ->
                        // This method is called when the data addition process is failed.
                        // Displaying a toast message when data addition is failed.
                        Toast.makeText(
                            context,
                            "Fail to submit Fundraiser \n$e",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
        }
        .addOnFailureListener { e ->
            // Handle the failure of the query.
            Toast.makeText(
                context,
                "Failed to check existing fundraisers \n$e",
                Toast.LENGTH_SHORT
            ).show()
        }
}
data class Fundariser(
    // on below line creating variables.
    var fundraiserTitle: String,
    var description: String,
    var linkReport: String,
    var amount: String,
    var startDate: String,
    var endDate: String,
    var fundraiserImageUrl: String,
    var currentUid: String

)


//@Composable
//@Preview
//fun SubmitFundraiserPreview() {
//    // You can pass a dummy Context for the preview.
//    val context = LocalContext.current
//    SubmitFundraiser(context, navController = rememberNavController())
//}


fun checkExistingFundraiser(linkReport: String, currentUid: String, context: Context, callback: (Boolean) -> Unit) {
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    val dbFundraiser: CollectionReference = db.collection("Fundraiser")

    dbFundraiser
        .whereEqualTo("linkReport", linkReport)
        .whereEqualTo("currentUid", currentUid)
        .get()
        .addOnSuccessListener { querySnapshot ->
            // If the user has already created a fundraiser for this report, callback with true.
            callback(querySnapshot.size() > 0)
        }
        .addOnFailureListener { e ->
            // Handle the failure of the query.
            Toast.makeText(
                context,
                "Failed to check existing fundraisers \n$e",
                Toast.LENGTH_SHORT
            ).show()
            // Callback with false in case of an error.
            callback(false)
        }
}