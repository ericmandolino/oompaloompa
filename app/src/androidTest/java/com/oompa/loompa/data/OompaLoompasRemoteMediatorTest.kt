package com.oompa.loompa.data

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.oompa.loompa.FakeOompaLoompaApi
import com.oompa.loompa.data.database.OompaLoompaDatabase
import com.oompa.loompa.data.database.OompaLoompasDao
import com.oompa.loompa.data.database.RemoteKeysDao
import com.oompa.loompa.data.model.OompaLoompa
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
@RunWith(AndroidJUnit4::class)
class OompaLoompasRemoteMediatorTest {
    private lateinit var oompaLoompaDao: OompaLoompasDao
    private lateinit var remoteKeysDao: RemoteKeysDao
    private lateinit var oompaLoompaDatabase: OompaLoompaDatabase
    private val oompaLoompaApiService = FakeOompaLoompaApi()

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        oompaLoompaDatabase = Room.inMemoryDatabaseBuilder(
            context, OompaLoompaDatabase::class.java).build()
        oompaLoompaDao = oompaLoompaDatabase.getOompaLoompasDao()
        remoteKeysDao = oompaLoompaDatabase.getRemoteKeysDao()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        oompaLoompaDatabase.close()
    }

    @Test
    fun refreshLoad_noMoreDataPresent_endOfPaginationReached() = runTest {
        // Arrange
        oompaLoompaApiService.populateOompaLoompaPages(10)
        val remoteMediator = OompaLoompasRemoteMediator(
            oompaLoompaApiService = oompaLoompaApiService,
            oompaLoompaDatabase,
        )
        val pagingState = PagingState<Int, OompaLoompa>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(25),
            leadingPlaceholderCount = 25,
        )

        // Act
        val result = remoteMediator.load(
            LoadType.REFRESH,
            pagingState,
        )

        // Assert
        Assert.assertTrue(result is RemoteMediator.MediatorResult.Success)
        Assert.assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun refreshLoad_moreDataPresent_endOfPaginationNotReached() = runTest {
        // Arrange
        oompaLoompaApiService.populateOompaLoompaPages(30)
        val remoteMediator = OompaLoompasRemoteMediator(
            oompaLoompaApiService = oompaLoompaApiService,
            oompaLoompaDatabase,
        )
        val pagingState = PagingState<Int, OompaLoompa>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(25),
            leadingPlaceholderCount = 25,
        )

        // Act
        val result = remoteMediator.load(
            LoadType.REFRESH,
            pagingState,
        )

        // Assert
        Assert.assertTrue(result is RemoteMediator.MediatorResult.Success)
        Assert.assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun refreshLoad_apiThrows_returnsErrorResult() = runTest {
        // Arrange
        oompaLoompaApiService.populateOompaLoompaPages(30)
        oompaLoompaApiService.throwOnNextGetPage()
        val remoteMediator = OompaLoompasRemoteMediator(
            oompaLoompaApiService = oompaLoompaApiService,
            oompaLoompaDatabase,
        )
        val pagingState = PagingState<Int, OompaLoompa>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(25),
            leadingPlaceholderCount = 25,
        )

        // Act
        val result = remoteMediator.load(
            LoadType.REFRESH,
            pagingState,
        )

        // Assert
        Assert.assertTrue(result is RemoteMediator.MediatorResult.Error)
    }
}
