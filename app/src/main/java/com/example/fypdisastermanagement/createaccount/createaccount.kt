package com.example.fypdisastermanagement.createaccount

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
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fypdisastermanagement.R
import com.example.fypdisastermanagement.destinations.ChooseYourselfScreen
import com.example.fypdisastermanagement.destinations.ForgotPassword
import com.example.fypdisastermanagement.destinations.LoginScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

@Composable
fun CreateAccount(viewModel: RegistrationViewModel,navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = Color(0xFFEB3B49)))
    {
        MainPanel(viewModel,navController)
    }
}

@Composable
fun MainPanel(viewModel: RegistrationViewModel,navController: NavController){


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp)
            .background(
                color = Color(0xFFFFFFFF),
                shape = RoundedCornerShape(
                    topStart = 30.dp,
                    topEnd = 30.dp
                )
            )
    )
    {
        Spacer(modifier = Modifier.height(30.dp))
        Divider(
            thickness = 5.dp,
            color = Color.Black,
            modifier = Modifier
                .width(120.dp)
                .clip(RoundedCornerShape(50))
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Create an Account",
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.robotomedium)),
                fontSize = 24.sp,
                color = Color.Black
            ),
            modifier = Modifier
                .padding(start = 20.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .padding(start = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(
                text = "Already have an account?",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.robotoregular)),
                    fontSize = 16.sp,
                    color = Color(0XFF808080)
                )
            )
            Text(
                text = "Sign In",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.robotomedium)),
                    fontSize = 16.sp,
                    color = Color(0XFFD34040)
                ),
                modifier = Modifier.clickable { navController.navigate(LoginScreen.route) }
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        Divider(
            thickness = 1.5.dp,
            color = Color(0xFF707070).copy(alpha = 0.16f),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))
        TextFields(viewModel)
        Spacer(modifier = Modifier.height(45.dp))
        val context = LocalContext.current
        Button(
            onClick = {
                val firstName = viewModel.firstName.value
                val lastName = viewModel.lastName.value
                val fatherName = viewModel.fatherName.value
                val emailAddress = viewModel.emailAddress.value
                val password = viewModel.password.value
                val contactNo = viewModel.contactNo.value
                val gender = viewModel.gender.value
                val profileImageUrl = viewModel.profileImageUrl.value
                val role = viewModel.role.value


                if (TextUtils.isEmpty(firstName)) {
                    Toast.makeText(context, "Please enter first name", Toast.LENGTH_SHORT).show()
                } else if (TextUtils.isEmpty(lastName)) {
                    Toast.makeText(context, "Please enter last name", Toast.LENGTH_SHORT).show()
                } else if (TextUtils.isEmpty(fatherName)) {
                    Toast.makeText(context, "Please enter father's name", Toast.LENGTH_SHORT).show()
                } else {
                    addUserToFirebase(
                        firstName,
                        lastName,
                        fatherName,
                        emailAddress,
                        password,
                        contactNo,
                        gender,
                        profileImageUrl,
                        role,
                        context,
                        navController
                    )
                }
            },


            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp),
            shape = RoundedCornerShape(15),
            colors = ButtonDefaults.buttonColors(Color(0xFFD34040)),
        ) {
            Text(
                text = "Sign Up",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.robotomedium)),
                    fontSize = 20.sp,
                    color = Color.White
                )
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFields(viewModel: RegistrationViewModel){
    var passwordVisibility by remember { mutableStateOf(false) }

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val icon =
        if(passwordVisibility)
            painterResource(id = R.drawable.passwordvisibility)
        else
            painterResource(id = R.drawable.passwordvisibilityoff)

    Column(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp),
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "First Name",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.robotomedium)),
                        fontSize = 16.sp,
                        color = Color(0xFF808080)
                    ),
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = viewModel.firstName.value,
                    onValueChange = { viewModel.firstName.value = it },
                    placeholder = {
                        Text(
                            text = "First Name",
                            fontFamily = FontFamily(Font(R.font.robotomedium)),
                            fontSize = 16.sp,
                            color = Color(0xFFCCCCCC)
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(15),
                    modifier = Modifier
                        .height(55.dp)
                        .width(155.dp)
                        .background(
                            color = Color(0xFFFFFFFF)
                        ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = Color(0xFFD34040),
                        focusedBorderColor = Color(0xFFD34040),
                        unfocusedBorderColor = Color(0xFFE2E2E9)
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = "Last Name",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.robotomedium)),
                        fontSize = 16.sp,
                        color = Color(0xFF808080)
                    ),
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = viewModel.lastName.value,
                    onValueChange = { viewModel.lastName.value = it },
                    placeholder = {
                        Text(
                            text = "Last Name",
                            fontFamily = FontFamily(Font(R.font.robotomedium)),
                            fontSize = 16.sp,
                            color = Color(0xFFCCCCCC)
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(15),
                    modifier = Modifier
                        .height(55.dp)
                        .width(155.dp)
                        .background(
                            color = Color(0xFFFFFFFF)
                        ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = Color(0xFFD34040),
                        focusedBorderColor = Color(0xFFD34040),
                        unfocusedBorderColor = Color(0xFFE2E2E9)
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Father's Name",
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.robotomedium)),
                fontSize = 16.sp,
                color = Color(0xFF808080)
            ),
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = viewModel.fatherName.value,
            onValueChange = { viewModel.fatherName.value = it },
            placeholder = {
                Text(
                    text = "Father's Name",
                    fontFamily = FontFamily(Font(R.font.robotomedium)),
                    fontSize = 16.sp,
                    color = Color(0xFFCCCCCC)
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(15),
            modifier = Modifier
                .height(55.dp)
                .fillMaxWidth()
                .background(
                    color = Color(0xFFFFFFFF)
                ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = Color(0xFFD34040),
                focusedBorderColor = Color(0xFFD34040),
                unfocusedBorderColor = Color(0xFFE2E2E9)
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Email Address",
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.robotomedium)),
                fontSize = 16.sp,
                color = Color(0xFF808080)
            ),
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = viewModel.emailAddress.value,
            onValueChange = { viewModel.emailAddress.value = it },
            placeholder = {
                Text(
                    text = "hiraraheel@gmail.com",
                    fontFamily = FontFamily(Font(R.font.robotomedium)),
                    fontSize = 16.sp,
                    color = Color(0xFFCCCCCC)
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(15),
            modifier = Modifier
                .height(55.dp)
                .fillMaxWidth()
                .background(
                    color = Color(0xFFFFFFFF)
                ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = Color(0xFFD34040),
                focusedBorderColor = Color(0xFFD34040),
                unfocusedBorderColor = Color(0xFFE2E2E9)
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Password",
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.robotomedium)),
                fontSize = 16.sp,
                color = Color(0xFF808080)
            ),
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = viewModel.password.value,
            onValueChange = { viewModel.password.value = it },
            placeholder = {
                Text(
                    text = "Enter your Password",
                    fontFamily = FontFamily(Font(R.font.robotomedium)),
                    fontSize = 16.sp,
                    color = Color(0xFFCCCCCC)
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(15),
            modifier = Modifier
                .height(55.dp)
                .fillMaxWidth()
                .background(
                    color = Color(0xFFFFFFFF)
                ),
            trailingIcon = {
                if(isFocused) {
                    IconButton(onClick = {
                        passwordVisibility = !passwordVisibility
                    }) {
                        Icon(
                            painter = icon,
                            contentDescription = "Visibility Icon"
                        )
                    }
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = Color(0xFFD34040),
                focusedBorderColor = Color(0xFFD34040),
                unfocusedBorderColor = Color(0xFFE2E2E9)
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            interactionSource = interactionSource,
            visualTransformation =
            if (passwordVisibility)
                VisualTransformation.None
            else
                PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Contact No",
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.robotomedium)),
                fontSize = 16.sp,
                color = Color(0xFF808080)
            ),
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = viewModel.contactNo.value,
            onValueChange = { viewModel.contactNo.value = it },
            placeholder = {
                Text(
                    text = "+92-3331905200",
                    fontFamily = FontFamily(Font(R.font.robotomedium)),
                    fontSize = 16.sp,
                    color = Color(0xFFCCCCCC)
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(15),
            modifier = Modifier
                .height(55.dp)
                .fillMaxWidth()
                .background(
                    color = Color(0xFFFFFFFF)
                ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = Color(0xFFD34040),
                focusedBorderColor = Color(0xFFD34040),
                unfocusedBorderColor = Color(0xFFE2E2E9)
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Gender",
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.robotomedium)),
                fontSize = 16.sp,
                color = Color(0xFF808080)
            ),
        )
        Spacer(modifier = Modifier.height(10.dp))
        RadioButton(viewModel)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Profile Image",
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.robotomedium)),
                fontSize = 16.sp,
                color = Color(0xFF808080)
            ),
        )
        Spacer(modifier = Modifier.height(10.dp))
        AddProfilePhoto(viewModel)
    }
}

@Composable
fun RadioButton(viewModel: RegistrationViewModel) {
    val radioOptions = listOf("Male", "Female")
    val selectedOption = viewModel.gender.value

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        radioOptions.forEach { text ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            viewModel.gender.value = text
                        }
                    )
                    .border(
                        width = 1.dp,
                        color = Color(0xFFE2E2E9),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(4.dp)
                    .weight(1f),
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = { viewModel.gender.value = text },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color(0xFFD34040),
                        unselectedColor = Color.Black
                    )
                )
                Text(
                    text = text,
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.robotoregular)),
                        fontSize = 16.sp,
                        color = Color.Black
                    ),
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}

@Composable
fun AddProfilePhoto(viewModel: RegistrationViewModel) {

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
        viewModel.profileImageUrl.value = uri?.toString() ?: ""  // Set the profile image URL in the ViewModel
    }


    Column{
        Box(
            modifier = Modifier
                .height(120.dp)
                .width(120.dp)
                .border(BorderStroke(width = 1.dp, color = Color(0xFF808080).copy(alpha = 0.45f))),
            contentAlignment = Alignment.Center
        ) {
            if (imageUri == null) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(50.dp),
                        tint = Color.Gray
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
                .size(width = 130.dp, height = 36.dp)
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
}

@Preview
@Composable
fun CreateAccountPrev(){
    CreateAccount(viewModel = RegistrationViewModel(), navController = rememberNavController())
}

fun addUserToFirebase(
    firstName: String,
    lastName: String,
    fatherName: String,
    emailAddress: String,
    password: String,
    contactNo: String,
    gender: String,
    profileImageUrl: String,
    role : String,
    context: Context,
    navController: NavController
) {
    val auth = FirebaseAuth.getInstance()
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    val dbUsers: CollectionReference = db.collection("users")

    // Existing code...

    auth.createUserWithEmailAndPassword(emailAddress, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val userId = auth.currentUser?.uid

                // Upload the profile image to Firebase Storage
                uploadProfileImageToStorage(userId, profileImageUrl, context) { imageUrl ->
                    // User created successfully, now add user details to Firestore
                    val user = User(
                        firstName,
                        lastName,
                        fatherName,
                        emailAddress,
                        password,
                        contactNo,
                        gender,
                        imageUrl,
                        role
                    )

                    // Add user data to Firestore
                    dbUsers.document(userId!!)
                        .set(user)
                        .addOnSuccessListener {
                            Toast.makeText(
                                context,
                                "User Registered",
                                Toast.LENGTH_SHORT
                            ).show()

                            // Navigate to ChooseYourselfScreen after successful registration
                            navController.navigate(ChooseYourselfScreen.route)
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(
                                context,
                                "Failed to add user: $e",
                                Toast.LENGTH_SHORT
                            ).show()

                            // Navigate to ChooseYourselfScreen on failure as well (if needed)
                        }
                }
            } else {
                Toast.makeText(
                    context,
                    "User registration failed: ${task.exception?.message}",
                    Toast.LENGTH_SHORT
                ).show()

                // Navigate to ChooseYourselfScreen on failure
            }
        }

}

private fun uploadProfileImageToStorage(userId: String?, profileImageUrl: String, context: Context, callback: (String) -> Unit) {
    if (userId != null && profileImageUrl.isNotBlank()) {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference.child("profile_images").child("$userId.jpg")

        val imageUri = Uri.parse(profileImageUrl)

        storageRef.putFile(imageUri)
            .addOnSuccessListener { _ ->
                // Image uploaded successfully, get the download URL
                storageRef.downloadUrl
                    .addOnSuccessListener { uri ->
                        callback(uri.toString())
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            context,
                            "Failed to upload profile image: $it",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
            }
            .addOnFailureListener {
                Toast.makeText(
                    context,
                    "Failed to upload profile image: $it",
                    Toast.LENGTH_SHORT
                ).show()
            }
    } else {
        callback("")  // Return empty string if no image to upload
    }
}

class RegistrationViewModel : ViewModel() {
    val firstName = mutableStateOf("")
    val lastName = mutableStateOf("")
    val fatherName = mutableStateOf("")
    val emailAddress = mutableStateOf("")
    val password = mutableStateOf("")
    val contactNo = mutableStateOf("")
    val gender = mutableStateOf("")
    val profileImageUrl = mutableStateOf("")
    val role = mutableStateOf("")


    // Add more functions to update other fields as needed
}