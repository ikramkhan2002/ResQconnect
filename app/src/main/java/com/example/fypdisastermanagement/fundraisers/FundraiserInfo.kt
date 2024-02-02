package com.example.fypdisastermanagement.fundraisers

import android.os.Parcel
import android.os.Parcelable

data class FundraiserInfo(
    val fundraiserTitle: String,
    val fundraiserImageUrl: String,
    val userId: String,
    val userName: String,
    val profileImageUrl: String,
    var description: String,
    var linkReport: String,
    var amount: String,
    var startDate: String,
    var endDate: String,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(fundraiserTitle)
        parcel.writeString(fundraiserImageUrl)
        parcel.writeString(userId)
        parcel.writeString(userName)
        parcel.writeString(profileImageUrl)
        parcel.writeString(description)
        parcel.writeString(linkReport)
        parcel.writeString(amount)
        parcel.writeString(startDate)
        parcel.writeString(endDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FundraiserInfo> {
        override fun createFromParcel(parcel: Parcel): FundraiserInfo {
            return FundraiserInfo(parcel)
        }

        override fun newArray(size: Int): Array<FundraiserInfo?> {
            return arrayOfNulls(size)
        }
    }
}
