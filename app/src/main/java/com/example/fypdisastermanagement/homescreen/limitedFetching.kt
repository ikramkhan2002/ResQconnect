package com.example.fypdisastermanagement.homescreen

import com.example.fypdisastermanagement.Campaigns.CampaignInfo
import com.example.fypdisastermanagement.fundraisers.FundraiserInfo
import com.example.fypdisastermanagement.reportview.Report
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

// Function to fetch limited campaign information from Firestore
suspend fun fetchLimitedCampaignsInfo(): List<CampaignInfo> {
    val firestore = Firebase.firestore
    val collection = firestore.collection("Campaigns")

    val querySnapshot = collection.limit(4).get().await()

    val campaignsList = mutableListOf<CampaignInfo>()

    for (document in querySnapshot.documents) {
        val id = document.id // Use the document ID as campaignId
        val heading = document.getString("campaignTitle") ?: "Default Heading"
        val description = document.getString("campaignDescription") ?: ""
        val image = document.getString("imageUrl") ?: ""
        val startDate = document.getString("campaignStartDate") ?: ""
        val endDate = document.getString("campaignEndDate") ?: ""
        val latitude = document.getDouble("latitude") ?: 0.0
        val longitude = document.getDouble("longitude") ?: 0.0
        val contactNumber = document.getString("contactNo") ?: ""
        val userId = document.getString("userId") ?: "" // Assuming you have a field for userId
        val name =  ""
        val profileImg =  ""

        val campaignInfo = CampaignInfo( heading, description, image, startDate, endDate, latitude, longitude, contactNumber, userId, name, profileImg )
        campaignsList.add(campaignInfo)
    }

    return campaignsList
}


// Function to fetch all fundraiser titles and image URLs from Firestore
suspend fun fetchLimitedFundraisersInfo(): List<FundraiserInfo> {
    val firestore = Firebase.firestore
    val collection = firestore.collection("Fundraiser")

    val querySnapshot = collection.limit(4).get().await()


    val fundraisersList = mutableListOf<FundraiserInfo>()

    for (document in querySnapshot.documents) {
        val title = document.getString("fundraiserTitle") ?: "Default Title"
        val imageUrl = document.getString("fundraiserImageUrl") ?: ""
        val userId = document.getString("userId") ?: "" // Assuming you have a field for userId
        val startDate = document.getString("startDate") ?: "" // Assuming you have a field for userId
        val endDate = document.getString("endDate") ?: "" // Assuming you have a field for userId
        val description = document.getString("description") ?: "" // Assuming you have a field for userId
        val linkreport = document.getString("linkReport") ?: "" // Assuming you have a field for userId
        val amount = document.getString("amount") ?: "" // Assuming you have a field for userId
        val currentUid = document.getString("currentUid") ?: ""


        val userDocument = firestore.collection("users").document(currentUid).get().await()
        val firstName = userDocument.getString("firstName") ?: "Unknown"
        val lastName = userDocument.getString("lastName") ?: "User"

        // Combine first name and last name to form the user's full name
        val userName = "$firstName $lastName"
        val profileImageUrl = userDocument.getString("profileImageUrl") ?: ""
        val fundraiserInfo = FundraiserInfo(title, imageUrl,userId, userName, profileImageUrl,description,linkreport,
            amount,startDate,endDate)
        fundraisersList.add(fundraiserInfo)
    }

    return fundraisersList
}


// Function to fetch reports from Firestore
suspend fun fetchLimitedReportsFromFirestore(): List<Report> {
    val firestore = Firebase.firestore
    val collection = firestore.collection("Reports")

    val querySnapshot = collection.get().await()

    val reportsList = mutableListOf<Report>()

    for (document in querySnapshot.documents) {
        val reportTitle = document.getString("reportTitle") ?: "Default Title"
        val imageUrl = document.getString("imageUrl") ?: "" // Adjust field names as per your Firestore schema
        val description = document.getString("reportDescription") ?: ""
        val latitude = document.getDouble("latitude") ?: 0.0
        val longitude = document.getDouble("longitude") ?: 0.0
        val userId = document.getString("userId") ?: "" // Assuming you have a field for userId
        val currentUid = document.getString("currentUid") ?: ""

        val userDocument = firestore.collection("users").document(currentUid).get().await()
        val firstName = userDocument.getString("firstName") ?: "Unknown"
        val lastName = userDocument.getString("lastName") ?: "User"

        // Combine first name and last name to form the user's full name
        val userName = "$firstName $lastName"
        val profileImageUrl = userDocument.getString("profileImageUrl") ?: ""
        val report = Report(reportTitle, imageUrl, description, latitude , longitude,userId, userName, profileImageUrl)
        reportsList.add(report)
    }

    return reportsList
}
