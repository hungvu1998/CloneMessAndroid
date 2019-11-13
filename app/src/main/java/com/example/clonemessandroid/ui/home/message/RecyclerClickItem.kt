package com.example.clonemessandroid.ui.home.message

import com.example.clonemessandroid.data.model.Stories
import com.example.clonemessandroid.data.model.UserModel


interface RecyclerClickItem {
    fun doThis(userModelFriend: UserModel)
}