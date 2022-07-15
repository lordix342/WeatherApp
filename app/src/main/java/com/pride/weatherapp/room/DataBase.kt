package com.pride.weatherapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Name :: class], version = 1)
abstract class DataBase : RoomDatabase() {

    abstract fun permissionDao() : NameDAO

    companion object{

        @Volatile
        private var INSTANCE : DataBase? = null

        fun getDatabase(context: Context): DataBase {

            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                return instance
            }

        }

    }

}