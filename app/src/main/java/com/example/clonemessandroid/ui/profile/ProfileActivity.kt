package com.example.clonemessandroid.ui.profile

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.clonemessandroid.R
import com.example.clonemessandroid.data.model.UserModel
import com.example.clonemessandroid.viewmodels.ViewModelProvidersFactory
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.layout_profile.*
import kotlinx.android.synthetic.main.progessbar_profile.*
import javax.inject.Inject


class ProfileActivity : DaggerAppCompatActivity(){

    @Inject
    lateinit var providerFactory: ViewModelProvidersFactory

    lateinit var viewModel: ProfileViewModel
    lateinit var userModel: UserModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_profile)
        userModel= intent.getParcelableExtra("userModel")
        txtUserNameEdit?.text=userModel.username
        txtFullName?.text=userModel.fullname
        txtFullNameEdit?.text=userModel.fullname
        Picasso.get().load(userModel.avatar).into(img_profile)
        viewModel= ViewModelProviders.of(this,providerFactory).get(ProfileViewModel::class.java)

        img_back?.setOnClickListener {
            finish()
        }
        subcribeObservers()
    }



    private fun subcribeObservers(){

    }


}