package com.example.safetytips

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.fypdisastermanagement.Campaigns.CampaignInfo
import com.example.fypdisastermanagement.R
import com.example.fypdisastermanagement.campaignview.fetchAllCampaignsInfo
import com.example.fypdisastermanagement.fundraisers.FundraiserInfo
import com.example.fypdisastermanagement.fundraisers.fetchAllFundraisersInfo
import com.example.fypdisastermanagement.fundraisers.fetchTotalDonationAmount
import com.example.fypdisastermanagement.homescreen.fetchLimitedCampaignsInfo
import com.example.fypdisastermanagement.homescreen.fetchLimitedFundraisersInfo

@Composable
fun AddNewCampaign(navController: NavController) {
    Card(
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp)
            .height(110.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(CornerSize(15.dp)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.5.dp
        )
    ) {
        Box(
            modifier = Modifier
                .background(Color(0XFFD34040))
                .padding(start = 15.dp, end = 15.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp)
            ) {
                Text(
                    text = "Start New\nCampaigns",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.sfprodisplaybold)),
                        fontSize = 17.sp,
                        color = Color.White,
                        letterSpacing = 1. sp,
                        lineHeight = 25.sp,
                    ),
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { /* TODO */ },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(Color.White),
                    modifier = Modifier.width(120.dp).height(50.dp)
                ) {
                    Text(
                        text = "Start now",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.poppinssemibold)),
                            fontSize = 14.sp,
                            color = Color(0xFFD34040),
                        ),
                        modifier = Modifier.clickable { navController.navigate(com.example.fypdisastermanagement.destinations.SubmitCampaign.route) }
                    )
                }
            }
        }
    }
}
