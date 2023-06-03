package com.oompa.loompa.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.oompa.loompa.model.OompaLoompa
import com.oompa.loompa.model.OompaLoompaExtraDetails
import com.oompa.loompa.model.RemoteKeys

@Database(
    entities = [OompaLoompa::class, OompaLoompaExtraDetails::class, RemoteKeys::class],
    version = 2,
)
abstract class OompaLoompaDatabase: RoomDatabase() {
    abstract fun getOompaLoompasDao(): OompaLoompasDao
    abstract fun getRemoteKeysDao(): RemoteKeysDao
}