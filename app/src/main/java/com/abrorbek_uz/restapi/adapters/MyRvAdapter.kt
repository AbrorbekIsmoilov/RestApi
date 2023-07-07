package com.abrorbek_uz.restapi.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.abrorbek_uz.restapi.databinding.ItemRvBinding
import com.abrorbek_uz.restapi.models.MyTodo


class MyRvAdapter(var list: List<MyTodo> = emptyList(),val rvClick: RvClick) :
    RecyclerView.Adapter<MyRvAdapter.Vh>() {

    inner class Vh(private val itemRvBinding: ItemRvBinding) :
        RecyclerView.ViewHolder(itemRvBinding.root) {

        fun onBind(myTodo: MyTodo, position: Int) {
            itemRvBinding.batafsil.text = myTodo.matn
            itemRvBinding.oxirgiMuddat.text = myTodo.oxirgi_muddat
            itemRvBinding.zarurlik.text = myTodo.holat
            itemRvBinding.sarlavha.text = myTodo.sarlavha

            itemRvBinding.more.setOnClickListener {
                rvClick.menuClick(itemRvBinding.more,myTodo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

}
interface RvClick{
    fun menuClick(imageView: ImageView,myTodo: MyTodo)
}