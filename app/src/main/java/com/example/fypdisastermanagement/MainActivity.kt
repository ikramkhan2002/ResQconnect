package com.example.fypdisastermanagement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.forgotpasswordsetup.ResetPassword
import com.example.forgotpasswordsetup.SuccessResetPassword
import com.example.fypdisastermanagement.bottomnavigation.ProblemReporterLandingScreen
import com.example.fypdisastermanagement.campaignview.Campaigns
import com.example.fypdisastermanagement.ui.theme.FypDisasterManagementTheme
import com.google.firebase.FirebaseApp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.fypdisastermanagement.createaccount.CreateAccount
import com.example.fypdisastermanagement.createaccount.RegistrationViewModel
import com.example.fypdisastermanagement.individualprofile.PublicProfileOfRescuer
import com.example.fypdisastermanagement.navigation.FundraiserNav
import com.example.fypdisastermanagement.navigation.MyNavigation
import com.example.myprofile.ProfileScreen
import com.example.safetytips.MyReports

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            FypDisasterManagementTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FirebaseApp.initializeApp(this)
                  MyNavigation()
                }
            }
        }
    }
}
