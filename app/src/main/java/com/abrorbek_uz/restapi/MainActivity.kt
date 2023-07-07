package com.abrorbek_uz.restapi

import android.R
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.helper.widget.MotionEffect.TAG
import androidx.lifecycle.ViewModelProvider
import com.abrorbek_uz.restapi.adapters.MyRvAdapter
import com.abrorbek_uz.restapi.adapters.RvClick
import com.abrorbek_uz.restapi.databinding.ActivityMainBinding
import com.abrorbek_uz.restapi.databinding.ItemDialogBinding
import com.abrorbek_uz.restapi.models.MyTodo
import com.abrorbek_uz.restapi.models.MyTodoRequest
import com.abrorbek_uz.restapi.repasitory.TodoRepository
import com.abrorbek_uz.restapi.retrofi.ApiClient
import com.abrorbek_uz.restapi.utils.Status
import com.abrorbek_uz.restapi.viewModel.MyViewModelFactory
import com.abrorbek_uz.restapi.viewModel.TodoViewModel
import java.util.Calendar
class MainActivity : AppCompatActivity(),RvClick {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var todoViewModel: TodoViewModel
    private lateinit var todoRepository: TodoRepository
    lateinit var myRvAdapter: MyRvAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        todoRepository = TodoRepository(ApiClient.getApiService())
        todoRepository = TodoRepository(ApiClient.getApiService())
        todoViewModel = ViewModelProvider(this, MyViewModelFactory(todoRepository)).get(TodoViewModel::class.java)

        myRvAdapter = MyRvAdapter(rvClick = this)
        binding.rv.adapter = myRvAdapter
        todoViewModel.getAllTodo()
            .observe(this) {
                when (it.status) {
                    Status.LOADING -> {
                        Log.d(TAG, "onCreate: Loading")
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    Status.ERROR -> {
                        Log.d(TAG, "onCreate: Error ${it.message}")
//                        binding.progressBar.visibility = View.VISIBLE
                        Toast.makeText(this, "Error ${it.message}", Toast.LENGTH_SHORT).show()
                    }

                    Status.SUCCESS -> {
                        Log.d(TAG, "onCreate: ${it.data}")
                        myRvAdapter.list = it.data!!
                        myRvAdapter.notifyDataSetChanged()
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }

        binding.btnAdd.setOnClickListener {
            val dialog = AlertDialog.Builder(this).create()
            val itemDialogBinding = ItemDialogBinding.inflate(layoutInflater)
            dialog.setView(itemDialogBinding.root)
            itemDialogBinding.btnSelectDate.setOnClickListener {
                val currentDate = Calendar.getInstance()
                val year = currentDate.get(Calendar.YEAR)
                val month = currentDate.get(Calendar.MONTH)
                val day = currentDate.get(Calendar.DAY_OF_MONTH)

                val datePicker = DatePickerDialog(
                    this,
                    { _, selectedYear, selectedMonth, selectedDay ->
                        val selectedDate =
                            "$selectedYear-${selectedMonth + 1}-$selectedDay"
                        itemDialogBinding.btnSelectDate.text = selectedDate
                    },
                    year,
                    month,
                    day
                )
                datePicker.show()
            }
            itemDialogBinding.apply {
                btnSave.setOnClickListener {
                val myPostTodoReques = MyTodoRequest(
                    spinnerZarurlik.selectedItem.toString(),
                    editBatafsil.text.toString().trim(),
                    btnSelectDate.text.toString().trim(),
                    editSarlavha.text.toString().trim()
                )
                todoViewModel.addTodo(myPostTodoReques).observe(this@MainActivity){
                    when (it.status) {
                        Status.LOADING -> {
                            Log.d(TAG, "onCreate: Loading")
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        Status.ERROR -> {
                            Log.d(TAG, "onCreate: Error ${it.message}")
                        binding.progressBar.visibility = View.VISIBLE
                            Toast.makeText(this@MainActivity, "Xatolik, ${it.message}", Toast.LENGTH_SHORT).show()
                        }

                        Status.SUCCESS -> {
                            Log.d(TAG, "onCreate: ${it.data}")
                            Toast.makeText(this@MainActivity, "${it.data?.sarlavha},${it.data}", Toast.LENGTH_SHORT).show()
                            myRvAdapter.notifyDataSetChanged()
                            binding.progressBar.visibility = View.GONE
                            dialog.cancel()
                        }
                    }
                }



                }

            }
            dialog.show()
        }
    }

    override fun menuClick(imageView: ImageView, myTodo: MyTodo) {

        val popup = PopupMenu(this,imageView)
        popup.inflate(com.abrorbek_uz.restapi.R.menu.todo_menu)

        popup.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.edit->{

                }
                R.id.edit->{

                }

            }
            true
        }
        popup.show()

    }
}

