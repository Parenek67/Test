package com.example.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DictApiViewModelFactory(val rep: DictApiRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DictApiViewModel(rep) as T
    }
}