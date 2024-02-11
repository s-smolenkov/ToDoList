package com.example.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.databinding.InputFragmentLayoutBinding

class AddToDoFragment : Fragment() {

    private lateinit var viewModel: ToDoViewModel
    private lateinit var binding: InputFragmentLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = InputFragmentLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ToDoViewModel::class.java)

        binding.addButton.setOnClickListener {
            val task = binding.taskInputField.text.toString()
            viewModel.addTask(task)
            parentFragmentManager.popBackStack()
        }
    }
}