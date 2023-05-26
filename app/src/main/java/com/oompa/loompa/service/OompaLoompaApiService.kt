package com.oompa.loompa.service

import com.oompa.loompa.model.OompaLoompaPage
import retrofit2.http.GET
import retrofit2.http.Query

interface OompaLoompaApiService {
    @GET("napptilus/oompa-loompas")
    suspend fun getPage(
        @Query("page") page: Int
    ): OompaLoompaPage
}