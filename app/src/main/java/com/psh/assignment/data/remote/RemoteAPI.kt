package com.psh.assignment.data.remote

import com.psh.assignment.data.model.Response
import retrofit2.http.GET

interface RemoteAPI {

    @GET("sampledata.json")
    suspend fun getBooks(): Response
}