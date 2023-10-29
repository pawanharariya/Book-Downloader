package com.psh.assignment.data

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import com.psh.assignment.data.Result.Error
import com.psh.assignment.data.Result.Success
import com.psh.assignment.data.model.Design
import com.psh.assignment.data.remote.RemoteAPI
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteAPI: RemoteAPI,
    private val application: Application
) {
    suspend fun getDesign(): Result<List<Design>> {
        if (!hasInternetConnection()) {
            return Error("No Internet Connection!")
        }
        return try {
            val response = remoteAPI.getDesigns()
            if (response.success) {
                Success(response.data)
            } else Error(response.errors)

        } catch (e: Exception) {
            Error(e.message)
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = application.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities =
            connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(TRANSPORT_WIFI) -> true
            capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}