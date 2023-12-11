package com.pp.todopierre.list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pp.todopierre.R
import com.pp.todopierre.detail.DetailActivity

class TaskListFragment : Fragment() {

    private val adapter = TaskListAdapter()

    private var taskList = listOf(
        Task(id = "id_1", title = "Task 1", description = "description 1"),
        Task(id = "id_2", title = "Task 2"),
        Task(id = "id_3", title = "Task 3")
    )

    val createTask = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = result.data?.getSerializableExtra("task") as Task?
        if (task != null) {
            taskList = taskList + task
            adapter.submitList(taskList)
        }
    }

    val editTask = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val intent = Intent(context, DetailActivity::class.java)

        adapter.submitList(taskList)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = adapter

        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener{

            /*val taskID = UUID.randomUUID().toString()
            val newTask = Task(id = taskID, title = "Task ${taskList.size + 1}")
            taskList = taskList + newTask
            adapter.submitList(taskList)*/

            createTask.launch(intent)
        }


        adapter.onClickDelete = { task ->
            taskList = taskList - task
            adapter.submitList(taskList)
        }

        adapter.onClickDelete = { task ->
            intent.putExtra("task", task)
            editTask.launch(intent)
        }
    }

}
