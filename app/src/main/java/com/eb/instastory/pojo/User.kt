package com.eb.instastory.pojo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ebayhan on 12/1/20.
 */
@Parcelize
data class User(
        val userName: String = "",
        val profilePictureUrl: String = "",
        val stories: ArrayList<Story> = arrayListOf()
) : Parcelable
