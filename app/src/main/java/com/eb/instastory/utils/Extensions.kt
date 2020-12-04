package com.eb.instastory.utils

import android.app.Activity
import android.content.res.Resources
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView


/**
 * Created by ebayhan on 12/1/20.
 */

// Time
const val SEC = 1000
const val MIN = 60 * SEC
const val HOUR = 60 * MIN

fun Long.getTimeDiff(): String {
    val diff = System.currentTimeMillis() - this

    return if (diff >= HOUR) {
        (diff / HOUR).toInt().toString() + "h"
    } else {
        (diff / MIN).toInt().toString() + "m"
    }
}

// View
fun View.hideKeyboard() {
    val inputMethodManager =
            this.context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
    inputMethodManager?.hideSoftInputFromWindow(windowToken, 0)
}

fun ImageView.loadUrl(url: String, isCircleCrop: Boolean = false) {
    var glide = Glide.with(context)
            .load(url)

    if (isCircleCrop) {
        glide = glide.circleCrop()
    }

    glide.into(this)
}

fun PlayerView.loadUrl(url: String) {
    player = SimpleExoPlayer.Builder(context).build().apply {
        setMediaItem(MediaItem.fromUri(url))
        prepare()
    }
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.slowlyGone() {
    animate().alpha(0.0f).duration = 300
}

fun View.slowlyVisible() {
    animate().alpha(1.0f).duration = 300
}

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

// Fragment
fun Fragment?.isActivityAlive(): Boolean = !((this?.activity)?.isFinishing ?: true)

fun Fragment.toast(message: String) {
    Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
}

fun log(message: String) {
    Log.d("InstagramStory", message)
}