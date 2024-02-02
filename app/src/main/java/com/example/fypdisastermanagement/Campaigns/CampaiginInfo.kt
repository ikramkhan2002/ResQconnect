package com.example.fypdisastermanagement.Campaigns

import android.os.Parcel
import android.os.Parcelable

data class CampaignInfo(
    val campaignHeading: String,
    val campaignDes: String,
    val campaignImage: String,
    val startDate: String,
    val endDate: String,
    val latitude: Double,
    val longitude: Double,
    val contactNumber: String,
    val userId: String,
    val userName: String,
    val profileImageUrl: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(campaignHeading)
        parcel.writeString(campaignDes)
        parcel.writeString(campaignImage)
        parcel.writeString(startDate)
        parcel.writeString(endDate)
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
        parcel.writeString(contactNumber)
        parcel.writeString(userId)
        parcel.writeString(userName)
        parcel.writeString(profileImageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CampaignInfo> {
        override fun createFromParcel(parcel: Parcel): CampaignInfo {
            return CampaignInfo(parcel)
        }

        override fun newArray(size: Int): Array<CampaignInfo?> {
            return arrayOfNulls(size)
        }
    }
}

