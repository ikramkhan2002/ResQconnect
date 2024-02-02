package com.example.fypdisastermanagement.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fypdisastermanagement.R
import com.example.fypdisastermanagement.destinations.Earthquake
import com.example.fypdisastermanagement.destinations.Fires
import com.example.fypdisastermanagement.destinations.Floods
import com.example.fypdisastermanagement.destinations.SafetyTips
import com.example.fypdisastermanagement.destinations.SubmitFundraiser

@Composable
fun SafetyTipsOptions(navController: NavController) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Card(
                modifier = Modifier
                    .clickable { navController.navigate(Earthquake.route) }
                    .height(65.dp)
                    .width(70.dp),
                shape = RoundedCornerShape(CornerSize(15.dp)),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                )
            ) {
                Box(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.eartquakeicon),
                        contentDescription = "null",
                        modifier = Modifier
                            .size(35.dp),
                        contentScale = ContentScale.Crop,
                    )
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Earthquake",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                    fontSize = 12.sp,
                    color = Color(0xFFEA7970).copy(alpha= 0.8f)
                )
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Card(
                modifier = Modifier
                    .clickable { navController.navigate(Floods.route) }
                    .height(65.dp)
                    .width(70.dp),
                shape = RoundedCornerShape(CornerSize(15.dp)),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                )
            ) {
                Box(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.floodicon),
                        contentDescription = "null",
                        modifier = Modifier
                            .size(35.dp),
                        contentScale = ContentScale.Crop,
                    )
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Flood",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                    fontSize = 12.sp,
                    color = Color(0xFFEA7970).copy(alpha= 0.8f)
                )
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Card(
                modifier = Modifier
                    .clickable { navController.navigate(Fires.route) }
                    .height(65.dp)
                    .width(70.dp),
                shape = RoundedCornerShape(CornerSize(15.dp)),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                )
            ) {
                Box(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.fireicon),
                        contentDescription = "null",
                        modifier = Modifier
                            .size(35.dp),
                        contentScale = ContentScale.Crop,
                    )
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Fires",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                    fontSize = 12.sp,
                    color = Color(0xFFEA7970).copy(alpha= 0.8f)
                )
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Card(
                modifier = Modifier
                    .clickable { navController.navigate(SafetyTips.route) }
                    .height(65.dp)
                    .width(70.dp),
                shape = RoundedCornerShape(CornerSize(15.dp)),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                )
            ) {
                Box(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.alerticon),
                        contentDescription = "null",
                        modifier = Modifier
                            .size(30.dp),
                        contentScale = ContentScale.Crop,
                    )
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "More",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                    fontSize = 12.sp,
                    color = Color(0xFFEA7970).copy(alpha= 0.8f)
                )
            )
        }
    }
}