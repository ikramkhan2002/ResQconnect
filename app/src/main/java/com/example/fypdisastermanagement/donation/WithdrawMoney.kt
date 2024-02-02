package com.example.fypdisastermanagement.donation

import android.graphics.Paint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults

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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fypdisastermanagement.R
import com.example.fypdisastermanagement.destinations.MyFundraiserView
import com.example.fypdisastermanagement.destinations.MyFundraisers
import com.example.fypdisastermanagement.destinations.RescuerHome
import com.example.fypdisastermanagement.destinations.RescuerLandingScreen
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun  withdrawMoney(navController: NavController,fundraiserTitle: String) {

    var isWithdrawDialogVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),

    ) {
        withdrawHeader(navController)
        Spacer(modifier = Modifier.height(40.dp))
        var currentBalance by remember { mutableStateOf("0") }

        var totalDonationAmount by remember { mutableStateOf(0) } // Track total donation amount
        Box(
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
                .background(color = Color.White)
                .border(BorderStroke(width = 1.dp, color = Color(0XFFE2E2E9))),
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 20.dp, start = 20.dp)
            ) {
                Text(
                    text = "Current Balance",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                        fontSize = 18.sp,
                        color = Color(0xFF000000),
                    ),
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Available to transfer",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
                        fontSize = 14.sp,
                        color = Color(0xFF808080)
                    )
                )
                Spacer(modifier = Modifier.height(22.dp))
                Text(
                    text = "Rs.$totalDonationAmount",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                        fontSize = 24.sp,
                        color = Color(0xFF000000),
                    )
                )
            }
        }

        LaunchedEffect(Unit) {
            try {
                val donationsCollection = firestore.collection("donations")
                val donationsQuerySnapshot = donationsCollection
                    .whereEqualTo("fundraiserName", fundraiserTitle) // Filter donations by fundraiser title
                    .get().await()

                // Calculate the sum of donationAmounts for this specific fundraiser
                totalDonationAmount = donationsQuerySnapshot.documents.sumBy { document ->
                    val donationAmount = document.getString("donationAmount")?.toIntOrNull() ?: 0
                    donationAmount
                }


                // Existing code...
            } catch (e: Exception) {
                // Handle any potential errors here
                e.printStackTrace()
            }
        }
        Spacer(modifier = Modifier.height(25.dp))
        val accountNumber = remember { mutableStateOf("") }
        val bankName = remember { mutableStateOf("") }
        val withdrawAmount = remember { mutableStateOf("") }
        val withdrawAmountValue = withdrawAmount.value.toIntOrNull() ?: 0
        Text(
            text = "Bank Details",
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                fontSize = 16.sp,
                color = Color(0xFF000000),
            ),
            modifier = Modifier
                .padding(start = 20.dp)

        )
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .height(330.dp)
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
                .clip(RoundedCornerShape(0.dp))
                .background(color = Color.White)
                .border(BorderStroke(width = 1.dp, color = Color(0XFFE2E2E9))),
        ) {
            Column(modifier = Modifier.padding(top = 20.dp)) {
                Text(
                    text = "Account Number",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                        fontSize = 14.sp,
                        color = Color(0xFF000000),
                    ),
                    modifier = Modifier
                        .padding(start = 20.dp)

                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = accountNumber.value,
                    onValueChange = { accountNumber.value = it },
                    placeholder = {
                        Text(
                            text = "Account Number",
                            fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
                            fontSize = 14.sp,
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
                Text(
                    text = "Bank Name",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                        fontSize = 14.sp,
                        color = Color(0xFF000000),
                    ),
                    modifier = Modifier
                        .padding(start = 20.dp)

                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = bankName.value,
                    onValueChange = { bankName.value = it },
                    placeholder = {
                        Text(
                            text = "Bank Name",
                            fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
                            fontSize = 14.sp,
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
                )
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "Withdraw Amount",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                        fontSize = 14.sp,
                        color = Color(0xFF000000),
                    ),
                    modifier = Modifier
                        .padding(start = 20.dp)

                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = withdrawAmount.value,
                    onValueChange = { withdrawAmount.value = it },
                    placeholder = {
                        Text(
                            text = "Withdraw Amount",
                            fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
                            fontSize = 14.sp,
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
                )
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
        Button(
            onClick = {
                val data = hashMapOf(
                    "accountNumber" to accountNumber.value,
                    "bankName" to bankName.value,
                    "withdrawAmount" to withdrawAmount.value
                )

                // Add a new document with a generated ID
                firestore.collection("withdrawals")
                    .add(data)
                    .addOnSuccessListener { documentReference ->
                        // Document has been added successfully
                        // You can display a success message or navigate to a different screen
                        isWithdrawDialogVisible = true
                        // Deduct the withdrawal amount from the total donation amount
                        totalDonationAmount -= withdrawAmountValue
                        // Update donationAmount in the donations collection
                        val donationCollection = firestore.collection("donations")
                        donationCollection
                            .whereEqualTo("fundraiserName", fundraiserTitle) // Filter donations by fundraiser title
                            .get()
                            .addOnSuccessListener { documents ->
                                for (document in documents) {
                                    val donationDocRef = donationCollection.document(document.id)
                                    val currentDonationAmount = document.getString("donationAmount")?.toIntOrNull() ?: 0
                                    val updatedDonationAmount = currentDonationAmount - withdrawAmountValue

                                    // Update the donationAmount in the document
                                    donationDocRef.update("donationAmount", updatedDonationAmount.toString())
                                }
                            }
                            .addOnFailureListener { e ->
                                // Handle any errors here
                                // You can display an error message to the user
                            }

                    }
                    .addOnFailureListener { e ->
                        // Handle any errors here
                        // You can display an error message to the user

                    }
                isWithdrawDialogVisible = true
            },
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp),
            shape = RoundedCornerShape(15),
            colors = ButtonDefaults.buttonColors(Color(0xFFD34040)),
        ) {
            Text(
                text = "Withdraw",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.robotomedium)),
                    fontSize = 20.sp,
                    color = Color.White
                )
            )
        }

        if (isWithdrawDialogVisible) {
            WithdrawSuccessDialog(onDismiss = { isWithdrawDialogVisible = false }, navController)
        }

        Spacer(modifier = Modifier.height(50.dp))
        Column(modifier = Modifier
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
fun withdrawHeader(navController: NavController) {
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
                        .clickable { navController.navigate(MyFundraiserView.route) }
                )
            }
            Spacer(modifier = Modifier.width(100.dp))
            Text(
                text = "Withdraw",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                    fontSize = 20.sp,
                    color = Color(0xFF000000),
                ),
            )
        }
    }
}



@Composable
fun WithdrawSuccessDialog(
    onDismiss: () -> Unit,
    navController: NavController
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Card(
            modifier = Modifier
                .height(300.dp)
                .width(340.dp),
            shape = RoundedCornerShape(CornerSize(10.dp)),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
        )
        {
            Box(
                modifier = Modifier
                    .width(340.dp)
                    .height(300.dp)
                    .background(Color.White),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .background(Color.White, CircleShape)
                            .clip(RoundedCornerShape(CornerSize(10.dp)))
                            .border(1.dp, Color(0XFFD34040), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.check),
                            modifier = Modifier.size(40.dp),
                            contentDescription = "Check",
                            tint = Color(0XFFD34040),
                        )
                    }
                    Spacer(modifier = Modifier.height(18.dp))
                    Text(
                        text = "Withdraw Successful",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.robotomedium)),
                            fontSize = 22.sp,
                            color = Color(0xFF000000)
                        )
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                    Text(
                        text = "The withdraw has been successfully applied and the payment will be made within 5 to 8 days.",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.robotoregular)),
                            fontSize = 16.sp,
                            color = Color(0xFF000000)
                        ),
                       modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = {
                            // Handle button click here
                        },
                        shape = RoundedCornerShape(15),
                        colors = ButtonDefaults.buttonColors(Color(0xFFD34040)),
                        modifier = Modifier
                            .height(48.dp)
                            .fillMaxWidth()
                            .padding(start = 30.dp, end = 30.dp)
                            .clickable { navController.navigate(MyFundraisers.route) },
                    ) {
                        Text(
                            text = "Continue",
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.robotomedium)),
                            color = Color(0xFFFFFFFF),
                            modifier = Modifier.clickable { navController.navigate(RescuerLandingScreen.route) }
                        )
                    }
                }
            }
        }
    }
}
@Composable
@Preview
fun withdrawwprev(){
    val fundraiser :String =""
    withdrawMoney(navController= rememberNavController(), fundraiserTitle = fundraiser)
}


