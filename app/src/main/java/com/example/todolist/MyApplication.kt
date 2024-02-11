package com.example.todolist

import android.app.Application
import androidx.room.Room
import com.example.todolist.db.ToDoDatabase

class MyApplication : Application() {
    lateinit var repo: ToDoRepository

    override fun onCreate() {
        super.onCreate()
        instance = this
        val db =
            Room.databaseBuilder(
                this,
                ToDoDatabase::class.java,
                "todo_database"
            ).build()
        repo = ToDoRepository(db)
    }

    companion object {
        private lateinit var instance: MyApplication
        fun getApp() = instance
    }
}