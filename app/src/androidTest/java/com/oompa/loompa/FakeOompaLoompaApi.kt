package com.oompa.loompa

import com.oompa.loompa.data.model.OompaLoompa
import com.oompa.loompa.data.model.OompaLoompaApiResponse
import com.oompa.loompa.data.model.OompaLoompaPage
import com.oompa.loompa.data.service.OompaLoompaApiService
import retrofit2.Call
import java.io.IOException

private const val PAGE_SIZE: Int = 25

class FakeOompaLoompaApi : OompaLoompaApiService {
    private val oompaLoompas = mutableListOf<OompaLoompa>()
    private var throwOnGetPage = false

    fun throwOnNextGetPage() {
        throwOnGetPage = true
    }

    fun populateOompaLoompaPages(nofOompaLoompas: Long) {
        oompaLoompas.clear()
        if (nofOompaLoompas <= 0) {
            return
        }

        for (i in  1..nofOompaLoompas) {
            oompaLoompas.add(TestUtil.createOompaLoompa(
                id = i,
                profession = "profession${i%10}",
                gender = "gender${i%2}",
            ))
        }
    }

    override suspend fun getPage(page: Int): OompaLoompaPage {
        if (throwOnGetPage) {
            throwOnGetPage = false
            throw IOException()
        }

        val zeroBasedPage = page - 1
        val total = oompaLoompas.size / PAGE_SIZE + 1
        val oompaLoompasPage = oompaLoompas.subList(
            fromIndex = zeroBasedPage * PAGE_SIZE,
            toIndex = minOf(zeroBasedPage * (PAGE_SIZE + 1), oompaLoompas.size),
        )

        return OompaLoompaPage(
            current = page,
            total,
            oompaLoompasPage.toList()
        )
    }

    override fun getOompaLoompa(oompaLoompaId: Long): Call<OompaLoompaApiResponse> {
        TODO("Not yet implemented")
    }
}