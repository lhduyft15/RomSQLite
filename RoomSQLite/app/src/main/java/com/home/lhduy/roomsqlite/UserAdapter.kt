package com.home.lhduy.roomsqlite

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.home.lhduy.roomsqlite.Room.TaskEntity
import com.home.lhduy.roomsqlite.Room.UserEntity
import com.home.lhduy.roomsqlite.Task.TaskItemClickListener
import com.home.lhduy.roomsqlite.User.UserItemClickListener
import kotlinx.android.synthetic.main.item_student.view.*

class UserAdapter(var items: ArrayList<UserEntity>, val context: Context): RecyclerView.Adapter<UserViewHolder>() {

    lateinit var mListener: UserItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): UserViewHolder {
       return UserViewHolder(LayoutInflater.from(context).inflate(R.layout.item_student, parent,false))
    }

    override fun onBindViewHolder(userViewHolder : UserViewHolder, position: Int) {
       userViewHolder.tvUserNameItem.text = "${items[position].name}"
        userViewHolder.itemView.setOnClickListener{
            mListener.onItemCLicked(position)
        }

        userViewHolder.itemView.setOnLongClickListener{
            mListener.onItemLongCLicked(position)
            true
        }
    }


    override fun getItemCount(): Int {
        return items.size
    }

    fun setData(items: java.util.ArrayList<UserEntity>) {
        this.items = items
    }

    fun appendData(newUserAdded: UserEntity) {
        this.items.add(newUserAdded)
        notifyItemInserted(items.size - 1)
    }

    fun setListener(listener: UserItemClickListener) {
        this.mListener = listener
    }
}
class UserViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var tvUserNameItem = view.tvUserNameItem
}