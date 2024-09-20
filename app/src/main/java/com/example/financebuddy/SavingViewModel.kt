package com.example.financebuddy

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financebuddy.data.Graph
import com.example.financebuddy.data.Transaction
import com.example.financebuddy.data.FinanceRepository
import com.example.financebuddy.data.Saving
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SavingViewModel(
    private val financeRepository: FinanceRepository = Graph.financeRepository
):ViewModel() {

    var savingTitleState by mutableStateOf("")
    var savingAmountState by mutableStateOf("")
    var savingDescriptionState by mutableStateOf("")


    fun onSavingTitleChanged(newString:String){
        savingTitleState = newString
    }

    fun onSavingAmountChanged(newDouble: String){
        savingAmountState = newDouble.toString()
    }

    fun onSavingDescriptionChanged(newString: String){
        savingDescriptionState = newString
    }

    lateinit var getAllSavings: Flow<List<Saving>>

    init {
        viewModelScope.launch {
            getAllSavings = financeRepository.getSavings()
        }
    }

    fun addSaving(saving: Saving){
        viewModelScope.launch(Dispatchers.IO) {
            financeRepository.addASaving(saving = saving)
        }
    }

    fun getASavingById(id:Long):Flow<Saving> {
        return financeRepository.getASavingById(id)
    }

    fun updateSaving(saving: Saving){
        viewModelScope.launch(Dispatchers.IO) {
            financeRepository.updateASaving(saving = saving)
        }
    }

    fun deleteSaving(saving: Saving){
        viewModelScope.launch(Dispatchers.IO) {
            financeRepository.deleteASaving(saving = saving)
            getAllSavings = financeRepository.getSavings()
        }
    }
}