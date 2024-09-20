package com.example.financebuddy

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financebuddy.data.Graph
import com.example.financebuddy.data.Transaction
import com.example.financebuddy.data.FinanceRepository
import com.example.financebuddy.data.Travel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TravelViewModel(
    private val financeRepository: FinanceRepository = Graph.financeRepository
):ViewModel() {

    var travelTitleState by mutableStateOf("")
    var travelTypeState by mutableStateOf("")
        public set
    var travelAmountState by mutableStateOf("")
    var travelDescriptionState by mutableStateOf("")


    fun onTravelTransactionTitleChanged(newString:String){
        travelTitleState = newString
    }

    fun onTravelTransactionTypeChanged(newType:String){
        travelTypeState = newType
    }

    fun onTravelTransactionAmountChanged(newDouble: String){
        travelAmountState = newDouble.toString()
    }

    fun onTravelTransactionDescriptionChanged(newString: String){
        travelDescriptionState = newString
    }

    lateinit var getAllTravelTransactions: Flow<List<Travel>>

    init {
        viewModelScope.launch {
            getAllTravelTransactions = financeRepository.getTravelTransactions()
        }
    }

    fun addTravelTransaction(travel: Travel){
        viewModelScope.launch(Dispatchers.IO) {
            financeRepository.addATravelTransaction(travel = travel)
        }
    }

    fun getATravelTransactionById(id:Long):Flow<Travel> {
        return financeRepository.getATravelTransactionById(id)
    }

    fun updateTravelTransaction(travel: Travel){
        viewModelScope.launch(Dispatchers.IO) {
            financeRepository.updateATravelTransaction(travel = travel)
        }
    }

    fun deleteTravelTransaction(travel: Travel){
        viewModelScope.launch(Dispatchers.IO) {
            financeRepository.deleteATravelTransaction(travel = travel)
            getAllTravelTransactions = financeRepository.getTravelTransactions()
        }
    }
}