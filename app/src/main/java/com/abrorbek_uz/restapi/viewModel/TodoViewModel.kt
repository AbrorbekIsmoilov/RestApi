package com.abrorbek_uz.restapi.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abrorbek_uz.restapi.models.MyTodo
import com.abrorbek_uz.restapi.models.MyTodoRequest
import com.abrorbek_uz.restapi.repasitory.TodoRepository
import com.abrorbek_uz.restapi.retrofi.ApiClient
import com.abrorbek_uz.restapi.utils.Resources
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception

class TodoViewModel(private val todoRepository: TodoRepository):ViewModel() {

    private val  livedata = MutableLiveData<Resources<List<MyTodo>>>()

    fun getAllTodo():MutableLiveData<Resources<List<MyTodo>>>{
        viewModelScope.launch {
            livedata.postValue(Resources.loading("loading"))
            try {

                coroutineScope {
                    val list = async {
                        todoRepository.getAllTodo()
                    }.await()
                    livedata.postValue(Resources.success(list))
                }

            }catch (e:Exception){
                livedata.postValue(Resources.error("Error"))
            }
        }
        return livedata
    }
    private val postLiveData = MutableLiveData<Resources<MyTodo>>()
    fun addTodo(myTodoRequest: MyTodoRequest):MutableLiveData<Resources<MyTodo>> {

        viewModelScope.launch {
            postLiveData.postValue(Resources.loading("Loading"))
            try {

                    coroutineScope {
                        val response = async {
                        todoRepository.addTodo(myTodoRequest)
                    }.await()
                    postLiveData.postValue(Resources.success(response))
                    getAllTodo()
                }
            } catch (e: Exception) {
                postLiveData.postValue(Resources.error(e.message))
            }
        }
        return postLiveData
    }

}