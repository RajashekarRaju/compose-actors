package com.developersbreach.composeactors.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "session_table")
data class SessionEntity(
    @PrimaryKey
    @ColumnInfo(name = "column_session_id")
    val id: Int = 0,
    @ColumnInfo(name = "column_is_guest")
    val isGuest: Boolean,
)