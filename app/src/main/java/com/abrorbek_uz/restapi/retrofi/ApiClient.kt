package com.abrorbek_uz.restapi.retrofi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiClient {

    const val BASE_URL = "https://hvax.pythonanywhere.com/"


    fun getRetrofit(): Retrofit{

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    fun getApiService():ApiService{

        return getRetrofit().create(ApiService::class.java)
    }
}