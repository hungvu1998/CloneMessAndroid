package com.example.clonemessandroid.session_manager

import androidx.core.content.ContextCompat.startActivity
import android.content.Intent
import com.example.clonemessandroid.ui.login.LoginActivity
import android.text.method.TextKeyListener.clear
import android.R.id.edit
import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SessionManager  {
    // var editor: SharedPreferences.Editor

    lateinit var pref: SharedPreferences
    private val IS_LOGIN = "IsLoggedIn"
    val KEY_ID_USER = "name"

    @Inject
    constructor () {
       // editor = pref.edit()
    }

    fun createLoginSession(name: String, idUser: String) {
//        editor.putBoolean(IS_LOGIN, true)
//        editor.putString(KEY_ID_USER, idUser)
//        editor.commit()
    }


//    fun checkLogin() {
//        // Check login status
//        if (!this.isLoggedIn()) {
//            val i = Intent(_context, LoginActivity::class.java)
//            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            _context.startActivity(i)
//        }
//
//    }
//
//
//    fun getUserId(): String? {
//         return pref.getString(KEY_ID_USER,null)
//    }
//
//    fun logoutUser() {
//        editor.clear()
//        editor.commit()
//        val i = Intent(_context, LoginActivity::class.java)
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        _context.startActivity(i)
//    }
//    fun isLoggedIn(): Boolean {
//        return pref.getBoolean(IS_LOGIN, false)
//    }
}