package com.example.todolist.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update

@Entity(tableName = "todo_list")
data class ToDo(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val taskStatus: Boolean,
    val taskName: String
)

@Dao
interface ToDoDao {

    @Insert
    fun addTask(toDo: ToDo)

    @Update
    fun updateTask(toDo: ToDo)

    @Query("SELECT * FROM todo_list")
    fun getAllTasks(): LiveData<List<ToDo>>

    @Query("SELECT * FROM todo_list WHERE taskStatus = 0")
    fun getActiveTasks(): LiveData<List<ToDo>>

    @Query("SELECT * FROM todo_list WHERE taskStatus = 1")
    fun getDoneTasks(): LiveData<List<ToDo>>

    @Delete
    fun deleteTask(toDo: ToDo)
}

@Database(entities = [ToDo::class], version = 1)
abstract class ToDoDatabase : RoomDatabase() {
    abstract fun toDoDao(): ToDoDao
}