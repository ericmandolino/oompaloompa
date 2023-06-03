package com.oompa.loompa.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.oompa.loompa.data.model.OompaLoompa
import com.oompa.loompa.data.model.OompaLoompaExtraDetails
import com.oompa.loompa.data.model.RemoteKeys

@Database(
    entities = [OompaLoompa::class, OompaLoompaExtraDetails::class, RemoteKeys::class],
    version = 2,
)
abstract class OompaLoompaDatabase: RoomDatabase() {
    abstract fun getOompaLoompasDao(): OompaLoompasDao
    abstract fun getRemoteKeysDao(): RemoteKeysDao
}