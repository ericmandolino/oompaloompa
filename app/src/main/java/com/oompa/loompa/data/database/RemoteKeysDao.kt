package com.oompa.loompa.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.oompa.loompa.data.model.RemoteKeys

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<RemoteKeys>)

    @Query("Select * From remote_keys Where oompa_loompa_id = :id")
    suspend fun getRemoteKeyByOompaLoompaId(id: Long): RemoteKeys?

    @Query("Delete From remote_keys")
    suspend fun clearRemoteKeys()

    @Query("Select created_at From remote_keys Order By created_at DESC LIMIT 1")
    suspend fun getCreationTime(): Long?
}