package com.example.fypdisastermanagement.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.forgotpasswordsetup.ForgotPassword
import com.example.forgotpasswordsetup.ResetPassword
import com.example.forgotpasswordsetup.SuccessResetPassword
import com.example.forgotpasswordsetup.VerifyCodeScreen
import com.example.fypdisastermanagement.Campaigns.CampaignView
import com.example.fypdisastermanagement.Reports.ReportView
import com.example.fypdisastermanagement.SafetyTips.Fires
import com.example.fypdisastermanagement.SafetyTips.Floods
import com.example.fypdisastermanagement.bottomnavigation.RescuerLandingScreen
import com.example.fypdisastermanagement.campaignview.Campaigns
import com.example.fypdisastermanagement.createaccount.ChooseYourself
import com.example.fypdisastermanagement.createaccount.CreateAccount
import com.example.fypdisastermanagement.createaccount.RegistrationViewModel
import com.example.fypdisastermanagement.destinations.AllReports
import com.example.fypdisastermanagement.destinations.Campaigns
import com.example.fypdisastermanagement.destinations.ChooseYourselfScreen
import com.example.fypdisastermanagement.destinations.Cyclone
import com.example.fypdisastermanagement.destinations.Earthquake
import com.example.fypdisastermanagement.destinations.Fires
import com.example.fypdisastermanagement.destinations.Floods
import com.example.fypdisastermanagement.destinations.ForgotPassword
import com.example.fypdisastermanagement.destinations.Fundraisers
import com.example.fypdisastermanagement.destinations.LoginScreen
import com.example.fypdisastermanagement.destinations.MyCampaigns
import com.example.fypdisastermanagement.destinations.MyFundraisers
import com.example.fypdisastermanagement.destinations.MyProfile
import com.example.fypdisastermanagement.destinations.MyReports
import com.example.fypdisastermanagement.destinations.Onboarding
import com.example.fypdisastermanagement.destinations.ProblemReporterHome
import com.example.fypdisastermanagement.destinations.ProblemReporterLandingScreen
import com.example.fypdisastermanagement.destinations.RescuerHome
import com.example.fypdisastermanagement.destinations.RescuerLandingScreen
import com.example.fypdisastermanagement.destinations.ResetPassword
import com.example.fypdisastermanagement.destinations.SafetyTips
import com.example.fypdisastermanagement.destinations.SignUp
import com.example.fypdisastermanagement.destinations.SocialWorkerHome
import com.example.fypdisastermanagement.destinations.SocialWorkerLandingScreen
import com.example.fypdisastermanagement.destinations.SubmitCampaign
import com.example.fypdisastermanagement.destinations.SubmitFundraiser
import com.example.fypdisastermanagement.destinations.SubmitReports
import com.example.fypdisastermanagement.destinations.SuccessDonated
import com.example.fypdisastermanagement.destinations.SuccessResetPassword
import com.example.fypdisastermanagement.destinations.VerifyCodeScreen
import com.example.fypdisastermanagement.donation.DonateMoney
import com.example.fypdisastermanagement.donation.PaymentMethodViewModel
import com.example.fypdisastermanagement.donation.SuccessDonated
import com.example.fypdisastermanagement.donation.withdrawMoney
import com.example.fypdisastermanagement.fundraisers.FundraiserView
import com.example.fypdisastermanagement.fundraisers.Fundraisers
import com.example.fypdisastermanagement.fundraisers.MyFundraiserView
import com.example.fypdisastermanagement.fundraisers.MyFundraisers
import com.example.fypdisastermanagement.individualprofile.PublicProfileOfIncidentNotifier
import com.example.fypdisastermanagement.individualprofile.PublicProfileOfRescuer
import com.example.fypdisastermanagement.individualprofile.PublicProfileOfSocialWorker
import com.example.fypdisastermanagement.login.LoginScreen
import com.example.fypdisastermanagement.reportview.Reports
import com.example.fypdisastermanagement.submit.SubmitFundraiser
import com.example.fypdisastermanagement.submit.SubmitReport
import com.example.myprofile.ProblemReporterHome
import com.example.myprofile.ProfileScreen
import com.example.myprofile.RescuerHome
import com.example.myprofile.SocialWorkerHome
import com.example.safetytips.Cyclone
import com.example.safetytips.Earthquake
import com.example.safetytips.MainFunction
import com.example.safetytips.MyCampaigns
import com.example.safetytips.MyReports
import com.example.safetytips.SafetyTips
import com.example.safetytips.SubmitCampaign
import com.google.accompanist.pager.ExperimentalPagerApi

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MyNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = LoginScreen.route) {
        composable(Onboarding.route) {
            MainFunction(navController)
        }
        composable(LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(RescuerLandingScreen.route) {
            RescuerLandingScreen(navController)
        }
        composable(ProblemReporterLandingScreen.route) {
          com.example.fypdisastermanagement.bottomnavigation.ProblemReporterLandingScreen(
              navController)
        }
        composable(SocialWorkerLandingScreen.route) {
            com.example.fypdisastermanagement.bottomnavigation.SocialWorkerLandingScreen(
                navController
            )
        }
        composable(RescuerHome.route) {
           RescuerHome(navController)
        }
        composable(ProblemReporterHome.route) {
            ProblemReporterHome(navController)
        }
        composable(SocialWorkerHome.route) {
            SocialWorkerHome(navController)
        }
        composable(SignUp.route) {
            CreateAccount(viewModel = RegistrationViewModel(),navController) // Pass navController to HomeScreen
        }
        composable(Fundraisers.route) {
            Fundraisers(navController,)
        }
        composable(MyFundraisers.route) {
            MyFundraisers(navController)
        }
        composable(SubmitFundraiser.route) {
            SubmitFundraiser( navController)
        }
        composable(AllReports.route) {
            Reports(navController)
        }
        composable(MyReports.route) {
            MyReports(navController)
        }
        composable(SubmitReports.route) {
            SubmitReport(context = LocalContext.current , navController)
        }
        composable(Campaigns.route) {
            Campaigns(navController)
        }
        composable(MyCampaigns.route) {
            MyCampaigns(navController)
        }
        composable(SubmitCampaign.route) {
            SubmitCampaign(navController)
        }


        composable(ForgotPassword.route) {
            ForgotPassword(navController)
        }
        composable(VerifyCodeScreen.route) {
           VerifyCodeScreen(navController)
        }
        composable(ResetPassword.route) {
            ResetPassword(navController)
        }
        composable(SuccessResetPassword.route) {
            SuccessResetPassword(navController)
        }
        composable(ChooseYourselfScreen.route) {
            ChooseYourself(navController) // Your ChooseYourself composable
        }

        //Campaigns Views
        composable(
            route = "campaignView/{campaignTitle}",
            arguments = listOf(navArgument("campaignTitle") { type = NavType.StringType })
        ) { backStackEntry ->
            val campaignTitle = backStackEntry.arguments?.getString("campaignTitle") ?: ""
            CampaignView(navController = navController, campaignTitle = campaignTitle)
        }
        composable(
            route = "myCampaignView/{campaignTitle}",
            arguments = listOf(navArgument("campaignTitle") { type = NavType.StringType })
        ) { backStackEntry ->
            val campaignTitle = backStackEntry.arguments?.getString("campaignTitle") ?: ""
        com.example.myapplication.MyCampaignView(navController = navController, campaignTitle = campaignTitle)
        }
//        composable(MyCampaignView.route) {
//            MyCampaignView(navController)
//        }

        //Report Views
        composable(
            route = "reportView/{reportTitle}",
            arguments = listOf(navArgument("reportTitle") { type = NavType.StringType })
        ) { backStackEntry ->
            val reportTitle = backStackEntry.arguments?.getString("reportTitle") ?: ""
            ReportView(navController = navController, reportTitle = reportTitle)
        }
        composable(
            route = "myReportView/{reportTitle}",
            arguments = listOf(navArgument("reportTitle") { type = NavType.StringType })
        ) { backStackEntry ->
            val reportTitle = backStackEntry.arguments?.getString("reportTitle") ?: ""
com.example.myapplication.MyReportView(navController = navController, reportTitle = reportTitle )
        }



        composable(
            route = "fundraiserView/{fundraiserTitle}",
            arguments = listOf(navArgument("fundraiserTitle") { type = NavType.StringType })
        ) { backStackEntry ->
            val fundraiserTitle = backStackEntry.arguments?.getString("fundraiserTitle") ?: ""
           // MyFundraiserView(navController = navController, fundraiserTitle = fundraiserTitle)
        FundraiserView(navController = navController, fundraiserTitle = fundraiserTitle)
        }
        composable(
            route = "myFundraiserView/{fundraiserTitle}",
            arguments = listOf(navArgument("fundraiserTitle") { type = NavType.StringType })
        ) { backStackEntry ->
            val fundraiserTitle = backStackEntry.arguments?.getString("fundraiserTitle") ?: ""
           MyFundraiserView(navController = navController, fundraiserTitle = fundraiserTitle)
        } 
        composable(
            route = "withdraw/{fundraiserTitle}",
            arguments = listOf(navArgument("fundraiserTitle") { type = NavType.StringType })
        ) { backStackEntry ->
            val fundraiserTitle = backStackEntry.arguments?.getString("fundraiserTitle") ?: ""
         withdrawMoney(navController = navController,fundraiserTitle)
        }

        composable(
            route = "donation/{fundraiserTitle}",
            arguments = listOf(navArgument("fundraiserTitle") { type = NavType.StringType })
        ) { backStackEntry ->
            val fundraiserTitle = backStackEntry.arguments?.getString("fundraiserTitle") ?: ""
            DonateMoney(paymentMethodViewModel = PaymentMethodViewModel(), navController = navController, fundraiserTitle = fundraiserTitle)

        }


        composable(
            route = "rescuerPublic/{currentUid}",
            arguments = listOf(navArgument("currentUid") { type = NavType.StringType })
        ) { backStackEntry ->
            val currentUid = backStackEntry.arguments?.getString("currentUid") ?: ""
            //   DonateMoney(paymentMethodViewModel = PaymentMethodViewModel(), navController = navController, currentUid = C)
            PublicProfileOfRescuer(navController = navController, userId = currentUid)
        }
        composable(
            route = "socialWorkerPublic/{currentUid}",
            arguments = listOf(navArgument("currentUid") { type = NavType.StringType })
        ) { backStackEntry ->
            val currentUid = backStackEntry.arguments?.getString("currentUid") ?: ""
            //   DonateMoney(paymentMethodViewModel = PaymentMethodViewModel(), navController = navController, currentUid = C)
            PublicProfileOfSocialWorker(navController = navController, userId = currentUid)
        }
        composable(
            route = "incidentNotifierPublic/{currentUid}",
            arguments = listOf(navArgument("currentUid") { type = NavType.StringType })
        ) { backStackEntry ->
            val currentUid = backStackEntry.arguments?.getString("currentUid") ?: ""
            //   DonateMoney(paymentMethodViewModel = PaymentMethodViewModel(), navController = navController, currentUid = C)
            PublicProfileOfIncidentNotifier(navController = navController, userId =currentUid )
        }

        //Money Related
//        composable(DonateMoney.route) {
//            DonateMoney(paymentMethodViewModel = PaymentMethodViewModel(), navController = navController)
//        }
        composable(VerifyCodeScreen.route) {
            VerifyCodeScreen(navController)
        }
        composable(SuccessDonated.route) {
            SuccessDonated(navController)
        }

        //Safety Tips
        composable(SafetyTips.route) {
           SafetyTips(navController)
        }
        composable(Floods.route) {
            Floods(navController)
        }
        composable(Earthquake.route) {
            Earthquake(navController)
        }
        composable(Cyclone.route) {
            Cyclone(navController)
        }
        composable(Fires.route) {
            Fires(navController)
        }

        //Profile
        composable(MyProfile.route) {
            ProfileScreen(navController)
        }

    }
}




@Composable
fun MyApp(){
    // Inside your NavHost setup or wherever you initialize your navigation
    val navController = rememberNavController()

    // Inside your navigation graph setup

    NavHost(navController = navController, startDestination = "campaigns") {
        composable("campaigns") {
            Campaigns(navController = navController)
        }
        composable(
            route = "campaignView/{campaignTitle}",
            arguments = listOf(navArgument("campaignTitle") { type = NavType.StringType })
        ) { backStackEntry ->
            val campaignTitle = backStackEntry.arguments?.getString("campaignTitle") ?: ""
            CampaignView(navController = navController, campaignTitle = campaignTitle)
        }
    }
}
@Composable
fun FundraiserNav() {
    // Inside your NavHost setup or wherever you initialize your navigation
    val navController = rememberNavController()

    // Inside your navigation graph setup

    NavHost(navController = navController, startDestination = "fundraisers") {
        composable("fundraisers") {
            Fundraisers(navController = navController)
        }
        composable(
            route = "fundraiserView/{fundraiserTitle}",
            arguments = listOf(navArgument("fundraiserTitle") { type = NavType.StringType })
        ) { backStackEntry ->
            val fundraiserTitle = backStackEntry.arguments?.getString("fundraiserTitle") ?: ""
           // MyFundraiserView(navController = navController, fundraiserTitle = fundraiserTitle)
            FundraiserView(navController = navController, fundraiserTitle = fundraiserTitle)

        }
    }
}