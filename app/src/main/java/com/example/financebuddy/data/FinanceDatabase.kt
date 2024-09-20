package com.example.financebuddy.data

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(
    // Take note about this entity!!!
    entities = [Transaction::class, Saving::class, Travel::class],
    version = 1,
    exportSchema = false
)
abstract class FinanceDatabase : RoomDatabase() {
    abstract fun financeDao(): FinanceDao
}