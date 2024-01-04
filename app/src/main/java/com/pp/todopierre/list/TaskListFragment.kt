package com.pp.todopierre.list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pp.todopierre.R
import com.pp.todopierre.data.Api
import com.pp.todopierre.detail.DetailActivity
import kotlinx.coroutines.launch

interface TaskListListener {
    fun onClickDelete(task: Task)
    fun onClickEdit(task: Task)
}

class TaskListFragment : Fragment() {

    val adapterListener : TaskListListener = object : TaskListListener {
        override fun onClickDelete(task: Task) {
            viewModel.remove(task)
        }
        override fun onClickEdit(task: Task) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("task", task)
            editTask.launch(intent)
        }
    }

    private val adapter = TaskListAdapter(adapterListener)

    private val viewModel: TasksListViewModel by viewModels()

    val createTask = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = result.data?.getSerializableExtra("task") as Task?
        if (task != null) {
            viewModel.add(task)
        }
    }

    val editTask = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = result.data?.getSerializableExtra("task") as Task?
        if (task != null) {
            viewModel.update(task)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lifecycleScope.launch { viewModel.refresh() }

        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = adapter

        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener{

            /*val taskID = UUID.randomUUID().toString()
            val newTask = Task(id = taskID, title = "Task ${taskList.size + 1}")
            taskList = taskList + newTask
            adapter.submitList(taskList)*/
            val intent = Intent(context, DetailActivity::class.java)
            createTask.launch(intent)
        }

        lifecycleScope.launch { // on lance une coroutine car `collect` est `suspend`
            viewModel.tasksStateFlow.collect { newList ->
                // cette lambda est exécutée à chaque fois que la liste est mise à jour dans le VM
                // -> ici, on met à jour la liste dans l'adapter
                adapter.submitList(newList)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            val user = Api.userWebService.fetchUser().body()!!
            view?.findViewById<TextView>(R.id.TVTitre)?.setText(user.name)
            viewModel.refresh() // on demande de rafraîchir les données sans attendre le retour directement
        }
    }
}
