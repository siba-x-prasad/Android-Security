package com.swasi.androidsecurity.sqlcipher

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.swasi.androidsecurity.R


class ToDoRecyclerAdapter(val list: ArrayList<TodoDto>, context: Context) :
    RecyclerView.Adapter<ToDoRecyclerAdapter.ToDOViewHolder>() {

    val mInflater by lazy {
        LayoutInflater.from(context)
    }

    class ToDOViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val tvTitle = itemview.findViewById<AppCompatTextView>(R.id.tvTitle)
        val tvDef = itemview.findViewById<AppCompatTextView>(R.id.tvDef)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDOViewHolder {
        return ToDOViewHolder(mInflater.inflate(R.layout.itemview_todo, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size!!
    }

    override fun onBindViewHolder(holder: ToDOViewHolder, position: Int) {
        val todo = list[position]
        holder.tvTitle.text = todo.word
        holder.tvDef.text = todo.definition
    }
}