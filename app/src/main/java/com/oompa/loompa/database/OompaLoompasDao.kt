package com.oompa.loompa.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.oompa.loompa.model.OompaLoompa

@Dao
interface OompaLoompasDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(oompaLoompas: List<OompaLoompa>)

    @Query("Select * From oompa_loompas Order By id")
    fun getOompaLoompasPagingSource(): PagingSource<Int, OompaLoompa>

    @Query("Delete From oompa_loompas")
    suspend fun clearAllOompaLoompas()
}