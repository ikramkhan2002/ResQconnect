package com.example.myprofile

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.fypdisastermanagement.R
import com.example.fypdisastermanagement.createaccount.auth
import com.example.fypdisastermanagement.individualprofile.ProfileHeader
import com.example.fypdisastermanagement.individualprofile.ProfileNames
import com.example.fypdisastermanagement.individualprofile.User
import com.example.fypdisastermanagement.individualprofile.fetchUserInfoFromFirebase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

// Assuming you have Firebase initialized in your app
class ProfileViewModel : ViewModel() {
    // Existing code...

    // LiveData to hold user data
    data class UserData(
        val username: String,
        val role: String,
        val email: String,
        val password: String,
        val contactNo: String,
        val profileImageUrl: String // Add this field for profile image URL
    )


    private val _userData = MutableLiveData<UserData>()
    private val db = FirebaseFirestore.getInstance()
    val userData: LiveData<UserData> get() = _userData

    init {
        viewModelScope.launch {
            fetchUserData()
        }
    }
    // Change visibility to internal or public
    suspend fun fetchUserData() {
        val currentUser = auth.currentUser
        currentUser?.let { user ->
            val usersCollection = db.collection("users")
            val userDocument = usersCollection.document(user.uid)

            userDocument.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val firstName = document.getString("firstName") ?: ""
                        val lastName = document.getString("lastName") ?: ""
                        val role = document.getString("role") ?: ""
                        val email = document.getString("emailAddress") ?: ""
                        val password = document.getString("password") ?: ""
                        val contactNo = document.getString("contactNo") ?: ""
                        val profileImageUrl = document.getString("profileImageUrl") ?: ""

                        val combinedUsername = "$firstName $lastName"
                        val userData = UserData(combinedUsername, role, email, password, contactNo, profileImageUrl)
                        _userData.value = userData
                    } else {
                        val defaultData = UserData("Default Username", "Default Role", "Default Email", "Default Password", "Default Contact No", "")
                        _userData.value = defaultData
                    }
                }
                .addOnFailureListener { exception ->
                    // Handle failure here
                    val defaultData = UserData("Default Username", "Default Role", "Default Email", "Default Password", "Default Contact No", "")
                    _userData.value = defaultData
                }
        }
    }
    // Function to update username (combination of first name and last name) and contact number in Firebase
    suspend fun updateUsernameAndContact(firstName: String, lastName: String, contactNo: String) {
        val currentUser = auth.currentUser
        currentUser?.let { user ->
            val usersCollection = db.collection("users")
            val userDocument = usersCollection.document(user.uid)

            // Update both the firstName, lastName, and contactNo fields in the document
            userDocument.update(
                "firstName", firstName,
                "lastName", lastName,
                "contactNo", contactNo
            ).addOnFailureListener { exception ->
                // Handle failure here
                // You may want to show an error message to the user
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    val profileViewModel: ProfileViewModel = viewModel()

    // Mutable state to hold user data
    var userData by remember { mutableStateOf(
        ProfileViewModel.UserData(
            "Default Username",
            "Default Role",
            "Default Email",
            "Default Password",
            "Default Contact No",
            ""
        )
    ) }

    DisposableEffect(key1 = profileViewModel) {
        // Fetch user data from the ViewModel
        profileViewModel.viewModelScope.launch {
            // Fetch user data from the ViewModel
            profileViewModel.fetchUserData()
        }

        // Observe the LiveData for user data changes
        val userDataLive = profileViewModel.userData
        val observer = Observer<ProfileViewModel.UserData> { newData ->
            userData = newData ?: ProfileViewModel.UserData(
                "Default Username",
                "Default Role",
                "Default Email",
                "Default Password",
                "Default Contact No",
                ""
            )
        }

        userDataLive.observeForever(observer)

        onDispose {
            userDataLive.removeObserver(observer)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
    ) {

        MyProfileHeader(
            profileImageUrl = userData.profileImageUrl
        )
        Spacer(modifier = Modifier.height(100.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = userData.username,
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.sfprodisplaybold)),
                    fontSize = 24.sp,
                    color = Color.Black
                )
            )
            Text(
                text = userData.role,
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                    fontSize = 18.sp,
                    color = Color(0XFFD34040)
                )
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        TextFields(userData = userData) { updatedUserData ->
            userData = updatedUserData
        }
        Spacer(modifier = Modifier.height(40.dp))
        val context = LocalContext.current
        Button(
            onClick = {
                // Split the username into first name and last name
                val names = userData.username.split(" ")
                val firstName = names.firstOrNull() ?: ""
                val lastName = names.drop(1).joinToString(" ")

                // Call the updateUsernameAndContact function with the modified data
                profileViewModel.viewModelScope.launch {
                    profileViewModel.updateUsernameAndContact(firstName, lastName, userData.contactNo)
                    // Fetch user data from the ViewModel after the update
                    profileViewModel.fetchUserData()
                    Toast.makeText(
                        context,
                        "Updated",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(Color(0xFFD34040)),
        ) {
            Text(
                text = "Save",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.robotomedium)),
                    fontSize = 20.sp,
                    color = Color.White
                )
            )
        }
        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
fun MyProfileHeader(profileImageUrl: String) {
    var profileImage by rememberSaveable { mutableStateOf(R.drawable.profileimage) }
    val painter = rememberImagePainter(profileImageUrl)

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
            painter = painter,
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




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFields(userData: ProfileViewModel.UserData, onUserDataChange: (ProfileViewModel.UserData) -> Unit) {
    var passwordVisibility by remember { mutableStateOf(false) }

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val icon =
        if (passwordVisibility)
            painterResource(id = R.drawable.passwordvisibility)
        else
            painterResource(id = R.drawable.passwordvisibilityoff)

    Text(
        text = "Your Email",
        modifier = Modifier
            .padding(start = 30.dp),
        style = TextStyle(
            fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
            fontSize = 16.sp,
            color = Color(0xFF808080)
        )
    )
    Spacer(modifier = Modifier.height(8.dp))
    OutlinedTextField(
        value = userData.email,
        onValueChange = {
            onUserDataChange(userData.copy(email = it))
        },
        singleLine = true,
        shape = RoundedCornerShape(50),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = Color(0xFFD34040),
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done
        ),
        modifier = Modifier
            .height(55.dp)
            .padding(start = 30.dp, end = 30.dp)
            .clip(RoundedCornerShape(40.dp))
            .fillMaxWidth()
            .background(color = Color(0xFFCCCCCC).copy(alpha = 0.4f))
    )
    Spacer(modifier = Modifier.height(12.dp))
    Text(
        text = "Password",
        modifier = Modifier
            .padding(start = 30.dp),
        style = TextStyle(
            fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
            fontSize = 16.sp,
            color = Color(0xFF808080)
        )
    )
    Spacer(modifier = Modifier.height(8.dp))
    OutlinedTextField(
        value = userData.password,
        onValueChange = {
            onUserDataChange(userData.copy(password = it))
        },
        singleLine = true,
        shape = RoundedCornerShape(50),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = Color(0xFFD34040),
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent
        ),
        trailingIcon = {
            if (isFocused) {
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
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        interactionSource = interactionSource,
        visualTransformation =
        if (passwordVisibility)
            VisualTransformation.None
        else
            PasswordVisualTransformation(),
        modifier = Modifier
            .height(55.dp)
            .padding(start = 30.dp, end = 30.dp)
            .clip(RoundedCornerShape(40.dp))
            .fillMaxWidth()
            .background(color = Color(0xFFCCCCCC).copy(alpha = 0.4f))
    )
    Spacer(modifier = Modifier.height(12.dp))
    Text(
        text = "Username",
        modifier = Modifier
            .padding(start = 30.dp),
        style = TextStyle(
            fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
            fontSize = 16.sp,
            color = Color(0xFF808080)
        )
    )
    Spacer(modifier = Modifier.height(8.dp))
    OutlinedTextField(
        value = userData.username,
        onValueChange = {
            onUserDataChange(userData.copy(username = it))
        },
        singleLine = true,
        shape = RoundedCornerShape(50),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = Color(0xFFD34040),
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        modifier = Modifier
            .height(55.dp)
            .padding(start = 30.dp, end = 30.dp)
            .clip(RoundedCornerShape(40.dp))
            .fillMaxWidth()
            .background(color = Color(0xFFCCCCCC).copy(alpha = 0.4f))
    )
    Spacer(modifier = Modifier.height(12.dp))
    Text(
        text = "Contact-No",
        modifier = Modifier
            .padding(start = 30.dp),
        style = TextStyle(
            fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
            fontSize = 16.sp,
            color = Color(0xFF808080)
        )
    )
    Spacer(modifier = Modifier.height(8.dp))
    OutlinedTextField(
        value = userData.contactNo,
        onValueChange = {
            onUserDataChange(userData.copy(contactNo = it))
        },
        singleLine = true,
        shape = RoundedCornerShape(50),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = Color(0xFFD34040),
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        modifier = Modifier
            .height(55.dp)
            .padding(start = 30.dp, end = 30.dp)
            .clip(RoundedCornerShape(40.dp))
            .fillMaxWidth()
            .background(color = Color(0xFFCCCCCC).copy(alpha = 0.4f))
    )
}
