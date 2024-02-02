package com.example.fypdisastermanagement.destinations

interface Destinations {
    val route : String
}
object LoginScreen:Destinations{
    override val route = "Login"
}
object  SignUp:Destinations {
    override val route = "CreateAccount"
}
object  RescuerLandingScreen:Destinations{
    override  val route = "RescuerLandingScreen"
}
object  ProblemReporterLandingScreen:Destinations{
    override  val route = "ProblemReporterLandingScreen"
}
object  SocialWorkerLandingScreen:Destinations{
    override  val route = "SocialWorkerLandingScreen"
}
object  RescuerHome:Destinations{
    override  val route = "RescuerHome"
}
object  ProblemReporterHome:Destinations{
    override  val route = "ProblemReporterHome"
}
object  SocialWorkerHome:Destinations{
    override  val route = "SocialWorkerHome"
}

//Fundraisers
object  Fundraisers:Destinations{
    override  val route = "Fundraisers"
}
object  MyFundraisers:Destinations{
    override  val route = "MyFundraisers"
}
object  SubmitFundraiser:Destinations{
    override  val route = "SubmitFundraisers"
}
//object  FundraisersView:Destinations{
//    override  val route = "FundraisersView"
//}
object  MyFundraiserView:Destinations{
    override  val route = "MyFundraiserView"
}

//Reports
object  AllReports:Destinations{
    override  val route = "AllReports"
}
object MyReports:Destinations{
    override  val route = "MyReports"
}
object  SubmitReports:Destinations{
    override  val route = "SubmitReports"
}
object  ReportView:Destinations{
    override  val route = "ReportView"
}
object  MyReportView:Destinations{
    override  val route = "MyReportView"
}

//Campaigns
object  Campaigns:Destinations{
    override  val route = "Campaigns"
}
object  MyCampaigns:Destinations{
    override  val route = "MyCampaigns"
}
object  SubmitCampaign:Destinations{
    override  val route = "SubmitCampaign"
}
object  CampaignsView:Destinations{
    override  val route = "CampaignsView"
}
object  MyCampaignView:Destinations{
    override  val route = "MyCampaignView"
}


object ChooseYourselfScreen {
    const val route = "choose_yourself"
}

object  ForgotPassword:Destinations{
    override  val route = "ForgotPassword"
}
object  VerifyCodeScreen:Destinations{
    override  val route = "VerifyCodeScreen"
}
object  ResetPassword:Destinations{
    override  val route = "ResetPassword"
}
object  SuccessResetPassword:Destinations{
    override  val route = "SuccessResetPassword"
}

//Money
object  DonateMoney:Destinations{
    override  val route = "DonateMoney"
}
object  withdrawMoney:Destinations{
    override  val route = "withdrawMoney"
}
object  SuccessDonated:Destinations{
    override  val route = " SuccessDonated"
}

//SafetyTips

object  SafetyTips:Destinations{
    override  val route = "SafetyTips"
}
object  Earthquake:Destinations{
    override  val route = "Earthquake"
}
object  Floods:Destinations{
    override  val route = "Floods"
}
object  Cyclone:Destinations{
    override  val route = "Cyclone"
}
object  Fires:Destinations{
    override  val route = "Fires"
}

//Onboarding
object  Onboarding:Destinations{
    override  val route = "Onboarding"
}

//Profile
object  MyProfile:Destinations{
    override  val route = "MyProfile"
}



