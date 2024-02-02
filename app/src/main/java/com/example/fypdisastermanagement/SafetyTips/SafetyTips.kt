package com.example.safetytips

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fypdisastermanagement.R
import com.example.fypdisastermanagement.destinations.Cyclone
import com.example.fypdisastermanagement.destinations.Earthquake
import com.example.fypdisastermanagement.destinations.Fires
import com.example.fypdisastermanagement.destinations.Floods
import com.example.fypdisastermanagement.destinations.ForgotPassword

@Composable
fun SafetyTips(navController: NavController) {

    Box(Modifier.verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        )
        {
            Spacer(modifier = Modifier.height(15.dp))
            IconButton(onClick = { /* Handle back button click */ })
            {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier.padding(start = 12.dp)
                )
            }
           Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Safety Tips",
                modifier = Modifier
                    .padding(start = 20.dp),
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                    fontSize = 26.sp,
                    color = Color(0xFF000000)
                )
            )
            Spacer(modifier = Modifier.height(28.dp))
            Card(
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                ),
                modifier = Modifier.padding(start = 20.dp, end = 20.dp)

            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .background(Color(0XFFD34040).copy(0.1f))
                )
                {
                    Text(
                        text = " During a disaster emergency,\n plan evacuations, assemble\n an emergency kit, keep\n communication lines open,\n and stay informed.",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                            fontSize = 13.sp,
                            color = Color(0xFF000000),
                            lineHeight = 20.sp,
                            textAlign = TextAlign.Justify
                        ),
                        modifier = Modifier
                            .padding(start = 148.dp, top = 30.dp)
                    )
                    Text(
                        text = "Learn what else to do to keep\n your loved ones safe. ",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.sfprodisplaybold)),
                            fontSize = 13.sp,
                            color = Color(0xFF000000),
                            lineHeight = 20.sp,
                            textAlign = TextAlign.Justify
                        ),
                        modifier = Modifier
                            .padding(start = 148.dp,top = 145.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
            tipsForYou()
            Spacer(modifier = Modifier.height(35.dp))
            emergencies(navController)
            Spacer(modifier = Modifier.height(50.dp))
        }
        ImagePlacing()
    }

}
@Composable
fun ImagePlacing() {
        Image(
            painter = painterResource(R.drawable.safety),
            contentDescription = "Your Image",
            // contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .aspectRatio(18f / 9f)
                .offset(x = (-93).dp, y = 160.dp)

        )
    }
    @Composable
    fun tipsForYou() {
        Text(
            text = "Suggested",
            modifier = Modifier
                .padding(start = 20.dp),
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                fontSize = 20.sp,
                color = Color(0xFF000000)
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)

        )
        {
            Card(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(vertical = 8.dp)
                    .height(280.dp)
                    .background(Color.White)
                    .width(250.dp),
                shape = RoundedCornerShape(CornerSize(0.dp)),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 1.5.dp
                ),
            )
            {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White),

                        ) {
                        Image(
                            painter = painterResource(R.drawable.heat),
                            contentDescription = "null",
                            modifier = Modifier
                                .height(150.dp)
                                .fillMaxWidth()
                                .background(Color.White),
                            contentScale = ContentScale.Crop,
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 12.dp, top = 160.dp),
                            verticalAlignment = Alignment.CenterVertically

                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.sun),
                                contentDescription = "Visibility Icon",
                                modifier = Modifier.size(28.dp),
                                tint = Color(0xFFFF6600)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Heat Wave",
                                style = TextStyle(
                                    fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                                    fontSize = 20.sp,
                                    color = Color(0xFF000000)
                                )
                            )
                        }
                        Text(
                            text = "Stay hydrated and avoid outdoor activities during  peak heat hours.",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 200.dp, start = 18.dp, end = 22.dp),
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
                                fontSize = 16.sp,
                                color = Color(0xFF808080),
                                textAlign = TextAlign.Justify,
                                lineHeight = 22.sp
                            )
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(20.dp))
            Card(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(vertical = 8.dp)
                    .height(280.dp)
                    .background(Color.White)
                    .width(250.dp),
                shape = RoundedCornerShape(CornerSize(0.dp)),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 1.5.dp
                ),
            )
            {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White),

                        ) {
                        Image(
                            painter = painterResource(R.drawable.thunderstorm),
                            contentDescription = "null",
                            modifier = Modifier
                                .height(150.dp)
                                .fillMaxWidth()
                                .background(Color.White),
                            contentScale = ContentScale.Crop,
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 12.dp, top = 160.dp),
                            verticalAlignment = Alignment.CenterVertically

                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.rain),
                                contentDescription = "Rain Icon",
                                modifier = Modifier.size(28.dp),
                                tint = Color(0xFF3A536C)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Thunderstorm",
                                style = TextStyle(
                                    fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                                    fontSize = 20.sp,
                                    color = Color(0xFF000000)
                                )
                            )
                        }
                        Text(
                            text = "Stay indoors and avoid electrical appliances during thunderstorms",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 200.dp, start = 18.dp, end = 22.dp),
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
                                fontSize = 16.sp,
                                color = Color(0xFF808080),
                                textAlign = TextAlign.Justify,
                                lineHeight = 22.sp
                            )
                        )
                    }


                }
            }
            Spacer(modifier = Modifier.width(20.dp))
            Card(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(vertical = 8.dp)
                    .height(280.dp)
                    .background(Color.White)
                    .width(250.dp),
                shape = RoundedCornerShape(CornerSize(0.dp)),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 1.5.dp
                ),
            )
            {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White),

                        ) {
                        Image(
                            painter = painterResource(R.drawable.cold),
                            contentDescription = "null",
                            modifier = Modifier
                                .height(150.dp)
                                .fillMaxWidth()
                                .background(Color.White),
                            contentScale = ContentScale.Crop,
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 12.dp, top = 160.dp),
                            verticalAlignment = Alignment.CenterVertically

                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.coldicon),
                                contentDescription = "Rain Icon",
                                modifier = Modifier.size(28.dp),
                                tint = Color(0xFF0CB4EE)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Cold and Foggy",
                                style = TextStyle(
                                    fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                                    fontSize = 20.sp,
                                    color = Color(0xFF000000)
                                )
                            )
                        }
                        Text(
                            text = "Minimize time outdoors especially during wind chill advisories.",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 200.dp, start = 18.dp, end = 22.dp),
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
                                fontSize = 16.sp,
                                color = Color(0xFF808080),
                                textAlign = TextAlign.Justify,
                                lineHeight = 22.sp
                            )
                        )
                    }
                }
            }
        }
    }


@Composable
fun emergencies(navController: NavController) {
    Text(
        text = "Emergencies",
        modifier = Modifier
            .padding(start = 20.dp),
        style = TextStyle(
            fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
            fontSize = 20.sp,
            color = Color(0xFF000000)
        )
    )
    Spacer(modifier = Modifier.height(10.dp))
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)

    ) {
        Card(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(vertical = 8.dp)
                .height(280.dp)
                .background(Color.White)
                .width(250.dp)
                .clickable { navController.navigate(Earthquake.route) },
            shape = RoundedCornerShape(CornerSize(0.dp)),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            ),
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),

                    ) {
                    Image(
                        painter = painterResource(R.drawable.earthquakecard),
                        contentDescription = "null",
                        modifier = Modifier
                            .height(150.dp)
                            .fillMaxWidth()
                            .background(Color.White),
                        contentScale = ContentScale.FillBounds,
                    )
                    Text(
                        text = "During an earthquake, take cover under a sturdy object to protect yourself from falling objects and debris.",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                            fontSize = 14.sp,
                            color = Color(0xFF000000),
                            textAlign = TextAlign.Justify
                        ),
                        modifier = Modifier
                            .padding(start = 12.dp, end = 12.dp, top = 160.dp)
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.padding(start = 30.dp, top = 180.dp))
                    Text(
                        text = "Read More",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.sfprodisplaybold)),
                            fontSize = 14.sp,
                            color = Color.Red
                        ),
                        modifier = Modifier.padding(start = 160.dp, top = 240.dp)
                            .clickable { navController.navigate(Earthquake.route) }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(20.dp))
        Card(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(vertical = 8.dp)
                .height(280.dp)
                .background(Color.White)
                .width(250.dp)
                .clickable { navController.navigate(Floods.route) },
            shape = RoundedCornerShape(CornerSize(0.dp)),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            ),
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),

                    ) {
                    Image(
                        painter = painterResource(R.drawable.floodcard),
                        contentDescription = "null",
                        modifier = Modifier
                            .height(150.dp)
                            .fillMaxWidth()
                            .background(Color.White),
                        contentScale = ContentScale.FillBounds,
                    )
                    // Spacer(modifier = Modifier.padding(start = 12.dp, top = 160.dp))
                    Text(
                        text = "During a flood, Keep an eye on weather updates and warnings issued by local authorities.",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                            fontSize = 14.sp,
                            color = Color(0xFF000000),
                            textAlign = TextAlign.Justify
                        ),
                        modifier = Modifier
                            .padding(start = 12.dp, end = 12.dp, top = 160.dp)
                            .fillMaxWidth()
                    )
                    Text(
                        text = "Read More",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.sfprodisplaybold)),
                            fontSize = 14.sp,
                            color = Color.Red
                        ),
                        modifier = Modifier.padding(start = 160.dp, top = 240.dp)
                            .clickable { navController.navigate(Floods.route) }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(20.dp))
        Card(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(vertical = 8.dp)
                .height(280.dp)
                .background(Color.White)
                .width(250.dp)
                .clickable { navController.navigate(Cyclone.route) },
            shape = RoundedCornerShape(CornerSize(0.dp)),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            ),
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),

                    ) {
                    Image(
                        painter = painterResource(R.drawable.cyclonecard),
                        contentDescription = "null",
                        modifier = Modifier
                            .height(150.dp)
                            .fillMaxWidth()
                            .background(Color.White),
                        contentScale = ContentScale.FillBounds,
                    )
                    Text(
                        text = "Avoid going outside during the cyclone, as flying debris and strong winds can pose a severe risk to your safety.",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                            fontSize = 14.sp,
                            color = Color(0xFF000000),
                            textAlign = TextAlign.Justify
                        ),
                        modifier = Modifier
                            .padding(start = 12.dp, end = 12.dp, top = 160.dp)
                            .fillMaxWidth()
                    )
                    Text(
                        text = "Read More",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.sfprodisplaybold)),
                            fontSize = 14.sp,
                            color = Color.Red
                        ),
                        modifier = Modifier.padding(start = 160.dp, top = 240.dp)
                            .clickable { navController.navigate(Cyclone.route) }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(20.dp))
        Card(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(vertical = 8.dp)
                .height(280.dp)
                .background(Color.White)
                .width(250.dp)
                .clickable { navController.navigate(Fires.route) },
            shape = RoundedCornerShape(CornerSize(0.dp)),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            ),
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),

                    ) {
                    Image(
                        painter = painterResource(R.drawable.firecard),
                        contentDescription = "null",
                        modifier = Modifier
                            .height(150.dp)
                            .fillMaxWidth()
                            .background(Color.White),
                        contentScale = ContentScale.FillBounds,
                    )
                    Text(
                        text = "If you spot a fire or smoke, take immediate action, activate the fire alarm to alert others and evacuate immediately.",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                            fontSize = 14.sp,
                            color = Color(0xFF000000),
                            textAlign = TextAlign.Justify
                        ),
                        modifier = Modifier
                            .padding(start = 12.dp, end = 12.dp, top = 160.dp)
                            .fillMaxWidth()
                    )
                    Text(
                        text = "Read More",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.sfprodisplaybold)),
                            fontSize = 14.sp,
                            color = Color.Red
                        ),
                        modifier = Modifier.padding(start = 160.dp, top = 240.dp)
                            .clickable { navController.navigate(Fires.route) }
                    )
                }
            }
        }
    }
}