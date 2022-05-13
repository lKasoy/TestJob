package com.example.testjob.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testjob.data.entity.Item
import com.example.testjob.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SomeItemViewModel(
    private val repository: Repository,
    private val key: String
) : ViewModel() {

    private val _someItem = MutableLiveData<Item>()
    val someItem: LiveData<Item> = _someItem

    init {
        subscribeSomeItem()
    }

    private fun subscribeSomeItem() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.someItemFlow.collect {
                _someItem.postValue(it)
            }
        }
    }

    fun renderSomeItem() {
        repository.getSomeItem(key)
    }
}