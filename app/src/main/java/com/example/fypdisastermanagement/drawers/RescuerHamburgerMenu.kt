package com.example.myprofile

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ScaffoldState
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.example.fypdisastermanagement.R
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.fypdisastermanagement.destinations.AllReports
import com.example.fypdisastermanagement.destinations.Campaigns
import com.example.fypdisastermanagement.destinations.Fundraisers
import com.example.fypdisastermanagement.destinations.LoginScreen
import com.example.fypdisastermanagement.destinations.MyFundraisers
import com.example.fypdisastermanagement.destinations.MyProfile
import com.example.fypdisastermanagement.homescreen.SafetyTipsOptions
import com.example.safetytips.AddNewFundarsier
import com.example.safetytips.CampaignsSection
import com.example.safetytips.ClickableTextViewAll
import com.example.safetytips.FundraisersSection
import com.example.safetytips.LatestReports
import com.example.safetytips.SearchBar
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RescuerHome(navController: NavController){
    val scaffoldState  = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            Column {
                Box(
                    modifier = Modifier
                        .height(120.dp)
                        .fillMaxWidth()
                        .background(Color(0xFFD34040))
                )
                {
                    CircleShapes()
                    IconButton(
                        onClick = {
                            scope.launch {
                                scaffoldState.drawerState.close()
                            }
                        },
                        modifier = Modifier.offset(x = 250.dp)
                    )
                    {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "close",
                            tint = Color.White,
                        )
                    }
                    RescuerDrawerHeader()
                }
                Spacer(modifier = Modifier.height(20.dp))
                RescuerDrawerContent(navController = navController)
            }
        },
        drawerGesturesEnabled = false,
        content = {
            Box(Modifier.verticalScroll(rememberScrollState())
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    // Your content sections here...
                    AppHeader(scaffoldState = scaffoldState, navController = navController)
                    Spacer(modifier = Modifier.height(25.dp))
                    RescuerName()
                    Spacer(modifier = Modifier.height(35.dp))
                    SearchBar()
                    Spacer(modifier = Modifier.height(35.dp))
                    AddNewFundarsier(navController)
                    Spacer(modifier = Modifier.height(20.dp))
                    SafetyTipsOptions(navController)
                    Spacer(modifier = Modifier.height(50.dp))
                    Text(
                        text = "Latest Reports",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                            fontSize = 20.sp,
                            color = Color.Black,
                        ),
                        modifier = Modifier.padding(start = 20.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    ClickableTextViewAll(
                        onClick = {
                            navController.navigate(AllReports.route)
                        },
                        navController = navController
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    LatestReports(navController)
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Fundraisers",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                            fontSize = 20.sp,
                            color = Color.Black,
                        ),
                        modifier = Modifier.padding(start = 20.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    ClickableTextViewAll(
                        onClick = {
                            navController.navigate(Fundraisers.route)
                        },
                        navController = navController
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    FundraisersSection(navController)
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Campaigns",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                            fontSize = 20.sp,
                            color = Color.Black,
                        ),
                        modifier = Modifier.padding(start = 20.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    ClickableTextViewAll(
                        onClick = {
                            navController.navigate(Campaigns.route)
                        },
                        navController = navController
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    CampaignsSection(navController)
                    Spacer(modifier = Modifier.height(95.dp))
                }
            }
        }
    )
}

@Composable
fun AppHeader(scaffoldState: ScaffoldState, navController: NavController) {
    val scope = rememberCoroutineScope()
    var selectedUserData: UsersData? by remember { mutableStateOf(null) }

    LaunchedEffect(true) {
        selectedUserData = fetchUserData()
    }
    selectedUserData?.let { user ->
        TopAppBar(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = 0.dp,
            backgroundColor = Color.White,
            contentPadding = PaddingValues(start = 5.dp, end = 10.dp)
        ) {
            IconButton(
                onClick = {
                    scope.launch {
                        scaffoldState.drawerState.open() // Open the drawer
                    }
                },
            ) {
                Icon(
                    Icons.Default.Menu,
                    contentDescription = "list",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(35.dp)
                        .clickable {
                            scope.launch {
                                scaffoldState.drawerState.open() // Open the drawer
                            }
                        }
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = rememberImagePainter(data = user.profileImageUrl),
                contentDescription = "null",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(45.dp)
                    .clip(shape = CircleShape)
                    .clickable { navController.navigate(MyProfile.route) }
            )
        }
    }
}

@Composable
fun RescuerName(){
    var selectedUserData: UsersData? by remember { mutableStateOf(null) }

    LaunchedEffect(true) {
        selectedUserData = fetchUserData()
    }
    selectedUserData?.let { user ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp)
        ) {
            Text(
                text = "Hey " + user.firstName + "!",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.merriweatherbold)),
                    fontSize = 24.sp,
                    color = Color.Black,
                ),
            )
            Spacer(modifier = Modifier.height(7.dp))
            Text(
                text = "Fund relief, rebuild lives",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.merriweatherregular)),
                    fontSize = 16.sp,
                    color = Color(0xFF808080),
                ),
            )
        }
    }
}

@Composable
fun RescuerDrawerHeader() {
    var selectedUserData: UsersData? by remember { mutableStateOf(null) }

    LaunchedEffect(true) {
        selectedUserData = fetchUserData()
    }
    selectedUserData?.let { user ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        ) {
            Row(
                modifier = Modifier.padding(start = 30.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberImagePainter(data = user.profileImageUrl),
                    contentDescription = "null",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(60.dp)
                )
                Spacer(modifier = Modifier.width(15.dp))
                Column {
                    Text(
                        text = user.username,
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.sfprodisplaymedium)),
                            fontSize = 16.sp,
                            color = Color.White,
                        ),
                    )
                    Text(
                        text = user.role,
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
                            fontSize = 14.sp,
                            color = Color.White,
                        ),
                    )
                }
            }
        }
    }
}

@Composable
fun CircleShapes(){
    Image(
        painter = painterResource(R.drawable.circleshapes),
        contentDescription = "CircleShapes",
        contentScale = ContentScale.FillBounds,
    )
}

@Composable
fun RescuerDrawerContent(navController: NavController) {
    data class DrawerItem(val text: String, val icon: Int)

    // List of drawer items
    val drawerItems = listOf(
        DrawerItem("Reports", R.drawable.reportsicon),
        DrawerItem("My Fundraisers", R.drawable.money),
        DrawerItem("Campaigns", R.drawable.campaignicon),
        DrawerItem("Donate", R.drawable.donateicon),
        DrawerItem("Logout", R.drawable.logout),
    )

    LazyColumn {
        itemsIndexed(drawerItems) {  index, item ->
            Row(
                modifier = Modifier
                    .clickable {
                        // Handle item click here
                        navigateToScreen(navController, item.text)
                    }
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = item.icon ),
                    contentDescription = null, // No content description for icons
                    tint = Color(0xFF777777),
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = item.text,
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.poppinsmedium)),
                        fontSize = 16.sp,
                        color = Color.Black,
                    ),
                )
            }

            if (index == 3) {
                Divider(
                    thickness = 1.5.dp,
                    color = Color.Black.copy(alpha = 0.25f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, start = 16.dp, end = 50.dp)
                )
            }

            if (index == drawerItems.lastIndex) {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

private fun navigateToScreen(navController: NavController, screen: String) {
    when (screen) {
       // "Profile" -> navController.navigate("profile")
        "Reports" -> navController.navigate(AllReports.route)
        "My Fundraisers" -> navController.navigate(MyFundraisers.route)
        "Campaigns" -> navController.navigate(Campaigns.route)
        "Donate" -> navController.navigate(Fundraisers.route)
        "Logout" -> navController.navigate(LoginScreen.route)
    }
}















