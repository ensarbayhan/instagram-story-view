package com.eb.instastory.pojo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ebayhan on 12/1/20.
 */
@Parcelize
data class Story(var id: Int = -1, var url: String = "", var date: Long = 0L) : Parcelable {

    fun isVideo(): Boolean {
        return url.contains(".mp4")
    }
}
