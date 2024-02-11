package com.example.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ListFragmentLayoutBinding

class ListFragment : Fragment() {

    private lateinit var viewModel: ToDoViewModel
    private lateinit var binding: ListFragmentLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ListFragmentLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ToDoViewModel::class.java)

        val activeAdapter = ToDoListAdapter(viewModel::updateTaskStatus)
        val doneAdapter = ToDoListAdapter(viewModel::updateTaskStatus)

        binding.todoList.layoutManager = LinearLayoutManager(requireContext())
        binding.todoList.adapter = activeAdapter
        binding.doneList.layoutManager = LinearLayoutManager(requireContext())
        binding.doneList.adapter = doneAdapter

        viewModel.activeTasks.observe(viewLifecycleOwner) { activeTasks ->
            activeAdapter.updateItems(activeTasks)
            binding.todoTextview.visibility = if (activeTasks.isEmpty()) View.GONE else View.VISIBLE
        }

        viewModel.doneTasks.observe(viewLifecycleOwner) { doneTasks ->
            doneAdapter.updateItems(doneTasks)
            binding.doneTextview.visibility = if (doneTasks.isEmpty()) View.GONE else View.VISIBLE
        }

        binding.fabButton.setOnClickListener {
            val fragment = AddToDoFragment()
            parentFragmentManager.beginTransaction()
                .add(R.id.container, fragment)
                .addToBackStack(fragment.javaClass.name)
                .commit()
        }

        val itemTouchHelperToDo = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int = makeMovementFlags(0, ItemTouchHelper.END)

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (direction == ItemTouchHelper.END) {
                    viewModel.removeTask(activeAdapter.items[viewHolder.adapterPosition])
                }
            }
        })
        itemTouchHelperToDo.attachToRecyclerView(binding.todoList)

        val itemTouchHelperDone = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int = makeMovementFlags(0, ItemTouchHelper.END)

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (direction == ItemTouchHelper.END) {
                    viewModel.removeTask(doneAdapter.items[viewHolder.adapterPosition])
                }
            }
        })
        itemTouchHelperDone.attachToRecyclerView(binding.doneList)
    }
}