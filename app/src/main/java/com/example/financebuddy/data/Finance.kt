package com.example.financebuddy.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="transaction-table")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name="transaction-title")
    val title: String="",
    @ColumnInfo(name="transaction-type")
    val type: String="",
    @ColumnInfo(name="transaction-amount")
    val amount: Double = 0.00,
    @ColumnInfo(name="transaction-desc")
    val description:String=""
)

@Entity(tableName="saving-table")
data class Saving(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name="saving-title")
    val title: String="",
    @ColumnInfo(name="saving-amount")
    val amount: Double = 0.00,
    @ColumnInfo(name="saving-desc")
    val description:String=""
)

@Entity(tableName="travel-table")
data class Travel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name="travel-title")
    val title: String="",
    @ColumnInfo(name="travel-type")
    val type: String="",
    @ColumnInfo(name="travel-amount")
    val amount: Double = 0.00,
    @ColumnInfo(name="travel-desc")
    val description:String=""
)