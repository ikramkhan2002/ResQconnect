package com.example.fypdisastermanagement.bottomnavigation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fypdisastermanagement.R
import com.example.myprofile.ProblemReporterHome
import com.example.myprofile.ProfileScreen
import com.example.myprofile.SearchScreen
import androidx.compose.material.Text
import com.example.fypdisastermanagement.Notifications.NotificationsForProblemReporters

@Composable
fun ProblemReporterLandingScreen(navController: NavController) {
    val (selectedScreen, setSelectedScreen) = remember { mutableStateOf<Screen>(Screen.Home) }

    val bottomNavigationItems = listOf(
        Screen.Home,
        Screen.Search,
        Screen.Notification,
        Screen.Profile
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Content of the selected screen
        ProblemReporterSelectedScreenContent(selectedScreen, navController)


        // Bottom navigation bar
        BottomNavigation(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(),
            backgroundColor = Color(0xFFFFFFFF)
        ) {
            bottomNavigationItems.forEach { screen ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector =  if (screen == selectedScreen) screen.selectedIcon else screen.icon,
                            contentDescription = screen.title,
                            tint = if (screen == selectedScreen) {
                                Color(0xFFD34040)
                            } else {
                                Color(0xFF808080).copy(alpha = 0.9f)
                            },
                            modifier = if (screen == selectedScreen) {
                                Modifier.size(28.dp) // Apply the modifier if the screen is selected
                            } else {
                                Modifier.size(25.dp) // Apply a different modifier if it's not selected
                            }
                        )
                    },
                    label = {
                        Text(
                            text = screen.title,
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.sfprodisplayregular)),
                                fontSize = 12.sp,
                                color = if (screen == selectedScreen) {
                                    Color(0xFFD34040)
                                } else {
                                    Color(0xFF808080).copy(alpha = 0.9f)
                                }
                            )
                        )
                    },
                    selected = screen == selectedScreen,
                    onClick = {
                        setSelectedScreen(screen)
                    },
                    selectedContentColor = Color(0xFFD34040)
                )
            }
        }
    }
}

@Composable
fun ProblemReporterSelectedScreenContent(screen: Screen, navController: NavController) {
    when (screen) {
        Screen.Home -> {
            ProblemReporterHome(navController)
        }
        Screen.Search -> {
            // Content for the Search screen
            SearchScreen(navController)
        }
        Screen.Notification -> {
            // Content for the Notification screen
       // ReportScreen(viewModel = ReportViewModel())
            NotificationsForProblemReporters(navController)
        }
        Screen.Profile -> {
            // Content for the Profile screen
            ProfileScreen(navController)
        }
    }
}



@Preview(showBackground = true)
@Composable
fun prLandingScreen() {
    ProblemReporterLandingScreen(navController = rememberNavController())
}