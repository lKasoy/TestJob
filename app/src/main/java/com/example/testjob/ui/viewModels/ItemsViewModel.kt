package com.example.testjob.ui.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testjob.constants.Constants.TAG_FOR_LOGS
import com.example.testjob.data.entity.Item
import com.example.testjob.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception

class ItemsViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _items = MutableLiveData<List<Item>>()
    val items: LiveData<List<Item>> = _items

    init {
        subscribeItemsList()
    }

    private fun subscribeItemsList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.listFlow.collect {
                    _items.postValue(it)
                }
            } catch (e: Exception) {
                Log.d(TAG_FOR_LOGS, e.toString())
            }
        }
    }

    fun readData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.readItems()
            } catch (e: Exception) {
                Log.d(TAG_FOR_LOGS, e.toString())
            }
        }
    }
}