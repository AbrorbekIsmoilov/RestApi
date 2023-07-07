package com.abrorbek_uz.restapi.repasitory

import com.abrorbek_uz.restapi.models.MyTodoRequest
import com.abrorbek_uz.restapi.retrofi.ApiService


class TodoRepository(val apiService: ApiService) {
    suspend fun getAllTodo() = apiService.getAllTodo()
    suspend fun addTodo(myTodoRequest: MyTodoRequest) = apiService.addTodo(myTodoRequest)
}