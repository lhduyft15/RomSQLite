package com.khtn.androidcamp

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.home.lhduy.roomsqlite.R
import com.home.lhduy.roomsqlite.Room.TaskEntity
import com.home.lhduy.roomsqlite.Task.TaskItemClickListener
import kotlinx.android.synthetic.main.item_task.view.*
import java.util.*


class TaskAdapter(var items: ArrayList<TaskEntity>, val context: Context) : RecyclerView.Adapter<TaskViewHolder>() {

    lateinit var mListener: TaskItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): TaskViewHolder {
        return TaskViewHolder(LayoutInflater.from(context).inflate(R.layout.item_task, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(taskViewHolder: TaskViewHolder, position: Int) {
        taskViewHolder.tvTaskName.text = "#$position ${items[position].name}"
        taskViewHolder.tvDesriptionTask.text = "${items[position].description}"
        taskViewHolder.tvUserAssigned.text = "Assigned to: ${items[position].userNameAssigned}"
        taskViewHolder.itemView.setOnClickListener {
            mListener.onItemCLicked(position)
        }

        taskViewHolder.itemView.setOnLongClickListener {
            mListener.onItemLongCLicked(position)
            true
        }
    }

    fun setListener(listener: TaskItemClickListener) {
        this.mListener = listener
    }

    fun setData(items: ArrayList<TaskEntity>) {
        this.items = items
    }

    fun appendData(newStudentAdded: TaskEntity) {
        this.items.add(newStudentAdded)
        notifyItemInserted(items.size - 1)
    }

}

class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var tvTaskName = view.tvTaskName
    var tvDesriptionTask = view.tvDescriptionTask
    var tvUserAssigned = view.tvUserAssigned
}
