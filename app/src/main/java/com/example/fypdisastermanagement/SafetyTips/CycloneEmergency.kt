package com.example.safetytips

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fypdisastermanagement.R
import com.example.fypdisastermanagement.destinations.SafetyTips

@Composable
fun Cyclone(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
    )
    {
        Spacer(modifier = Modifier.height(14.dp))
        Row(
            modifier = Modifier
                .padding(start = 12.dp)
        )
        {
            Button(
                onClick = {
                    // Handle button click here
                },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(Color(0xFF808080).copy(0.1f)),
                modifier = Modifier
                    .size(40.dp)
                    .clickable { navController.navigate(SafetyTips.route) },
                contentPadding = PaddingValues(0.dp)


                ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrowback),
                        modifier = Modifier
                            .size(20.dp),
                        contentDescription = "Icon-Back",
                        tint = Color.Black,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp)
                .clip(RoundedCornerShape(5))
                .background(Color.White)

        )
        {
            Image(
                painter = painterResource(R.drawable.cyclone1),
                contentDescription = "null",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Cyclone Safety Tips",
            modifier = Modifier
                .padding(start = 20.dp),
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.sfprodisplaybold)),
                fontSize = 22.sp,
                color = Color(0xFF000000)
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Stay safe and prepared with essential cyclone safety tips for yourself and loved ones.",
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp),
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
                fontSize = 14.sp,
                color = Color(0xFF000000).copy(0.6f),
            )
        )
        Spacer(modifier = Modifier.height(14.dp))
        Box(
            modifier = Modifier
                .height(1.5.dp)
                .padding(start = 20.dp, end = 20.dp)
                .fillMaxWidth()
                .background(color = Color(0xFF707070).copy(alpha = 0.16f))
        )
        Spacer(modifier = Modifier.height(25.dp))
        Text(
            text = "Essential Activities",
            modifier = Modifier
                .padding(start = 20.dp),
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                fontSize = 18.sp,
                color = Color(0xFF000000)
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .padding(start = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.cyclonesafety1),
                contentDescription = "Visibility Icon",
                modifier = Modifier.size(120.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Keep an eye on weather updates and warnings. Stay up-to-date with information on cyclone's path and intensity.",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.poppinsregular)),
                    fontSize = 12.sp,
                    color = Color(0xFF000000).copy(0.6f),
                    textAlign = TextAlign.Justify
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 14.dp),
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .height(1.5.dp)
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
                .background(color = Color(0xFF707070).copy(alpha = 0.18f))
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier
                .padding(start = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.cyclonesafety2),
                contentDescription = "Visibility Icon",
                modifier = Modifier.size(120.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "If instructed by authorities or if you experience flooding, turn off gas, electricity, and water supplies to prevent accidents.",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.poppinsregular)),
                    fontSize = 12.sp,
                    color = Color(0xFF000000).copy(0.6f),
                    textAlign = TextAlign.Justify
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 14.dp),
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .height(1.5.dp)
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
                .background(color = Color(0xFF707070).copy(alpha = 0.18f))
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier
                .padding(start = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.cyclonesafety3),
                contentDescription = "Visibility Icon",
                modifier = Modifier.size(120.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Avoid going outside during the cyclone, as flying debris and strong winds can pose a severe risk to your safety.",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.poppinsregular)),
                    fontSize = 12.sp,
                    color = Color(0xFF000000).copy(0.6f),
                    textAlign = TextAlign.Justify
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 14.dp),
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .height(1.5.dp)
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
                .background(color = Color(0xFF707070).copy(alpha = 0.18f))
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier
                .padding(start = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.cyclonesafety4),
                contentDescription = "Visibility Icon",
                modifier = Modifier.size(120.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "If you're able and it's safe, check on your neighbors, especially the elderly and vulnerable, to see if they need assistance.",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.poppinsregular)),
                    fontSize = 12.sp,
                    color = Color(0xFF000000).copy(0.6f),
                    textAlign = TextAlign.Justify
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 14.dp),
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .height(1.5.dp)
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
                .background(color = Color(0xFF707070).copy(alpha = 0.18f))
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier
                .padding(start = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.earthquakesafety5),
                contentDescription = "Visibility Icon",
                modifier = Modifier.size(120.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Prepare an emergency kit that includes essential items such as water, canned food, flashlight and medications.",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.poppinsregular)),
                    fontSize = 12.sp,
                    color = Color(0xFF000000).copy(0.6f),
                    textAlign = TextAlign.Justify
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 14.dp),
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .height(1.5.dp)
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
                .background(color = Color(0xFF707070).copy(alpha = 0.18f))
        )
        Spacer(modifier = Modifier.height(40.dp))






    }
}