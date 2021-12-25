package com.app.flowexample.common.extensions

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat

fun Context.showToast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, "" + text, duration).show()
}

fun Context.inDevelopmentToast() {
    Toast.makeText(this, "In development", Toast.LENGTH_SHORT).show()
}

fun Context.getResourceColor(resId: Int) = ContextCompat.getColor(this, resId)

fun Context.getResourceDrawable(resId: Int) = ContextCompat.getDrawable(this, resId)

fun Context.getResourceArray(resId: Int) = ArrayList(resources.getStringArray(resId).asList())

fun Context.dpToPx(dp: Int) = (dp * resources.displayMetrics.density).toInt()

fun Context.pxToDp(px: Int) = (px / resources.displayMetrics.density).toInt()

fun Context.shareText(text: String, intentText: String = "") {
    val shareIntent = Intent()
    shareIntent.action = Intent.ACTION_SEND
    shareIntent.type="text/plain"
    shareIntent.putExtra(Intent.EXTRA_TEXT, text);
    startActivity(Intent.createChooser(shareIntent, intentText))
}