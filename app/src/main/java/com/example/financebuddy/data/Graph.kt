package com.example.financebuddy.data

import android.content.Context
import androidx.room.Room

// Connect repository, database and DOA
object Graph {
    lateinit var database: FinanceDatabase

    val financeRepository by lazy{
        FinanceRepository(financeDao = database.financeDao())
    }

    fun provide(context: Context){
        database = Room.databaseBuilder(context, FinanceDatabase::class.java, "finance.db").build()
    }

}