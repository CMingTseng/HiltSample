package com.kapri.hiltapplication.di

import android.content.Context
import androidx.room.Room
import com.kapri.hiltapplication.data.db.AppDatabase
import com.kapri.hiltapplication.data.db.dao.CountryDao
import com.kapri.hiltapplication.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
//import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//@InstallIn(ApplicationComponent::class)
//https://stackoverflow.com/questions/65988186/error-cannot-find-symbol-dagger-hilt-installinvalue-applicationcomponent-c
//ApplicationComponent is Deprecated in Dagger Version 2.30
//ApplicationComponent removed in Dagger Version 2.31
//Alternatively SingletonComponent should be used instead of ApplicationComponent
@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            Constants.DB_NAME
        ).build()
    }

    @Provides
    fun provideTodoDao(database: AppDatabase): CountryDao {
        return database.getCountryDao()
    }
}