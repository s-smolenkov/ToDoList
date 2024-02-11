package com.example.todolist

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ListItemLayoutBinding
import com.example.todolist.db.ToDo

class ToDoListAdapter(private val onTaskStatusChanged: (ToDo) -> Unit) :
    RecyclerView.Adapter<ToDoViewHolder>() {

    var items: List<ToDo> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val binding =
            ListItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToDoViewHolder(binding, onTaskStatusChanged)
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(itemsToUpdate: List<ToDo>) {
        items = itemsToUpdate
        notifyDataSetChanged()
    }
}

class ToDoViewHolder(
    private val binding: ListItemLayoutBinding,
    private val onTaskStatusChanged: (ToDo) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(toDo: ToDo) {
        binding.task.text = toDo.taskName
        binding.checkBox.isChecked = toDo.taskStatus

        updateTextStyle(toDo.taskStatus)

        binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            onTaskStatusChanged(toDo.copy(taskStatus = isChecked))
            updateTextStyle(isChecked)
        }
    }

    private fun updateTextStyle(isChecked: Boolean) {
        if (isChecked) {
            binding.task.paintFlags = binding.task.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            binding.task.paintFlags = binding.task.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }
}
