package com.oompa.loompa.data.database

import android.content.Context
import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.oompa.loompa.TestUtil
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class OompaLoompaDaoTest {
    private lateinit var oompaLoompaDao: OompaLoompasDao
    private lateinit var oompaLoompaDatabase: OompaLoompaDatabase

    @Before
    fun createDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        oompaLoompaDatabase = Room.inMemoryDatabaseBuilder(
            context, OompaLoompaDatabase::class.java).build()
        oompaLoompaDao = oompaLoompaDatabase.getOompaLoompasDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        oompaLoompaDatabase.close()
    }

    @Test
    fun getOompaLoompas_noFiltering_returnsAllResults() = runTest {
        // Arrange
        val genders = listOf<String>()
        val professions = listOf<String>()
        val oompaLoompa1 = TestUtil.createOompaLoompa(1, gender = "M", profession = "Driver")
        val oompaLoompa2 = TestUtil.createOompaLoompa(2, gender = "F", profession = "Driver")
        val oompaLoompa3 = TestUtil.createOompaLoompa(3, gender = "M", profession = "Cook")
        oompaLoompaDao.insertAll(listOf(oompaLoompa1, oompaLoompa2, oompaLoompa3))

        // Act
        val oompaLoompas = oompaLoompaDao.getOompaLoompas(genders, professions)
        val loadResult = oompaLoompas.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 3,
                placeholdersEnabled = false,
            )
        )

        // Assert
        Assert.assertEquals(
            PagingSource.LoadResult.Page(
                data = listOf(oompaLoompa1, oompaLoompa2, oompaLoompa3),
                prevKey = null,
                nextKey = null,
                itemsBefore = 0,
                itemsAfter = 0,
            ),
            loadResult,
        )
    }

    @Test
    fun getOompaLoompas_filterByGender_returnsFilteredResults() = runTest {
        // Arrange
        val genders = listOf("M")
        val professions = listOf<String>()
        val oompaLoompa1 = TestUtil.createOompaLoompa(1, gender = "M", profession = "Driver")
        val oompaLoompa2 = TestUtil.createOompaLoompa(2, gender = "F", profession = "Driver")
        val oompaLoompa3 = TestUtil.createOompaLoompa(3, gender = "M", profession = "Cook")
        oompaLoompaDao.insertAll(listOf(oompaLoompa1, oompaLoompa2, oompaLoompa3))

        // Act
        val oompaLoompas = oompaLoompaDao.getOompaLoompas(genders, professions)
        val loadResult = oompaLoompas.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 3,
                placeholdersEnabled = false,
            )
        )

        // Assert
        Assert.assertEquals(
            PagingSource.LoadResult.Page(
                data = listOf(oompaLoompa1, oompaLoompa3),
                prevKey = null,
                nextKey = null,
                itemsBefore = 0,
                itemsAfter = 0,
            ),
            loadResult,
        )
    }

    @Test
    fun getOompaLoompas_filterByProfession_returnsFilteredResults() = runTest {
        // Arrange
        val genders = listOf<String>()
        val professions = listOf("Driver")
        val oompaLoompa1 = TestUtil.createOompaLoompa(1, gender = "M", profession = "Driver")
        val oompaLoompa2 = TestUtil.createOompaLoompa(2, gender = "F", profession = "Driver")
        val oompaLoompa3 = TestUtil.createOompaLoompa(3, gender = "M", profession = "Cook")
        oompaLoompaDao.insertAll(listOf(oompaLoompa1, oompaLoompa2, oompaLoompa3))

        // Act
        val oompaLoompas = oompaLoompaDao.getOompaLoompas(genders, professions)
        val loadResult = oompaLoompas.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 3,
                placeholdersEnabled = false,
            )
        )

        // Assert
        Assert.assertEquals(
            PagingSource.LoadResult.Page(
                data = listOf(oompaLoompa1, oompaLoompa2),
                prevKey = null,
                nextKey = null,
                itemsBefore = 0,
                itemsAfter = 0,
            ),
            loadResult,
        )
    }

    fun getOompaLoompas_filterByGenderAndProfession_returnsFilteredResults() = runTest {
        // Arrange
        val genders = listOf("F")
        val professions = listOf("Driver")
        val oompaLoompa1 = TestUtil.createOompaLoompa(1, gender = "M", profession = "Driver")
        val oompaLoompa2 = TestUtil.createOompaLoompa(2, gender = "F", profession = "Driver")
        val oompaLoompa3 = TestUtil.createOompaLoompa(3, gender = "M", profession = "Cook")
        oompaLoompaDao.insertAll(listOf(oompaLoompa1, oompaLoompa2, oompaLoompa3))

        // Act
        val oompaLoompas = oompaLoompaDao.getOompaLoompas(genders, professions)
        val loadResult = oompaLoompas.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 3,
                placeholdersEnabled = false,
            )
        )

        // Assert
        Assert.assertEquals(
            PagingSource.LoadResult.Page(
                data = listOf(oompaLoompa2),
                prevKey = null,
                nextKey = null,
                itemsBefore = 0,
                itemsAfter = 0,
            ),
            loadResult,
        )
    }
}