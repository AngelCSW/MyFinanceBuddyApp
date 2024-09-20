package com.example.financebuddy

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financebuddy.data.Graph
import com.example.financebuddy.data.Transaction
import com.example.financebuddy.data.FinanceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TransactionViewModel(
    private val financeRepository: FinanceRepository = Graph.financeRepository
):ViewModel() {

    var transactionTitleState by mutableStateOf("")
    var transactionTypeState by mutableStateOf("")
        public set
    var transactionAmountState by mutableStateOf("")
    var transactionDescriptionState by mutableStateOf("")


    fun onTransactionTitleChanged(newString:String){
        transactionTitleState = newString
    }

    fun onTransactionTypeChanged(newType:String){
        transactionTypeState = newType
    }

    fun onTransactionAmountChanged(newDouble: String){
        transactionAmountState = newDouble.toString()
    }

    fun onTransactionDescriptionChanged(newString: String){
        transactionDescriptionState = newString
    }

    lateinit var getAllTransaction: Flow<List<Transaction>>

    init {
        viewModelScope.launch {
            getAllTransaction = financeRepository.getTransactions()
        }
    }

    fun addTransaction(transaction: Transaction){
        viewModelScope.launch(Dispatchers.IO) {
            financeRepository.addATransaction(transaction = transaction)
        }
    }

    fun getATransactionById(id:Long):Flow<Transaction> {
        return financeRepository.getATransactionById(id)
    }

    fun updateTransaction(transaction: Transaction){
        viewModelScope.launch(Dispatchers.IO) {
            financeRepository.updateATransaction(transaction = transaction)
        }
    }

    fun deleteTransaction(transaction: Transaction){
        viewModelScope.launch(Dispatchers.IO) {
            financeRepository.deleteATransaction(transaction = transaction)
            getAllTransaction = financeRepository.getTransactions()
        }
    }

}