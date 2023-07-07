package com.abrorbek_uz.restapi.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abrorbek_uz.restapi.repasitory.TodoRepository


class MyViewModelFactory(val todoRepository: TodoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            return TodoViewModel(todoRepository) as T
        }
        throw IllegalArgumentException("Error")
    }
}