package com.home.lhduy.roomsqlite

import android.app.Activity
import android.arch.persistence.room.Room
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.bumptech.glide.Glide
import com.home.lhduy.roomsqlite.Room.*
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    lateinit var db : AppDatabase
    lateinit var dbUser: UserDatabase
    var userAssign = "Unassigned"
    var userAssigned = "Unassigned"
    var taskID = -1
    var taskPosition = -1
    var nameTask = ""
    var description = ""

    var users : ArrayList<UserEntity> = ArrayList()
    var names : ArrayList<String> = ArrayList()
    lateinit var userDAO : UserDAO
    var task = TaskEntity()
    lateinit var taskDAO : TaskDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        initUserDatabase()

        db = AppDatabase.invoke(this)
        dbUser = UserDatabase.invoke(this)

        //Get data
        taskDAO = db.taskDAO()


        var userAll = userDAO.getAll()
        this.users.addAll(userAll)
        for(element in users){
            names.add(element.name)
        }

        getAndPutData()
        setUpSpinner()

        btnSave.setOnClickListener {
            val intent = Intent(this@ProfileActivity, MainActivity::class.java)
            intent.putExtra(TASK_POSITION_KEY, taskPosition)
            intent.putExtra(TASK_ID, taskID)
            intent.putExtra(TASK_NAME_KEY,  tvTaskNameDetail.text )
            intent.putExtra(TASK_COMPLETED_KEY, checkBox.isChecked)
            intent.putExtra(TASK_NAME_ASSIGNED, userAssign)
            intent.putExtra(TASK_DELETE_KEY, 0)
            intent.putExtra(TASK_DESCRIPTION,description)
            startActivity(intent)
        }
    }

    private fun initUserDatabase() {
        val dbUser = Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java, DATABASE_USER
        ).allowMainThreadQueries()
            .build()
        userDAO = dbUser.userDAO()
    }

    private fun getAndPutData() {
        val data = intent.extras
        if (data != null) {

            nameTask = data.getString(TASK_NAME_KEY)
            val completed = data.getBoolean(TASK_COMPLETED_KEY)
            description = data.getString(TASK_DESCRIPTION)
            userAssigned = data.getString(TASK_NAME_ASSIGNED)
            taskID = data.getInt(TASK_ID)
            taskPosition = data.getInt(TASK_POSITION_KEY)

            tvTaskNameDetail.text = nameTask
            checkBox.isChecked = completed
            tvuserNameAssigned.text = userAssigned

        }
    }

    private fun setUpSpinner(){
        val spinner = findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, names)
            spinner.adapter = arrayAdapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    userAssign = names[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    tvuserNameAssigned.text = userAssigned
                }
            }
        }
    }
}
