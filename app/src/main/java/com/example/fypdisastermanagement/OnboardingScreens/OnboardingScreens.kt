package com.example.safetytips

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.Button
import androidx.compose.material3.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fypdisastermanagement.R
import com.example.fypdisastermanagement.destinations.Floods
import com.example.fypdisastermanagement.destinations.LoginScreen


@ExperimentalPagerApi
@Composable
fun MainFunction(navController: NavController) {
    val items = ArrayList<OnBoardingData>()

    items.add(
        OnBoardingData(
            R.drawable.onboardingimage1,
            "Become a Rescuer",
            "Become a valued rescuer in our community dedicated to supporting relief efforts. Raise funds to make a meaningful on those in need.\n"
        )
    )

    items.add(
        OnBoardingData(
            R.drawable.onboardingimage2,
            "Report Critical\n" +
                    "Incidents",
            "Take an active role in reporting critical incidents in your area. Your report provide vital information to rescuers understand the situation and take immediate action."
        )
    )

    items.add(
        OnBoardingData(
            R.drawable.onboardingimage3,
            "Share and Support",
            "Join us as a Social Worker. Post campaigns, events, and vital information to make a real impact in our community.",
        )
    )
    val pagerState = rememberPagerState(
        pageCount = items.size,
        initialOffscreenLimit = 2,
        infiniteLoop = false,
        initialPage = 0
    )

    OnBoardingPager(
        item = items,
        pagerState = pagerState,
        navController = navController,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
    )
}


@ExperimentalPagerApi
@Composable
fun OnBoardingPager(
    item: List<OnBoardingData>,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Box(modifier = modifier) {
        TopSection(navController)
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(count = 3, state = pagerState) { page ->
                Column(
                    modifier = Modifier
                        .padding(top = 130.dp)
                        .fillMaxWidth(),
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Image(
                            painter = painterResource(id = item[page].image),
                            contentDescription = "image",
                            // contentDescription = item[page].title,
                            modifier = Modifier
                                .height(200.dp)
                                .fillMaxWidth()
                        )
                    }
                    Text(
                        text = item[page].title,
                        modifier = Modifier.padding(top = 50.dp, start = 20.dp),
                        color = Color.Black,
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.robotobold)),
                            fontSize = 28.sp,
                            color = Color.Black
                        )
                    )
                    Text(
                        text = item[page].desc,
                        modifier = Modifier.padding(top = 20.dp, start = 20.dp, end = 20.dp),
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.robotoregular)),
                            fontSize = 16.sp,
                            color = Color(0xFF000000),
                            lineHeight = 20.sp,
                          //  textAlign = TextAlign.Justify,
                        )
                    )
                }
            }

            PagerIndicator(item.size, pagerState.currentPage)
        }

        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
           BottomSection(pagerState.currentPage, navController)
        }
    }
}


@ExperimentalPagerApi
@Composable
fun rememberPagerState(
    pageCount: Int,
    initialPage: Int = 0,
    initialPageOffset: Float = 0f,
    initialOffscreenLimit: Int = 1,
    infiniteLoop: Boolean = false
): PagerState = rememberSaveable(saver = PagerState.Saver) {
    PagerState(
        //pageCount = pageCount,
        currentPage = initialPage,
      //  currentPageOffset = initialPageOffset,
      //  offscreenLimit = initialOffscreenLimit,
      //  infiniteLoop = infiniteLoop
    )
}

@Composable
fun PagerIndicator(
    size: Int,
    currentPage: Int
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(top = 60.dp)
    ) {
        repeat(size) {
            Indicator(isSelected = it == currentPage)
        }
    }
}

@Composable
fun Indicator(isSelected: Boolean) {
    val width = animateDpAsState(targetValue = if (isSelected) 28.dp else 10.dp)

    Box(
        modifier = Modifier
            .padding(1.5.dp)
            .height(10.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(
                color =
                if (isSelected) Color(0xFFD34040) else Color.Gray.copy(alpha = 0.5f)
            )
    )
}

@Composable
fun TopSection(navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        TextButton(
            onClick = {  navController.navigate(LoginScreen.route)
                 },
            modifier = Modifier
                .padding(end = 20.dp, top = 16.dp),
        )
        {
            Text(
                text = "Skip",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                    fontSize = 16.sp,
                    color = Color(0xFF000000).copy(alpha = 0.5f)
                ),
                modifier = Modifier.clickable {
                    navController.navigate(LoginScreen.route) }
            )
       }
    }
}

@Composable
fun BottomSection(currentPager: Int, navController: NavController) {
    Row(
        modifier = Modifier
            .padding(bottom = 20.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.Bottom
    ) {
        if (currentPager == 2) {
            BackButton()
            GetStartedButton(navController)
        }
        if (currentPager != 0) {
            BackButton()
        }
        if (currentPager != 2) {
            NextButton()
        }
    }
}

@Composable
fun BackButton(){
    TextButton(
        onClick = {},
        modifier = Modifier
            .padding(start = 20.dp),
    )
    {
        Text(
            text = "Back",
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                fontSize = 16.sp,
                color = Color(0xFFD34040)
            )
        )
    }
}

@Composable
fun NextButton() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 20.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Button(
            onClick = {},
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(Color(0xFFD34040)),
            modifier = Modifier
                .size(55.dp),
            contentPadding = PaddingValues(0.dp),
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painterResource(R.drawable.arrow_forward),
                    contentDescription = "forward",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp),
                )
            }
        }
    }
}

@Composable
fun GetStartedButton(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 20.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Button(
            onClick = {
                navController.navigate(LoginScreen.route)
            },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(Color(0xFFD34040)),
            modifier = Modifier
                .height(45.dp)
                .width(150.dp)
        ) {
            Text(
                text = "Get Started",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.robotomedium)),
                    fontSize = 16.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                ),
            )
        }
    }
}

@Preview
@ExperimentalPagerApi
@Composable
fun OnBoardingPrev(){
    MainFunction(navController = rememberNavController())
}