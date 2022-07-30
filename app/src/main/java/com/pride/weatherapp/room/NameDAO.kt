package com.pride.weatherapp.room

import androidx.room.*
import io.reactivex.rxjava3.core.Flowable

@Dao
interface NameDAO {
    @Query("SELECT * FROM Permissions WHERE name LIKE :text")
    fun findText(text : String) : Flowable<Name>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertToDB(name: Name)

}