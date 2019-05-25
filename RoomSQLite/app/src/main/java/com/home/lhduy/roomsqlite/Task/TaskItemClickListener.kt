package com.home.lhduy.roomsqlite.Task

interface TaskItemClickListener {
    fun onItemCLicked(position: Int)
    fun onItemLongCLicked(position: Int)
    fun onEditIconClicked(position: Int)
}