package com.oompa.loompa.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.oompa.loompa.data.model.OompaLoompa
import com.oompa.loompa.data.model.OompaLoompaExtraDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface OompaLoompasDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(oompaLoompas: List<OompaLoompa>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(oompaLoompaExtraDetails: OompaLoompaExtraDetails)

    @Query("Delete From oompa_loompas")
    suspend fun clearAllOompaLoompas()

    @Query("Select Distinct profession From oompa_loompas")
    fun observeProfessions(): Flow<List<String>>

    @Query("Select * From oompa_loompas_extra_details Where id=:oompaLoompaId")
    suspend fun getOompaLoompaExtraDetails(oompaLoompaId: Long): OompaLoompaExtraDetails?

    @Query("Select * From oompa_loompas Where id=:oompaLoompaId")
    fun observeOompaLoompa(oompaLoompaId: Long): Flow<OompaLoompa>

    @Query("Select * From oompa_loompas_extra_details Where id=:oompaLoompaId")
    fun observeOompaLoompaExtraDetails(oompaLoompaId: Long): Flow<OompaLoompaExtraDetails>

    @RawQuery(observedEntities = [OompaLoompa::class])
    fun getOompaLoompas(query: SupportSQLiteQuery): PagingSource<Int, OompaLoompa>
}