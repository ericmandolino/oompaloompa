package com.oompa.loompa.data.service

import com.oompa.loompa.data.model.OompaLoompaApiResponse
import com.oompa.loompa.data.model.OompaLoompaPage
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val API_GENDER_MALE = "M"
const val API_GENDER_FEMALE = "F"

interface OompaLoompaApiService {
    @GET("napptilus/oompa-loompas")
    suspend fun getPage(
        @Query("page") page: Int
    ): OompaLoompaPage

    @GET("napptilus/oompa-loompas/{id}")
    fun getOompaLoompa(@Path("id") oompaLoompaId: Long): Call<OompaLoompaApiResponse>
}