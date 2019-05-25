package com.home.lhduy.roomsqlite

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.home.lhduy.roomsqlite.Room.AppDatabase
import com.home.lhduy.roomsqlite.Room.UserDAO
import com.home.lhduy.roomsqlite.Room.UserDatabase
import com.home.lhduy.roomsqlite.Room.UserEntity
import com.home.lhduy.roomsqlite.User.UserItemClickListener
import kotlinx.android.synthetic.main.activity_user.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule


class UserActivity : AppCompatActivity() {

    var users: ArrayList<UserEntity> = ArrayList()
    var user = UserEntity()
    lateinit var userAdapter : UserAdapter
    lateinit var daoUser : UserDAO
    lateinit var db : UserDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        callDatabase()
        setupRecyclerView()
        getStudents()

        btnSubmitUser.setOnClickListener {

            user.name = tvUserName.text.toString()

            val id = daoUser.insert(user) // insert our student object to ROOM database
            user.id = id.toInt()

            handleOnNewUserAdded(user)

        }

    }

    private fun callDatabase(){
        db = UserDatabase.invoke(this)
        daoUser = db.userDAO()

    }

    private fun setupRecyclerView() {
        rvUsers.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?

        userAdapter = UserAdapter(users, this)

        rvUsers.adapter = userAdapter

        userAdapter.setListener(userItemClickListener)

    }

    private fun getStudents() {
        val userAll = daoUser.getAll() // get Students from ROOM database

        this.users.addAll(userAll) // add to student list

        userAdapter.notifyDataSetChanged() // notify data changed
    }

    private fun handleOnNewUserAdded(user: UserEntity) {
        userAdapter.appendData(user)
    }

    private val userItemClickListener = object : UserItemClickListener {
        override fun onItemCLicked(position: Int) {

        }

        override fun onItemLongCLicked(position: Int) {

            val builder = AlertDialog.Builder(this@UserActivity)
            builder.setTitle("Confirmation")
                .setMessage("Remove ${users[position].name} ?")
                .setPositiveButton("OK") { _, _ ->
                    removeUserItem(position)
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

    private fun removeUserItem(position: Int) {
        daoUser.delete(users[position]) // remove from Room database  //

        users.removeAt(position) // remove student list on RAM

        userAdapter.notifyItemRemoved(position) // notify data change
        Timer(false).schedule(500) {
            runOnUiThread {
                userAdapter.setData(users)
                userAdapter.notifyDataSetChanged()
            }
        }
    }

}
