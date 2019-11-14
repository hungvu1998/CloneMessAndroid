package com.example.clonemessandroid.ui.detail_chat

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.clonemessandroid.R
import com.example.clonemessandroid.data.model.ChatDetailModel
import com.example.clonemessandroid.viewmodels.ViewModelProvidersFactory
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.google.gson.Gson
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.layout_chat_detail.*
import kotlinx.android.synthetic.main.progessbar_chat_detail.*
import org.json.JSONObject
import javax.inject.Inject

class DetailChatActivity : DaggerAppCompatActivity (){
    @Inject
    lateinit var providerFactory: ViewModelProvidersFactory
    lateinit var viewModel: DetailChatViewModel
    var idChat: String?=null
    lateinit var from :String
    lateinit var to :String
    private var socket = IO.socket("https://clonemessage.herokuapp.com/")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_chat_detail)
        idChat = intent.getStringExtra("idChat")
        Log.d("kiemtra",""+idChat)
        from = intent.getStringExtra("from")
        to = intent.getStringExtra("to")
        viewModel = ViewModelProviders.of(this,providerFactory).get(DetailChatViewModel::class.java)

        if(idChat!=null){
            viewModel.getListDetailChat(idChat!!)
        }


        socket.connect()
        socket.on("message", onNewMessage)
        img_back?.setOnClickListener {
            finish()
        }
        img_send?.setOnClickListener {
            if(edtContent?.text.toString().trim()!="")
            sendMessage(edtContent?.text.toString(),1)
        }

        subcribeObservers()
    }

    private fun subcribeObservers(){
        viewModel.liveListChat.observe(this, Observer {it->
            for (item in it){
                viewModel.liveDataChat.value=item
            }

        })
        viewModel.liveDataChat.observe(this, Observer { it->
            Log.d("kiemtra",""+it.content)
        })

    }
    private fun sendMessage(content:String,type :Long){
        var chatDetailModel = ChatDetailModel()
        if (idChat==null){
            idChat = ""
        }
        Log.d("kiemtra",""+idChat)
        chatDetailModel.idChat=idChat
        chatDetailModel.content=content
        chatDetailModel.from=from
        chatDetailModel.to=to
        chatDetailModel.type=type
        val gson = Gson()
        val json = gson.toJson(chatDetailModel)

        socket.emit("message",json)
    }

    override fun onStop() {
        super.onStop()
        socket.disconnect()
    }
    private val onNewMessage =
        Emitter.Listener {
            runOnUiThread {
                val data= it[0] as JSONObject
                idChat= data["idChat"].toString()
                var chatDetailModel = ChatDetailModel()
                chatDetailModel.idChat=idChat
                chatDetailModel.content=data["content"].toString()
                chatDetailModel.from=data["from"].toString()
                chatDetailModel.to=data["to"].toString()
                chatDetailModel.type= data["type"] as Long

                viewModel.liveDataChat.value= chatDetailModel
            }
        }
}