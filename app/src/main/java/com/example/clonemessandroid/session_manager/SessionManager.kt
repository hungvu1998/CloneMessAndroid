package com.example.clonemessandroid.session_manager

import androidx.core.content.ContextCompat.startActivity
import android.content.Intent
import com.example.clonemessandroid.ui.login.LoginActivity
import android.text.method.TextKeyListener.clear
import android.R.id.edit
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.clonemessandroid.data.model.UserModel
import com.example.clonemessandroid.ui.home.HomeActivity
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SessionManager   {

    var editor: SharedPreferences.Editor
     var pref: SharedPreferences
    private val IS_LOGIN = "IsLoggedIn"

    val KEY_ID_USER = "idUser"



    @Inject
    constructor ( pref: SharedPreferences){
        this.pref=pref
        editor=pref.edit()
    }


    fun createLoginSession(idUser: String) {
        editor.putBoolean(IS_LOGIN, true)
        editor.putString(KEY_ID_USER, idUser)
        editor.commit()
    }




    fun getUserId(): String? {
         return pref.getString(KEY_ID_USER,null)
    }

    fun logoutUser(context: Context) {
        editor.clear()
        editor.commit()
        val intent= Intent(context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }
    fun isLoggedIn(): Boolean {
        return pref.getBoolean(IS_LOGIN, false)
    }
}