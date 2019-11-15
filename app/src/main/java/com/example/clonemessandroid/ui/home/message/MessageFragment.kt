package com.example.clonemessandroid.ui.home.message

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clonemessandroid.R
import com.example.clonemessandroid.data.model.Stories
import com.example.clonemessandroid.data.model.UserModel
import com.example.clonemessandroid.ui.detail_stories.StoriesDetailActivity
import com.example.clonemessandroid.ui.home.HomeActivity
import com.example.clonemessandroid.ui.home.HomeViewModel
import com.example.clonemessandroid.ui.home.OnBack
import com.example.clonemessandroid.viewmodels.ViewModelProvidersFactory
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_message.*
import kotlinx.android.synthetic.main.progress_bar.*
import javax.inject.Inject
import androidx.recyclerview.widget.DividerItemDecoration
import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.example.clonemessandroid.data.model.ChatModel
import com.example.clonemessandroid.ui.detail_chat.DetailChatActivity
import com.example.clonemessandroid.ui.profile.ProfileActivity
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject


class MessageFragment : DaggerFragment(),RecyclerClickItem,OnBack{


    override fun doThis(userModelFriend: UserModel) {
        var idChat:String?=null
        for(item in userCurrent.chats!!){
            for(item2 in userModelFriend.chats!!){
                if(item2 == item){
                    idChat=item2
                    break
                }

            }
        }
        val intent= Intent(context, DetailChatActivity::class.java)
        intent.putExtra("idChat",idChat)
        intent.putExtra("to",userModelFriend.username)
        intent.putExtra("imgFriend",userModelFriend.avatar)
        intent.putExtra("from",userCurrent.username)
        startActivity(intent)

    }

    @Inject
    lateinit var providerFactory: ViewModelProvidersFactory

    lateinit var messageViewModel: MessageViewModel

    lateinit var homeViewModel: HomeViewModel

    var listFriend: ArrayList<UserModel> = ArrayList()

    var listFriend2: ArrayList<UserModel> = ArrayList()

    var listChatModel :ArrayList<ChatModel> = ArrayList()
    lateinit var userCurrent : UserModel
    lateinit var adapter: StoryRecyclerAdapter
    lateinit var adapterFriend: FriendRecyclerAdapter
    lateinit var adapterListChat : ListChatRecyclerAdapter

    private var socket = IO.socket("https://clonemessage.herokuapp.com/")
    val REQUEST_PERMISSION_CAMERA=5
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        messageViewModel= ViewModelProviders.of(this,providerFactory).get(MessageViewModel::class.java)
        homeViewModel=ViewModelProviders.of(activity!!,providerFactory).get(HomeViewModel::class.java)


    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_message, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as HomeActivity).setOnBackListener(this)

        profile_image?.setOnClickListener {
            val intent= Intent(context, ProfileActivity::class.java)
            intent.putExtra("userName",userCurrent.username)
            startActivity(intent)
        }
        homeViewModel.liveDataUserModel.observe(this, Observer {it->
            userCurrent=it
            Picasso.get().load(it.avatar).into(profile_image)

            it.friends?.let { it1 -> messageViewModel.getProfileFriend(it1) }

            var userModelNew= UserModel()
            userModelNew.username=userCurrent.username
            val gson = Gson()
            val json = gson.toJson(userModelNew)
            socket.emit("username",json)
        })
        messageViewModel.liveDataFriend.observe(this, Observer {it->
            listFriend.add(it)
            listFriend2.add(it)
            if(listFriend.size == userCurrent.friends!!.size +1){
                adapter.setFriendList(listFriend)
            }
            if(listFriend2.size == userCurrent.friends!!.size){
                adapterFriend.setFriendList(listFriend2)
                Observable.fromIterable(userCurrent.chats)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe{it->
                        messageViewModel.getListChat(it)

                    }

            }

        })

        messageViewModel.liveDataChatModel.observe(this, Observer { it->


            var chatModel = ChatModel()
            chatModel._id = it._id
            chatModel.messages= it.messages!!.sortedByDescending {it.timestamp }
            if (chatModel.messages!![0].from == userCurrent.username){
                for(item in listFriend2){
                    if(item.username == chatModel.messages!![0].to){
                       chatModel.userFriend=item

                        break
                    }
                }

            }
            else{
                for(item in listFriend2){
                    if(item.username == chatModel.messages!![0].from){
                        chatModel.userFriend=item

                        break
                    }
                }

            }

            adapterListChat.addItemArrayListChat(chatModel)

        })

        imgCamera?.setOnClickListener {
            permission()
        }



        socket.on("message", onNewMessage)

        edtSearchFriend?.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                txtHuySearchFriend?.visibility=View.VISIBLE
                layout_listFriend?.visibility=View.VISIBLE
                layout_chat?.visibility=View.GONE
            } else {
                txtHuySearchFriend?.visibility=View.GONE
                layout_listFriend?.visibility=View.GONE
                layout_chat?.visibility=View.VISIBLE

            }
        }
        txtHuySearchFriend?.setOnClickListener {
            edtSearchFriend?.clearFocus()
            edtSearchFriend?.text?.clear()
            hideKeyboard(activity as HomeActivity)

        }
        edtSearchFriend?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {



            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var list:ArrayList<UserModel> =ArrayList<UserModel>()
                for(item in listFriend2){
                    if (item.username.toString().toLowerCase().indexOf(s.toString())==-1){

                    }
                    else{
                        list.add(item)
                    }
                }

                adapterFriend.setFriendList(list)
            }

        }

        )

        initRecyclerView()
        initRecyclerViewListChat()
        initRecyclerViewFriend()
        subscribeObservers()
    }
    private val onNewMessage =
        Emitter.Listener {

            activity?.runOnUiThread {
                var _isExist = false
                val data= it[0] as JSONObject
                Log.d("kiemtra",""+data)
                if(userCurrent.chats!=null){
                    for (item in userCurrent.chats!!) {
                        if (item == data["idChat"].toString()){
                            _isExist=true
                            break
                        }
                        _isExist=false
                    }
                    if(_isExist){

                    }
                    else{
                        userCurrent.chats?.add(data["idChat"].toString())

                        if(data["from"].toString() == userCurrent.username){
                            for(i in 0 until listFriend2.size){
                                if(listFriend2[i].username==data["to"].toString()){
                                    listFriend2[i].chats?.add(data["idChat"].toString())
                                    break
                                }
                            }
                        }
                        else{
                            for(i in 0 until listFriend2.size){
                                if(listFriend2[i].username==data["from"].toString()){
                                    listFriend2[i].chats?.add(data["idChat"].toString())
                                    break
                                }
                            }
                        }

                        messageViewModel.getListChat(data["idChat"].toString())
                    }
                }

                //                idChat= data["idChat"].toString()
                //                var chatDetailModel = ChatDetailModel()
                //                chatDetailModel.idChat=idChat
                //                chatDetailModel.content=data["content"].toString()
                //                chatDetailModel.from=data["from"].toString()
                //                chatDetailModel.to=data["to"].toString()
                //                chatDetailModel.type= data["type"] as Int
                //
                //                viewModel.liveDataChat.value= chatDetailModel
            }

        }


    fun subscribeObservers(){

    }
    private fun initRecyclerView() {
        adapter= StoryRecyclerAdapter(this)
        listFriend.add(UserModel())
        adapter.setFriendList(listFriend)
        val layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL, false)
        recyclerListStory?.layoutManager = layoutManager
        recyclerListStory?.adapter = adapter
    }
    private fun initRecyclerViewListChat() {
        adapterListChat= ListChatRecyclerAdapter()
        val layoutManager=LinearLayoutManager(activity,LinearLayoutManager.VERTICAL, false)
        recyclerListMess?.layoutManager = layoutManager
        recyclerListMess?.adapter = adapterListChat
    }


    private fun initRecyclerViewFriend() {
        adapterFriend= FriendRecyclerAdapter()
        adapterFriend.setFriendList(listFriend2)
        val layoutManager=LinearLayoutManager(activity,LinearLayoutManager.VERTICAL, false)
        recyclerListFriend?.layoutManager = layoutManager
        recyclerListFriend?.adapter = adapterFriend
        val dividerItemDecoration = DividerItemDecoration(recyclerListFriend?.context, layoutManager.orientation)
        recyclerListFriend?.addItemDecoration(dividerItemDecoration)
    }
    fun permission(){
        val checkPermissionExternalStorage:Int= ContextCompat.checkSelfPermission(context!!,
            Manifest.permission.CAMERA)
        if(checkPermissionExternalStorage!= PackageManager.PERMISSION_GRANTED ){
            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.CAMERA), REQUEST_PERMISSION_CAMERA)
        }
        else{
            succesPermission()
        }
    }
    fun succesPermission(){
        val intent =  Intent("android.media.action.IMAGE_CAPTURE")
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("kiemtra","destroy")
        socket.disconnect()
    }
    override fun onBack(){
        if(!edtSearchFriend?.hasFocus()!!){
            (activity as HomeActivity).methodOnBack()
        }
        else{
            edtSearchFriend?.clearFocus()
            edtSearchFriend?.text?.clear()
            hideKeyboard(activity as HomeActivity)
        }
    }
    fun hideKeyboard(activity: Activity) {
        val view = activity.findViewById<View>(android.R.id.content)
        if (view != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}