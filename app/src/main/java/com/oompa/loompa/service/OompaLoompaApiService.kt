package com.oompa.loompa.service

import com.oompa.loompa.model.OompaLoompaPage
import retrofit2.http.GET
import retrofit2.http.Query

const val API_GENDER_MALE = "M"
const val API_GENDER_FEMALE = "F"

interface OompaLoompaApiService {
    @GET("napptilus/oompa-loompas")
    suspend fun getPage(
        @Query("page") page: Int
    ): OompaLoompaPage
}