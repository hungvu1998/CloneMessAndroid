package com.example.clonemessandroid.ui.detail_chat

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clonemessandroid.R
import com.example.clonemessandroid.data.model.ChatDetailModel
import com.example.clonemessandroid.data.model.UserModel
import com.example.clonemessandroid.viewmodels.ViewModelProvidersFactory
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.google.gson.Gson
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.fragment_chated.*
import kotlinx.android.synthetic.main.fragment_message.*
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
    var listChatDetailModel: ArrayList<ChatDetailModel> = ArrayList()
    lateinit var imgFriend :String
    private var socket = IO.socket("https://clonemessage.herokuapp.com/")
    lateinit var adapter: DetailChatRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_chat_detail)
        idChat = intent.getStringExtra("idChat")
        from = intent.getStringExtra("from")
        to = intent.getStringExtra("to")
        imgFriend = intent.getStringExtra("imgFriend")
        viewModel = ViewModelProviders.of(this,providerFactory).get(DetailChatViewModel::class.java)
        initRecyclerView()
        if(idChat!=null){
            viewModel.getListDetailChat(idChat!!)
        }


        socket.connect()
        var userModel= UserModel()
        userModel.username=from
        val gson = Gson()
        val json = gson.toJson(userModel)
        socket.emit("username",json)
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
//            for (item in it){
//                viewModel.liveDataChat.value=item
//            }
            listChatDetailModel = it as ArrayList<ChatDetailModel>
            adapter.setArrayListDetail(it as ArrayList<ChatDetailModel>)

        })
        viewModel.liveDataChat.observe(this, Observer { it->

           adapter.addItemArralyListDetail(it)
            recyclerListChatDetail?.scrollToPosition(adapter.arrayList.size -1)
        })

    }
    private fun sendMessage(content:String,type :Int){
        var chatDetailModel = ChatDetailModel()
        if (idChat==null){
            idChat = ""
        }
        chatDetailModel.idChat=idChat
        chatDetailModel.content=content
        chatDetailModel.from=from
        chatDetailModel.to=to
        chatDetailModel.type=type
        val gson = Gson()
        val json = gson.toJson(chatDetailModel)

        socket.emit("message",json)
        edtContent?.setText("")
    }
    private fun initRecyclerView(){
        adapter = DetailChatRecyclerAdapter(from,imgFriend)
        adapter.setArrayListDetail(listChatDetailModel)
        val layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        layoutManager.stackFromEnd=true
        recyclerListChatDetail?.layoutManager = layoutManager
        recyclerListChatDetail?.adapter = adapter



    }
    override fun onStop() {
        super.onStop()
        socket.disconnect()
        Log.d("kiemtra","dus")
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
                chatDetailModel.type= data["type"] as Int

                viewModel.liveDataChat.value= chatDetailModel
            }
        }
}