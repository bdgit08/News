package com.mobile.dtnews

import android.content.Context
import java.io.IOException

object Utils {
    fun handleErrorMessage(
        context: Context,
        t: Throwable,
        callback: (errorString: String) -> Unit
    ) {
        if (t is IOException) {
            callback.invoke(context.getString(R.string.no_internet))
        } else {
            callback.invoke("Maintenance")
        }
    }
}