package com.example.clonemessandroid.ui.call

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.clonemessandroid.R
import com.example.clonemessandroid.data.model.UserModel
import com.example.clonemessandroid.viewmodels.ViewModelProvidersFactory
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.layout_call.*
import javax.inject.Inject

class CallActivity : DaggerAppCompatActivity(){

    @Inject
    lateinit var providerFactory: ViewModelProvidersFactory
    lateinit var viewModel: CallViewModel
    lateinit var to: String
    lateinit var imgFriend:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_call)
        viewModel = ViewModelProviders.of(this,providerFactory).get(CallViewModel::class.java)
        to = intent.getStringExtra("to")
        imgFriend = intent.getStringExtra("imgFriend")

        Picasso.get().load(imgFriend).into(img_background)
        Picasso.get().load(imgFriend).into(imgAvatar)
    }
}