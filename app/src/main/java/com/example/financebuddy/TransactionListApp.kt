package com.example.financebuddy

import android.app.Application
import com.example.financebuddy.data.Graph

class TransactionListApp:Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}