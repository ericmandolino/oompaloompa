package com.oompa.loompa.service

import com.oompa.loompa.model.OompaPage
import retrofit2.http.GET
import retrofit2.http.Query

interface OompaLoompaApiService {
    @GET("napptilus/oompa-loompas?page={page}")
    suspend fun getPage(
        @Query("page") page: Int
    ): OompaPage
}