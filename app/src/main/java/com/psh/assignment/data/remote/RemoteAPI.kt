package com.psh.assignment.data.remote

import com.psh.assignment.data.model.Response
import com.psh.assignment.util.Constants
import retrofit2.http.GET

interface RemoteAPI {

    @GET(Constants.ENDPOINT_DESIGN)
    suspend fun getDesigns(): Response
}