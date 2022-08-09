package com.pride.weatherapp.di

import android.content.Context
import androidx.appcompat.widget.ActivityChooserView
import com.pride.weatherapp.room.DataBase
import com.pride.weatherapp.server.ApiRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@InstallIn(SingletonComponent::class)
@Module
class DataModule {
    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext applicationContext: Context): DataBase {
        return DataBase.getDatabase(applicationContext)
    }
    @Provides
    @Singleton
    fun provideApiRepo(): ApiRepo {
        return ApiRepo()
    }
}