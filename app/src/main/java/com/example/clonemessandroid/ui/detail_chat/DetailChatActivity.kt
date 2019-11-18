package com.example.clonemessandroid.ui.detail_chat

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.clonemessandroid.R
import com.example.clonemessandroid.data.model.ChatDetailModel
import com.example.clonemessandroid.data.model.UserModel
import com.example.clonemessandroid.viewmodels.ViewModelProvidersFactory
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.google.gson.Gson
import com.squareup.picasso.Picasso
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
    var active :Boolean =false
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
        active = intent.getBooleanExtra("active",false)
        viewModel = ViewModelProviders.of(this,providerFactory).get(DetailChatViewModel::class.java)
        initRecyclerView()
        if(idChat!=null){
            viewModel.getListDetailChat(idChat!!)
        }
        if(!active){
            txtActive?.setText("Offline")
        }
        else{
            txtActive?.setText("Đang hoạt động")
        }
        txtUserName?.setText(to)
        Picasso.get().load(imgFriend).into(story_user)
        loadGif()

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
            else
                sendMessage("like",2)
        }
        ic_sticker?.setOnClickListener {
            hideKeyboard(this)
            if(layout_sticker?.visibility==View.GONE){
                layout_sticker?.visibility=View.VISIBLE
            }
            else{
                layout_sticker?.visibility=View.GONE
            }

        }
        edtContent?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {



            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s?.trim().toString().isNotEmpty()){
                    Glide.with(this@DetailChatActivity).load(R.drawable.ic_send_blue_24dp).into(img_send)
                }
                else{
                    Glide.with(this@DetailChatActivity).load(R.drawable.like).into(img_send)
                }
            }

        }

        )
        subcribeObservers()
    }

    fun loadGif(){
        Glide.with(this).load(R.drawable.mimi1).into(mimi1)
        Glide.with(this).load(R.drawable.mimi2).into(mimi2)
        Glide.with(this).load(R.drawable.mimi3).into(mimi3)
        Glide.with(this).load(R.drawable.mimi4).into(mimi4)
        Glide.with(this).load(R.drawable.mimi5).into(mimi5)
        Glide.with(this).load(R.drawable.mimi6).into(mimi6)
        Glide.with(this).load(R.drawable.mimi7).into(mimi7)
        Glide.with(this).load(R.drawable.mimi8).into(mimi8)
        Glide.with(this).load(R.drawable.mimi9).into(mimi9)

        mimi1?.setOnClickListener {
            sendMessage("mimi1",2)
        }
        mimi2?.setOnClickListener {
            sendMessage("mimi2",2)
        }
        mimi3?.setOnClickListener {
            sendMessage("mimi3",2)
        }
        mimi4?.setOnClickListener {
            sendMessage("mimi4",2)
        }
        mimi5?.setOnClickListener {
            sendMessage("mimi5",2)
        }
        mimi6?.setOnClickListener {
            sendMessage("mimi6",2)
        }
        mimi7?.setOnClickListener {
            sendMessage("mimi7",2)
        }
        mimi8?.setOnClickListener {
            sendMessage("mimi8",2)
        }
        mimi9?.setOnClickListener {
            sendMessage("mimi9",2)
        }
    }

    override fun onBackPressed() {
        if(layout_sticker?.visibility==View.GONE)
        {
            super.onBackPressed()
        }

        else{
            layout_sticker?.visibility=View.GONE
        }
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
    fun hideKeyboard(activity: Activity) {
        val view = activity.findViewById<View>(android.R.id.content)
        if (view != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
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
        adapter = DetailChatRecyclerAdapter(this,from,imgFriend)
        adapter.setArrayListDetail(listChatDetailModel)
        val layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        layoutManager.stackFromEnd=true
        recyclerListChatDetail?.layoutManager = layoutManager
        recyclerListChatDetail?.adapter = adapter



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
                chatDetailModel.type= data["type"] as Int
                chatDetailModel.timestamp= data["timestamp"] as Long
                viewModel.liveDataChat.value= chatDetailModel
            }
        }
}