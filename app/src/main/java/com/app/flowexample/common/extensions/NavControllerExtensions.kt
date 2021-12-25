package com.app.flowexample.common.extensions

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import timber.log.Timber
import java.lang.IllegalStateException

fun Fragment.navigateTo(destination: NavDirections) {
    findNavController().navigate(destination)
}

fun Fragment.navigateTo(@IdRes id: Int) {
    findNavController().navigate(id)
}

fun Fragment.navigateTo(@IdRes id: Int, bundle: Bundle) {
    findNavController().navigate(id, bundle)
}

fun Fragment.isGraphEquals(@IdRes id: Int): Boolean {
    try {
        return id == findNavController().graph.id
    } catch (e: IllegalStateException) {
        Timber.e(e.localizedMessage)
    }
    return false
}

fun Fragment.navigateToPrevFragment() {
    findNavController().navigateUp()
}