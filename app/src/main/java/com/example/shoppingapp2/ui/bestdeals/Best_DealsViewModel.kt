package com.example.shoppingapp2.ui.bestdeals

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class Best_DealsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Best Deals"
    }
    val text: LiveData<String> = _text
}