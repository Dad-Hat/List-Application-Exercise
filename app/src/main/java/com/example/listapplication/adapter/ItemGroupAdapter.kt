package com.example.listapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listapplication.R
import com.example.listapplication.model.ItemGroup

class ItemGroupAdapter(private val groups: List<ItemGroup>):
    RecyclerView.Adapter<ItemGroupAdapter.GroupViewHolder>() {

    class GroupViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val listIdTitle: TextView = view.findViewById(R.id.listIdTitle)
        val recyclerView: RecyclerView = view.findViewById(R.id.itemRecyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.group_row, parent, false)
        return GroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val group = groups[position]
        holder.listIdTitle.text = "List ID: ${group.listId}"
        holder.recyclerView.layoutManager = LinearLayoutManager(holder.itemView.context)
        holder.recyclerView.adapter = ItemAdapter(group.items)
    }

    override fun getItemCount() = groups.size
}