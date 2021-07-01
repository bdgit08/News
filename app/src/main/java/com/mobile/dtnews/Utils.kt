package com.mobile.dtnews

import android.content.Context
import retrofit2.HttpException
import java.io.IOException

object Utils {
    fun handleErrorMessage(
        context: Context,
        t: Throwable,
        callback: (errorString: String) -> Unit
    ) {
        when (t) {
            is IOException -> {
                callback.invoke(context.getString(R.string.no_internet))
            }
            is HttpException -> {
                when {
                    t.code() == 429 -> {
                        callback.invoke(context.getString(R.string.error_429))
                    }
                    t.code() == 500 -> {
                        callback.invoke(context.getString(R.string.error_500))
                    }
                    t.code() == 401 -> {
                        callback.invoke(context.getString(R.string.error_401))
                    }
                }
            }
            else -> {
                callback.invoke("Maintenance")
            }
        }
    }
}