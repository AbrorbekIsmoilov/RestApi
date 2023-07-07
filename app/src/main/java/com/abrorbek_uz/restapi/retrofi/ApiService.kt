package com.abrorbek_uz.restapi.retrofi

import com.abrorbek_uz.restapi.models.MyTodo
import com.abrorbek_uz.restapi.models.MyTodoRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("plan")
    suspend fun getAllTodo():List<MyTodo>

    @POST("plan/")

    suspend fun addTodo(@Body myTodoRequest: MyTodoRequest):MyTodo
}