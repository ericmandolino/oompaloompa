package com.oompa.loompa.data

import com.oompa.loompa.TestUtil
import com.oompa.loompa.data.database.OompaLoompaDatabase
import com.oompa.loompa.data.database.OompaLoompasDao
import com.oompa.loompa.data.model.OompaLoompaApiResponse
import com.oompa.loompa.data.model.OompaLoompaExtraDetails
import com.oompa.loompa.data.service.OompaLoompaApiService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import retrofit2.Call
import retrofit2.Callback
import retrofit2.mock.Calls

@RunWith(MockitoJUnitRunner::class)
class OompaLoompaRepositoryImplTest {
    @Mock
    lateinit var oompaLoompaDatabase: OompaLoompaDatabase
    @Mock
    lateinit var oompaLoompasDao: OompaLoompasDao
    @Spy
    lateinit var oompaLoompaApiService: OompaLoompaApiService

    @Test
    fun getOompaLoompaById_returnsOompaLoompa() = runTest {
        // Arrange
        val oompaLoompaId = 1L
        val oompaLoompa1 = TestUtil.createOompaLoompa(oompaLoompaId)
        `when`(oompaLoompaDatabase.getOompaLoompasDao()).thenReturn(oompaLoompasDao)
        `when`(oompaLoompasDao.observeOompaLoompa(oompaLoompaId)).thenReturn(flowOf(oompaLoompa1))
        val repository = OompaLoompaRepositoryImpl(
            oompaLoompaApiService = oompaLoompaApiService,
            oompaLoompaDatabase = oompaLoompaDatabase,
        )

        // Act
        val result = repository.getOompaLoompa(oompaLoompaId)

        // Assert
        Assert.assertEquals(oompaLoompa1, result.first())
    }

    @Test
    fun getOompaLoompaExtraDetails_inCache_returnsOompaLoompaExtraDetails() = runTest {
        // Arrange
        val oompaLoompaId = 1L
        val oompaLoompaExtraDetails1 = TestUtil.createOompaLoompaExtraDetails(oompaLoompaId)
        `when`(oompaLoompaDatabase.getOompaLoompasDao()).thenReturn(oompaLoompasDao)
        `when`(oompaLoompasDao.observeOompaLoompaExtraDetails(oompaLoompaId)).thenReturn(flowOf(oompaLoompaExtraDetails1))
        `when`(oompaLoompasDao.getOompaLoompaExtraDetails(oompaLoompaId)).thenReturn(oompaLoompaExtraDetails1)
        val repository = OompaLoompaRepositoryImpl(
            oompaLoompaApiService = oompaLoompaApiService,
            oompaLoompaDatabase = oompaLoompaDatabase,
        )

        // Act
        val result = repository.getOompaLoompaExtraDetails(
            coroutineScope = this,
            oompaLoompaId,
            onApiFailure = {}
        )

        // Assert
        Assert.assertEquals(oompaLoompaExtraDetails1, result.first())
    }

    @Test
    fun getOompaLoompaExtraDetails_inCache_apiRequestNotTriggered() = runTest {
        // Arrange
        val oompaLoompaId = 1L
        val oompaLoompaExtraDetails1 = TestUtil.createOompaLoompaExtraDetails(oompaLoompaId)
        `when`(oompaLoompaDatabase.getOompaLoompasDao()).thenReturn(oompaLoompasDao)
        `when`(oompaLoompasDao.observeOompaLoompaExtraDetails(oompaLoompaId)).thenReturn(flowOf(oompaLoompaExtraDetails1))
        `when`(oompaLoompasDao.getOompaLoompaExtraDetails(oompaLoompaId)).thenReturn(oompaLoompaExtraDetails1)
        val repository = OompaLoompaRepositoryImpl(
            oompaLoompaApiService = oompaLoompaApiService,
            oompaLoompaDatabase = oompaLoompaDatabase,
        )

        // Act
        repository.getOompaLoompaExtraDetails(
            coroutineScope = this,
            oompaLoompaId,
            onApiFailure = {}
        )

        // Assert
        verify(oompaLoompaApiService, never()).getOompaLoompa(any())
    }

    @Test
    fun getOompaLoompaExtraDetails_notInCache_apiRequestTriggered() = runTest {
        // Arrange
        val oompaLoompaId = 1L
        val oompaLoompaExtraDetails1 = TestUtil.createOompaLoompaExtraDetails(oompaLoompaId, createdAt = 0)
        `when`(oompaLoompaApiService.getOompaLoompa(oompaLoompaId)).thenReturn(
            mock(Call::class.java) as Call<OompaLoompaApiResponse>
        )
        `when`(oompaLoompaDatabase.getOompaLoompasDao()).thenReturn(oompaLoompasDao)
        `when`(oompaLoompasDao.observeOompaLoompaExtraDetails(oompaLoompaId)).thenReturn(flowOf(oompaLoompaExtraDetails1))
        `when`(oompaLoompasDao.getOompaLoompaExtraDetails(oompaLoompaId)).thenReturn(oompaLoompaExtraDetails1)
        val repository = OompaLoompaRepositoryImpl(
            oompaLoompaApiService = oompaLoompaApiService,
            oompaLoompaDatabase = oompaLoompaDatabase,
        )

        // Act
        repository.getOompaLoompaExtraDetails(
            coroutineScope = this,
            oompaLoompaId,
            onApiFailure = {}
        )
        testScheduler.advanceUntilIdle()

        // Assert
        verify(oompaLoompaApiService).getOompaLoompa(oompaLoompaId)
    }

    @Test
    fun getOompaLoompaExtraDetails_expiredInCache_apiRequestTriggered() = runTest {
        // Arrange
        val oompaLoompaId = 1L
        val oompaLoompaExtraDetails1 = TestUtil.createOompaLoompaExtraDetails(oompaLoompaId)
        `when`(oompaLoompaApiService.getOompaLoompa(oompaLoompaId)).thenReturn(
            mock(Call::class.java) as Call<OompaLoompaApiResponse>
        )
        `when`(oompaLoompaDatabase.getOompaLoompasDao()).thenReturn(oompaLoompasDao)
        `when`(oompaLoompasDao.observeOompaLoompaExtraDetails(oompaLoompaId)).thenReturn(flowOf(oompaLoompaExtraDetails1))
        val repository = OompaLoompaRepositoryImpl(
            oompaLoompaApiService = oompaLoompaApiService,
            oompaLoompaDatabase = oompaLoompaDatabase,
        )

        // Act
        repository.getOompaLoompaExtraDetails(
            coroutineScope = this,
            oompaLoompaId,
            onApiFailure = {}
        )
        testScheduler.advanceUntilIdle()

        // Assert
        verify(oompaLoompaApiService).getOompaLoompa(oompaLoompaId)
    }

    @Test
    fun fetchOompaLoompaExtraDetails_onResponse_cacheUpdated() = runTest {
        // Arrange
        val oompaLoompaId = 1L
        val description = "description"
        val quota = "quota"
        val apiResponse = TestUtil.createOompaLoompaApiResponse(description, quota)
        val callMock = Calls.response(apiResponse)
        `when`(oompaLoompaApiService.getOompaLoompa(oompaLoompaId)).thenReturn(callMock)
        var extraDetails: OompaLoompaExtraDetails? = null
        doAnswer { invocationOnMock ->
            extraDetails = invocationOnMock.arguments[0] as OompaLoompaExtraDetails
        }.`when`(oompaLoompasDao).insert(any())
        `when`(oompaLoompaDatabase.getOompaLoompasDao()).thenReturn(oompaLoompasDao)
        val repository = OompaLoompaRepositoryImpl(
            oompaLoompaApiService = oompaLoompaApiService,
            oompaLoompaDatabase = oompaLoompaDatabase,
        )

        // Act
        repository.fetchOompaLoompaExtraDetails(
            coroutineScope = this,
            oompaLoompaId,
            onApiFailure = { },
        )
        testScheduler.advanceUntilIdle()

        // Assert
        Assert.assertEquals(oompaLoompaId, extraDetails?.id)
        Assert.assertEquals(description, extraDetails?.description)
        Assert.assertEquals(quota, extraDetails?.quota)
    }

    @Test
    fun fetchOompaLoompaExtraDetails_onFailure_callbackCalled() = runTest {
        // Arrange
        val oompaLoompaId = 1L
        val callMock = mock(Call::class.java) as Call<OompaLoompaApiResponse>
        `when`(oompaLoompaApiService.getOompaLoompa(oompaLoompaId)).thenReturn(callMock)
        val callbackCaptor = ArgumentCaptor.forClass(Callback::class.java) as ArgumentCaptor<Callback<OompaLoompaApiResponse>>
        doNothing().`when`(callMock).enqueue(callbackCaptor.capture())
        val repository = OompaLoompaRepositoryImpl(
            oompaLoompaApiService = oompaLoompaApiService,
            oompaLoompaDatabase = oompaLoompaDatabase,
        )
        var i = 0

        // Act
        repository.fetchOompaLoompaExtraDetails(
            coroutineScope = this,
            oompaLoompaId,
            onApiFailure = { i++ },
        )
        callbackCaptor.value.onFailure(callMock, Throwable())

        // Assert
        Assert.assertEquals(1, i)
    }
}