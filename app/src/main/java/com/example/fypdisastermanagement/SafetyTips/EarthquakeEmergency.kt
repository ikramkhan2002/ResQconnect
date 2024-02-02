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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fypdisastermanagement.R
import com.example.fypdisastermanagement.destinations.SafetyTips

@Composable
fun Earthquake(navController: NavController) {
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
                painter = painterResource(R.drawable.earthquakeheader),
                contentDescription = "null",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Earthquake Safety Tips",
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
            text = "Stay safe and prepared with essential earthquake safety tips for yourself and loved ones.",
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
                painter = painterResource(id = R.drawable.earthquakesafety1),
                contentDescription = "Visibility Icon",
                modifier = Modifier.size(120.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "During an earthquake, take cover under a sturdy object to protect yourself from falling objects and debris.",
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
                painter = painterResource(id = R.drawable.earthquakesafety22),
                contentDescription = "Visibility Icon",
                modifier = Modifier.size(120.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "If you are driving stop in a safe place, turn on the hazard lights light and stay inside the vehicle  until the shaking stops.",
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
                painter = painterResource(id = R.drawable.earthquakesafety3),
                contentDescription = "Visibility Icon",
                modifier = Modifier.size(120.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "During an earthquake, opt for stairs not elevators for safer evacuation. Elevators may pose risks during seismic activity.",
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
                painter = painterResource(id = R.drawable.earthquakesafety4),
                contentDescription = "Visibility Icon",
                modifier = Modifier.size(120.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Stay away from tall buildings during and after an earthquake due to the risk of collapse. Seek open spaces for safety.",
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
                text = "Prepare earthquake emergency kit that includes essential items such as water, canned food, flashlight and medications.",
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












