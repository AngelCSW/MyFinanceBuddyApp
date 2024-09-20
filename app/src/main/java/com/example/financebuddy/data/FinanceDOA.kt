package com.example.financebuddy.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
abstract class FinanceDao {

    // Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addATransaction(transactionEntity: Transaction)

    // Loads all transactions from the transaction table
    @Query("Select * from `transaction-table`")
    abstract fun getAllTransactions(): Flow<List<Transaction>>

    @Update
    abstract suspend fun updateATransaction(transactionEntity: Transaction)

    @Delete
    abstract suspend fun deleteATransaction(transactionEntity: Transaction)

    @Query("Select * from `transaction-table` where id=:id")
    abstract fun getATransactionById(id:Long): Flow<Transaction>

    // Saving
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addASaving(savingEntity: Saving)

    // Loads all savings from the saving table
    @Query("Select * from `saving-table`")
    abstract fun getAllSavings(): Flow<List<Saving>>

    @Update
    abstract suspend fun updateASaving(savingEntity: Saving)

    @Delete
    abstract suspend fun deleteASaving(savingEntity: Saving)

    @Query("Select * from `saving-table` where id=:id")
    abstract fun getASavingById(id:Long): Flow<Saving>

    // Travel
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addATravelTransaction(travelEntity: Travel)

    // Loads all travel transactions from the travel table
    @Query("Select * from `travel-table`")
    abstract fun getAllTravelTransactions(): Flow<List<Travel>>

    @Update
    abstract suspend fun updateATravelTransaction(travelEntity: Travel)

    @Delete
    abstract suspend fun deleteATravelTransaction(travelEntity: Travel)

    @Query("Select * from `travel-table` where id=:id")
    abstract fun getATravelTransactionById(id:Long): Flow<Travel>


}