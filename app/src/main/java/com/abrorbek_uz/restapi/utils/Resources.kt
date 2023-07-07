package com.abrorbek_uz.restapi.utils

data class Resources<out T>(val status: Status, val data:T?, val message:String?){

    companion object{
        fun <T>success(data:T):Resources<T>{
            return Resources(Status.SUCCESS,data,null)
        }
        fun <T>error(message: String?):Resources<T>{
            return Resources(Status.ERROR,null,message)
        }
        fun <T>loading(message: String?):Resources<T>{
            return Resources(Status.LOADING,null,message)
        }
    }
}