package com.example.clonemessandroid.ui.detail_chat

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.clonemessandroid.R
import com.example.clonemessandroid.viewmodels.ViewModelProvidersFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class DetailChatActivity : DaggerAppCompatActivity (){
    @Inject
    lateinit var providerFactory: ViewModelProvidersFactory
    lateinit var viewModel: DetailChatViewModel
    lateinit var idChat: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_chat_detail)
        idChat = intent.getStringExtra("idChat")
        viewModel = ViewModelProviders.of(this,providerFactory).get(DetailChatViewModel::class.java)

        subcribeObservers()
    }

    private fun subcribeObservers(){

    }


}