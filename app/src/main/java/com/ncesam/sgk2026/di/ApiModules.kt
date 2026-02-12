package com.ncesam.sgk2026.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.ncesam.sgk2026.data.remote.AuthApi
import com.ncesam.sgk2026.data.remote.BookingApi
import com.ncesam.sgk2026.data.remote.HotelApi
import com.ncesam.sgk2026.data.room.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module
import retrofit2.Retrofit


val apiModules = module {
    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create(corruptionHandler = ReplaceFileCorruptionHandler {
            emptyPreferences()
        }, scope = CoroutineScope(Dispatchers.IO + SupervisorJob()), produceFile = {
            get<Context>().preferencesDataStoreFile("settings.preferences_pb")
        })
    }
    single<AppDatabase> {
        Room.databaseBuilder(
            get<Context>(),
            AppDatabase::class.java,
            "AppDatabase"
        ).build()
    }

    single<HotelApi> { get<Retrofit>().create(HotelApi::class.java) }
    single<BookingApi> { get<Retrofit>().create(BookingApi::class.java) }
    single<AuthApi> { get<Retrofit>().create(AuthApi::class.java) }
}