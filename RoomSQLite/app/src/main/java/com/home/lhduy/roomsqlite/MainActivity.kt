package com.home.lhduy.roomsqlite

import android.annotation.SuppressLint
import android.arch.persistence.room.Room
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.schedule
import android.app.Activity
import android.util.Log
import com.home.lhduy.roomsqlite.Room.*
import com.home.lhduy.roomsqlite.Task.TaskItemClickListener
import com.khtn.androidcamp.TaskAdapter
import kotlinx.android.synthetic.main.activity_main.tvTaskName
import kotlinx.android.synthetic.main.item_task.*
import kotlin.collections.ArrayList


@SuppressLint("SetTextI18n")


class MainActivity : AppCompatActivity() {


    var tasks : ArrayList<TaskEntity> = ArrayList()
    var task = TaskEntity()


    lateinit var taskAdapter: TaskAdapter
    lateinit var daoTask: TaskDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRoomDatabase()

        setupRecyclerView()

        getStudents()

        getAndPutData()


        btnSubmitTask.setOnClickListener {
            // FIXME:  validate empty input value
            task.name = tvTaskName.text.toString()
            task.description = tvDescription.text.toString()
            task.completed = false
            task.userNameAssigned = "Unassigned"
            val id = daoTask.insert(task) // insert our task object to ROOM database
            task.id = id.toInt()

            handleOnNewTaskAdded(task)

        }
        tvAddUser.setOnClickListener {
            val intent = Intent(this@MainActivity,UserActivity::class.java)
            startActivity(intent)
        }

    }

    private fun initRoomDatabase() {
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, DATABASE_APP
        ).allowMainThreadQueries()
            .build()
        daoTask = db.taskDAO()
    }

    /**
     * setup layout manager and recyclerview
     */
    private fun setupRecyclerView() {
        rvTasks.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?

        taskAdapter = TaskAdapter(tasks, this)

        rvTasks.adapter = taskAdapter

        taskAdapter.setListener(taskItemCLickListener)
    }

    private val taskItemCLickListener = object : TaskItemClickListener {
        override fun onItemCLicked(position: Int) {

            val intent = Intent(this@MainActivity, ProfileActivity::class.java)
            intent.putExtra(TASK_NAME_KEY, tasks[position].name)
            intent.putExtra(TASK_COMPLETED_KEY,tasks[position].completed)
            intent.putExtra(TASK_NAME_ASSIGNED,tasks[position].userNameAssigned)
            intent.putExtra(TASK_ID,tasks[position].id)
            intent.putExtra(TASK_POSITION_KEY, position)
            intent.putExtra(TASK_DESCRIPTION,tasks[position].description)
            startActivity(intent)
        }

        override fun onItemLongCLicked(position: Int) {

            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setTitle("Confirmation")
                .setMessage("Remove ${tasks[position].name} ?")
                .setPositiveButton("OK") { _, _ ->
                    removeTaskItem(position)
                }
                .setNegativeButton(
                    "Cancel"
                ) { dialog, _ -> dialog?.dismiss() }

            val myDialog = builder.create();
            myDialog.show()
        }

        override fun onEditIconClicked(position: Int) {

        }
    }


    private fun removeTaskItem(position: Int) {
        daoTask.delete(tasks[position]) // remove from Room database  //

        tasks.removeAt(position) // remove student list on RAM

        taskAdapter.notifyItemRemoved(position) // notify data change
        Timer(false).schedule(500) {
            runOnUiThread {
                taskAdapter.setData(tasks)
                taskAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun updateItem(position: Int, task: TaskEntity) {
        daoTask.update(task)

        tasks.set(position, task)

        taskAdapter.notifyItemChanged(position)
        Timer(false).schedule(500) {
            runOnUiThread {
                taskAdapter.setData(tasks)
                taskAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun getStudents() {
        val tasks = daoTask.getAll() // get Students from ROOM database

        this.tasks.addAll(tasks) // add to student list

        taskAdapter.notifyDataSetChanged() // notify data changed
    }

    private fun getAndPutData(){
        val data = intent.extras
        if (data != null){
            val test = data.getInt(TASK_DELETE_KEY)
            val position = data.getInt(TASK_POSITION_KEY)

            if (test == 0) {
                val id = data.getInt(TASK_ID)
                val nameTask = data.getString(TASK_NAME_KEY)
                val completed = data.getBoolean(TASK_COMPLETED_KEY)
                val assigned = data.getString(TASK_NAME_ASSIGNED)
                val description = data.getString(TASK_DESCRIPTION)
                if (position != null && nameTask != null && completed != null && assigned != null) {
                    updateItem(position, TaskEntity(id, description,nameTask, completed, assigned))
                    Log.e(
                        "Main_onActivityResult: ",
                        "user:" + assigned + " - id:" + id.toString() + " - com:" + completed.toString()
                    )
                }
            } else {
                if (position != null)
                    removeTaskItem(position)
            }
        }
    }
    /**
     * Update the item
     */
    private fun handleOnStudentUpdated(newStudentAdded: UserEntity) {
        // TODO : Update the updated item infomation

    }

    /**
     * append new data to student list and notify data change
     */

    private fun handleOnNewTaskAdded(task: TaskEntity) {
        taskAdapter.appendData(task)
    }
}




