package com.example.fypdisastermanagement.bottomnavigation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fypdisastermanagement.Notifications.NotificationsForRescuers
import com.example.fypdisastermanagement.R
import com.example.myprofile.ProfileScreen
import com.example.myprofile.RescuerHome
import com.example.myprofile.SearchScreen

sealed class Screen(val title: String, val icon: ImageVector, val selectedIcon: ImageVector) {
    object Home : Screen("Home", Icons.Outlined.Home, Icons.Default.Home)
    object Search : Screen("Search", Icons.Outlined.Search, Icons.Default.Search)
    object Notification : Screen("Notification", Icons.Outlined.Notifications, Icons.Default.Notifications)
    object Profile : Screen("Profile", Icons.Outlined.Person, Icons.Default.Person)
}

@Composable
fun RescuerLandingScreen(navController: NavController) {
    val (selectedScreen, setSelectedScreen) = remember { mutableStateOf<Screen>(Screen.Home) }

    val bottomNavigationItems = listOf(
        Screen.Home,
        Screen.Search,
        Screen.Notification,
        Screen.Profile
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Content of the selected screen
        selectedScreenContent(selectedScreen, navController)


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
fun selectedScreenContent(screen: Screen, navController: NavController) {
    when (screen) {
        Screen.Home -> {
            RescuerHome(navController)
        }
        Screen.Search -> {
            // Content for the Search screen
            SearchScreen(navController)
        }
        Screen.Notification -> {
            NotificationsForRescuers(navController)
        }
        Screen.Profile -> {
            // Content for the Profile screen
            ProfileScreen(navController)
        }
    }
}





