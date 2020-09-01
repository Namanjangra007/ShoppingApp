package com.example.shoppingapp2.ui.Products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Shop by Category!!!!"
    }
    val text: LiveData<String> = _text
}