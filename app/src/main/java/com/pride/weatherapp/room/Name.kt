package com.pride.weatherapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Permissions")
data class Name(

    @PrimaryKey(autoGenerate = true)
    val id : Int?,

    @ColumnInfo(name = "name")
    val name: String?,

    @ColumnInfo(name = "confirmation")
    val confirmation : Boolean?
)


