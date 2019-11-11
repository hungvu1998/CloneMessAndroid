package com.example.clonemessandroid.ui.register

import com.example.clonemessandroid.data.model.UserModel

interface RegisterNavigator {
    fun register()
    fun succes(boolean: Boolean,userModel: UserModel?)

}
