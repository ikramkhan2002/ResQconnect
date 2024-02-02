package com.example.fypdisastermanagement.SafetyTips

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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

@Composable
fun Fires(navController: NavController) {
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
                    .size(40.dp),
                contentPadding = PaddingValues(0.dp),

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
                painter = painterResource(R.drawable.fireheader),
                contentDescription = "null",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Fire Safety Tips",
            modifier = Modifier
                .padding(start = 20.dp),
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.sfprodisplaybold)),
                fontSize = 22.sp,
                color = Color(0xFF000000)
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Learn how to protect yourself and your loved ones from fire hazards with the essential guidelines.",
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
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
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
                painter = painterResource(id = R.drawable.firesafety1),
                contentDescription = "Visibility Icon",
                modifier = Modifier.size(120.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "If you spot a fire or smoke, take immediate action, activate the fire alarm to alert others and evacuate immediately.",
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
                painter = painterResource(id = R.drawable.firesafety2),
                contentDescription = "Visibility Icon",
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "During a fire evacuation, it's essential to cover your nose and mouth with cloth to filter smoke during evacuation.",
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
                painter = painterResource(id = R.drawable.firesafety3),
                contentDescription = "Visibility Icon",
                modifier = Modifier.size(120.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "If you find yourself trapped during a fire emergency try attract attention for help by using signals or a flashlight.",
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
                painter = painterResource(id = R.drawable.firesafety4),
                contentDescription = "Visibility Icon",
                modifier = Modifier.size(120.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Avoid elevators in a fire. Use stairs for safer evacuation. Elevators can be dangerous hinder rescue efforts.",
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
                painter = painterResource(id = R.drawable.firesafety5),
                contentDescription = "Visibility Icon",
                modifier = Modifier.size(120.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Call emergency services with clear and concise information about the location to ensure a quick response.",
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