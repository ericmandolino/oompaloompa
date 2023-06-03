package com.oompa.loompa.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "oompa_loompas_extra_details",
    foreignKeys = [
        ForeignKey(
            entity = OompaLoompa::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("id"),
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
data class OompaLoompaExtraDetails(
    @PrimaryKey val id: Long,
    val description: String,
    val quota: String,
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis(),
)
