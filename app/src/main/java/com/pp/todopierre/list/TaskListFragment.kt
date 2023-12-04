package com.pp.todopierre.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.pp.todopierre.R

class TaskListFragment : Fragment() {

    private var taskList = listOf("Task 1", "Task 2", "Task 3")
    private val adapter = TaskListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter.currentList = taskList

        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView?.adapter = adapter

        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }
}
