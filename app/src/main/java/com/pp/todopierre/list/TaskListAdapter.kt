package com.pp.todopierre.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pp.todopierre.R


object TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task) : Boolean {
        return oldItem.id == newItem.id// comparaison: est-ce la même "entité" ? => même id?
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task) : Boolean {
        return oldItem == newItem// comparaison: est-ce le même "contenu" ? => mêmes valeurs? (avec data class: simple égalité)
    }
}
class TaskListAdapter : ListAdapter<Task, TaskListAdapter.TaskViewHolder>(TaskDiffCallback) {

    var onClickDelete: (Task) -> Unit = {}

    var onClickEdit: (Task) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false))
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
       holder.bind(currentList[position])
    }

    // on utilise `inner` ici afin d'avoir accès aux propriétés de l'adapter directement
    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val taskTitleTextView: TextView = itemView.findViewById(R.id.task_title)
        private val taskDescriptionTextView: TextView = itemView.findViewById(R.id.task_desc)
        fun bind(task: Task) {
            taskTitleTextView.text = task.title
            taskDescriptionTextView.text = task.description

            itemView.findViewById<ImageButton>(R.id.buttondelete).setOnClickListener {
                onClickDelete(task)
            }

            itemView.findViewById<ImageButton>(R.id.buttonedit).setOnClickListener {
                onClickEdit(task)
            }
        }

    }
}