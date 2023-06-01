package com.oompa.loompa.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.oompa.loompa.model.OompaLoompa
import kotlinx.coroutines.flow.Flow

@Dao
interface OompaLoompasDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(oompaLoompas: List<OompaLoompa>)

    @Query("Delete From oompa_loompas")
    suspend fun clearAllOompaLoompas()

    @Query("Select Distinct profession From oompa_loompas")
    fun getProfessions(): Flow<List<String>>

    @RawQuery(observedEntities = [OompaLoompa::class])
    fun getOompaLoompas(query: SupportSQLiteQuery): PagingSource<Int, OompaLoompa>

    fun createOompaLoompasQuery(genders: List<String>, professions: List<String>): SupportSQLiteQuery {
        val whereBuilder = StringBuilder()

        if (genders.isNotEmpty()) {
            addWhereFilterClause(whereBuilder, "gender in (${genders.joinToString { "'$it'" }})")
        }

        if (professions.isNotEmpty()) {
            addWhereFilterClause(whereBuilder, "profession in (${professions.joinToString { "'$it'" }})")
        }

        return if (whereBuilder.isNotEmpty()) {
            SimpleSQLiteQuery("Select * From oompa_loompas where $whereBuilder Order By id")
        } else {
            SimpleSQLiteQuery("Select * From oompa_loompas Order By id")
        }
    }

    private fun addWhereFilterClause(filterBuilder: StringBuilder, filter: String) {
        if (filterBuilder.isNotEmpty()) {
            filterBuilder.append(" and ")
        }
        filterBuilder.append(filter)
    }
}