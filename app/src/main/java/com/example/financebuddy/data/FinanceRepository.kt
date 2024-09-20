package com.example.financebuddy.data

import kotlinx.coroutines.flow.Flow


class FinanceRepository(private val financeDao: FinanceDao) {

    // Transaction
    suspend fun addATransaction(transaction: Transaction){
        financeDao.addATransaction(transaction)
    }

    fun getTransactions(): Flow<List<Transaction>> = financeDao.getAllTransactions()

    fun getATransactionById(id:Long) :Flow<Transaction> {
        return financeDao.getATransactionById(id)
    }

    suspend fun updateATransaction(transaction: Transaction){
        financeDao.updateATransaction(transaction)
    }

    suspend fun deleteATransaction(transaction: Transaction){
        financeDao.deleteATransaction(transaction)
    }

    // Saving
    suspend fun addASaving(saving: Saving){
        financeDao.addASaving(saving)
    }

    fun getSavings(): Flow<List<Saving>> = financeDao.getAllSavings()

    fun getASavingById(id:Long) :Flow<Saving> {
        return financeDao.getASavingById(id)
    }

    suspend fun updateASaving(saving: Saving){
        financeDao.updateASaving(saving)
    }

    suspend fun deleteASaving(saving: Saving) {
        financeDao.deleteASaving(saving)
    }

    // Travel
    suspend fun addATravelTransaction(travel: Travel){
        financeDao.addATravelTransaction(travel)
    }

    fun getTravelTransactions(): Flow<List<Travel>> = financeDao.getAllTravelTransactions()

    fun getATravelTransactionById(id:Long) :Flow<Travel> {
        return financeDao.getATravelTransactionById(id)
    }

    suspend fun updateATravelTransaction(travel: Travel){
        financeDao.updateATravelTransaction(travel)
    }

    suspend fun deleteATravelTransaction(travel: Travel) {
        financeDao.deleteATravelTransaction(travel)
    }

}