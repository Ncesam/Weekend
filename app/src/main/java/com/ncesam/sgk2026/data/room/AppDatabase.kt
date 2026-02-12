package com.ncesam.sgk2026.data.room

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [CartItemEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun shopCartDao(): ShopCartDao
}