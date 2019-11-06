package com.example.clonemessandroid.ui.home.message

import com.example.clonemessandroid.data.model.Stories


interface RecyclerClickItem {
    fun doThis(array: ArrayList<Stories>)
}