package com.example.todolist

import com.example.todolist.db.ToDoDatabase
import com.example.todolist.db.ToDo
import java.util.concurrent.Executors

class ToDoRepository(private val database: ToDoDatabase) {
    private val executor = Executors.newSingleThreadExecutor()

    fun getAll() = database.toDoDao().getAllTasks()

    fun getActiveTasks() = database.toDoDao().getActiveTasks()

    fun getDoneTasks() = database.toDoDao().getDoneTasks()

    fun update(toDo: ToDo) {
        executor.execute {
            database.toDoDao().updateTask(toDo)
        }
    }

    fun add(toDo: ToDo) {
        executor.execute {
            database.toDoDao().addTask(toDo)
        }
    }

    fun remove(toDo: ToDo) {
        executor.execute {
            database.toDoDao().deleteTask(toDo)
        }
    }

}