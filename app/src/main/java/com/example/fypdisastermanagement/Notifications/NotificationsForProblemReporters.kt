package com.example.fypdisastermanagement.Notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fypdisastermanagement.NotificationsHeader

@Composable
fun NotificationsForProblemReporters(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
    ){
        NotificationsHeader()
        Spacer(modifier = Modifier.height(12.dp))
        NotificationsProblemReporters()
    }
}

@Composable
fun NotificationsProblemReporters() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp),
    ) {
        NotificationWhenCreateFundraiser(viewModel = NotificationWhenCreateFundraiser())
    }
}