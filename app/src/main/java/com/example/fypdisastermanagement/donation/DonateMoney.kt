package com.example.fypdisastermanagement.donation

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.material3.*
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.fypdisastermanagement.R
import com.example.fypdisastermanagement.destinations.LoginScreen
import com.example.fypdisastermanagement.destinations.SuccessDonated
import com.example.fypdisastermanagement.fundraisers.FundraiserInfo
import com.example.fypdisastermanagement.fundraisers.fetchFundraiserDetailsFromFirebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

val firestore = Firebase.firestore
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun  DonateMoney(navController: NavController,paymentMethodViewModel: PaymentMethodViewModel,fundraiserTitle: String) {
    val donationAmount  = remember { mutableStateOf("") }
    val donationDisplayName  = remember { mutableStateOf("") }
    val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())

    ){
        header(navController)
        Spacer(modifier = Modifier.height(50.dp))
        Fundraiser(fundraiserTitle)
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "Donation Amount",
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                fontSize = 16.sp,
                color = Color(0xFF000000),
            ),
            modifier = Modifier
                .padding(start = 20.dp)

        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = donationAmount.value,
            onValueChange = {donationAmount.value = it },
            leadingIcon = {
                Text(text = "Rs.")
            },
            trailingIcon = {
                Text(text = "PKR", color = Color(0XFFD34040))
            },
            singleLine = true,
            shape = RoundedCornerShape(15),
            modifier = Modifier
                .height(55.dp)
                .fillMaxWidth()
                .background(
                    color = Color(0xFFFFFFFF)
                )
                .padding(start = 20.dp, end = 20.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = Color(0xFFD34040),
                focusedBorderColor = Color(0xFFD34040),
                unfocusedBorderColor = Color(0xFFE2E2E9)
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
        )
        Spacer(modifier = Modifier.height(25.dp))
        PaymentMethod(paymentMethodViewModel)
        Spacer(modifier = Modifier.height(25.dp))
        Text(
            text = "Donation Display Name",
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                fontSize = 16.sp,
                color = Color(0xFF000000),
            ),
            modifier = Modifier
                .padding(start = 20.dp)

        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = donationDisplayName.value,
            onValueChange = {donationDisplayName.value = it },
            placeholder = {
                Text(
                    text = "Full Name",
                    fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
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
                )
                .padding(start = 20.dp, end = 20.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = Color(0xFFD34040),
                focusedBorderColor = Color(0xFFD34040),
                unfocusedBorderColor = Color(0xFFE2E2E9)
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
        )
        Spacer(modifier = Modifier.height(35.dp))
        Divider(
            thickness = 1.5.dp,
            color = Color(0xFF707070).copy(alpha = 0.16f),
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Total Payment",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                    fontSize = 16.sp,
                    color = Color(0xFF000000),
                ),
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Rs. " + donationAmount.value,
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                    fontSize = 16.sp,
                    color = Color(0xFF000000),
                ),
                modifier = Modifier.padding(end = 20.dp)
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
        val context = LocalContext.current
        Button(
            onClick = {
                // Create a Donation object using data from paymentMethodViewModel and other fields
                val donation = Donation(
                    donationAmount = donationAmount.value,
                    donationDisplayName = donationDisplayName.value,
                    cardNumber = paymentMethodViewModel.cardNumber,
                    expirationDate = paymentMethodViewModel.expirationDate,
                    cvv = paymentMethodViewModel.cvv,
                    nameOnCard = paymentMethodViewModel.nameOnCard,
                    fundraiserName = fundraiserTitle,
                    currentUid=currentUserUid
                    // Add more fields as needed
                )


                // Save the donation data to Firebase Firestore
                firestore.collection("donations")
                    .add(donation)
                    .addOnSuccessListener {
                        // Handle the success, e.g., show a confirmation message
                    }
                    .addOnFailureListener {
                        // Handle the failure, e.g., show an error message
                    }
                Toast.makeText(context,"Donated",Toast.LENGTH_SHORT).show()
                CoroutineScope(Dispatchers.Main).launch {
                    navigateBasedOnRole(navController)
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
                text = "Donate Now",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.robotomedium)),
                    fontSize = 20.sp,
                    color = Color.White
                ),
               // modifier = Modifier.clickable { navController.navigate(SuccessDonated.route) }
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
        Column(
            modifier = Modifier
            .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .height(6.dp)
                    .width(140.dp)
                    .background(color = Color(0xFF000000), shape = RoundedCornerShape(100)),
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun header(navController: NavController) {
    Surface(
        shadowElevation = 0.5.dp
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically,

        ) {
            IconButton(
                onClick = { /* Handle back button click */ },
                modifier = Modifier
            )
            {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "back",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(32.dp)
                        .padding(start = 5.dp)
                        .clickable { navController.navigate("FundraisersView") }
                )
            }
            Spacer(modifier = Modifier.width(100.dp))
            Text(
                text = "Donate",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                    fontSize = 20.sp,
                    color = Color.Black,
                ),
            )
        }
    }
}

@Composable
fun Fundraiser(fundraiserTitle: String) {
    var fundraiserHeading by rememberSaveable { mutableStateOf("Help me raise\nRs.50,000 to build\nthe house for the victims") }
    var fundraiserImage by rememberSaveable { mutableStateOf((R.drawable.fundraisersheader)) }
    var selectedFundraiser: FundraiserInfo? by remember { mutableStateOf(null) }


    LaunchedEffect(Unit) {
        selectedFundraiser = fetchFundraiserDetailsFromFirebase(fundraiserTitle)
    }

    selectedFundraiser?.let { fundraiser ->
        Box(
            modifier = Modifier
                .background(Color.White)
                .clickable {}
        )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
            )
            {
                Image(
                    painter = rememberImagePainter(data = fundraiser.fundraiserImageUrl),
                    contentDescription = "null",
                    modifier = Modifier
                        .width(140.dp)
                        .height(90.dp),
                    contentScale = ContentScale.Crop,
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = if (fundraiser.fundraiserTitle.length > 50) "${fundraiser.fundraiserTitle.take(50)}.." else fundraiser.fundraiserTitle,
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                        fontSize = 16.sp,
                        color = Color(0xFF000000),
                    )
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Divider(
            thickness = 1.5.dp,
            color = Color(0xFF707070).copy(alpha = 0.16f),
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentMethod(paymentMethodViewModel: PaymentMethodViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Payment Method",
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                fontSize = 16.sp,
                color = Color(0xFF000000),
            ),
            modifier = Modifier.padding(start = 20.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = paymentMethodViewModel.cardNumber,
            onValueChange = { paymentMethodViewModel.cardNumber = it },
            placeholder = {
                Text(
                    text = "Card Number",
                    fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
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
                )
                .padding(start = 20.dp, end = 20.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = Color(0xFFD34040),
                focusedBorderColor = Color(0xFFD34040),
                unfocusedBorderColor = Color(0xFFE2E2E9)
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
        )
        Spacer(modifier = Modifier.height(14.dp))
        OutlinedTextField(
            value = paymentMethodViewModel.expirationDate,
            onValueChange = { paymentMethodViewModel.expirationDate = it },
            placeholder = {
                Text(
                    text = "MM/YY",
                    fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
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
                )
                .padding(start = 20.dp, end = 20.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = Color(0xFFD34040),
                focusedBorderColor = Color(0xFFD34040),
                unfocusedBorderColor = Color(0xFFE2E2E9)
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
        )
        Spacer(modifier = Modifier.height(14.dp))
        OutlinedTextField(
            value = paymentMethodViewModel.cvv,
            onValueChange = { paymentMethodViewModel.cvv = it },
            placeholder = {
                Text(
                    text = "CVV",
                    fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
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
                )
                .padding(start = 20.dp, end = 20.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = Color(0xFFD34040),
                focusedBorderColor = Color(0xFFD34040),
                unfocusedBorderColor = Color(0xFFE2E2E9)
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
        )
        Spacer(modifier = Modifier.height(14.dp))
        OutlinedTextField(
            value = paymentMethodViewModel.nameOnCard,
            onValueChange = { paymentMethodViewModel.nameOnCard = it },
            placeholder = {
                Text(
                    text = "Name On Card",
                    fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
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
                )
                .padding(start = 20.dp, end = 20.dp),
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

data class Donation(
    val donationAmount: String,
    val donationDisplayName: String,
    val cardNumber: String, // Add payment method fields
    val expirationDate: String,
    val cvv: String,
    val nameOnCard: String,
    val fundraiserName: String,
    val currentUid:String

    // Add more fields as needed
)
class PaymentMethodViewModel : ViewModel() {
    var cardNumber by mutableStateOf("")
    var expirationDate by mutableStateOf("")
    var cvv by mutableStateOf("")
    var nameOnCard by mutableStateOf("")
    // Add more payment method fields as needed
}

@Composable
@Preview
fun donatePrev(){
    val paymentMethodViewModel = PaymentMethodViewModel()

    val fundraiserTitle = "fundraiserTitle"

    DonateMoney(navController = rememberNavController(),paymentMethodViewModel, fundraiserTitle)
}