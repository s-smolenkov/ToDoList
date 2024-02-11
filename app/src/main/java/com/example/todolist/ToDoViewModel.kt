package com.example.todolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.todolist.db.ToDo

class ToDoViewModel : ViewModel() {
    private val repo = MyApplication.getApp().repo
    private val _listState = MutableLiveData<ListState>(ListState.EmptyList)
    val listState: LiveData<ListState> = _listState

    val activeTasks: LiveData<List<ToDo>> = repo.getActiveTasks()
    val doneTasks: LiveData<List<ToDo>> = repo.getDoneTasks()

    private val observer = Observer<List<ToDo>> {
        _listState.postValue(ListState.UpdatedList(list = it))
    }

    init {
        repo.getAll().observeForever(observer)
    }

    fun addTask(name: String) {
        repo.add(ToDo(taskStatus = false, taskName = name))
    }

    fun updateTaskStatus(toDo: ToDo) {
        repo.update(toDo.copy(taskStatus = !toDo.taskStatus))
    }

    fun removeTask(toDo: ToDo) {
        repo.remove(toDo)
    }

    override fun onCleared() {
        repo.getAll().removeObserver(observer)
        super.onCleared()
    }

    sealed class ListState {
        object EmptyList : ListState()
        class UpdatedList(val list: List<ToDo>) : ListState()
    }
}