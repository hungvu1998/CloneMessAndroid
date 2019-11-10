package com.example.clonemessandroid.ui.login

import com.example.clonemessandroid.data.model.UserModel


interface LoginNavigator {
    fun login()
    fun succes(boolean: Boolean,userModel: UserModel?)

}
