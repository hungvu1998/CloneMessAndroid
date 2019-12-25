package com.example.clonemessandroid.ui.detail_chat

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.clonemessandroid.R
import com.example.clonemessandroid.data.model.ChatDetailModel
import com.example.clonemessandroid.data.model.UserModel
import com.example.clonemessandroid.ui.call.CallActivity
import com.example.clonemessandroid.ui.detail_chat.full_screen_img.FullScreenImgDialog
import com.example.clonemessandroid.ui.full_img.FullImageActivity
import com.example.clonemessandroid.util.ImageFilePath
import com.example.clonemessandroid.viewmodels.ViewModelProvidersFactory
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_chated.*
import kotlinx.android.synthetic.main.fragment_message.*
import kotlinx.android.synthetic.main.layout_chat_detail.*
import kotlinx.android.synthetic.main.progessbar_chat_detail.*
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class DetailChatActivity : DaggerAppCompatActivity (),RecyclerImgFullScreen{
    override fun loadImg(img: String) {
        val intent = Intent(this,FullImageActivity::class.java)
        intent.putExtra("url",img)
        startActivity(intent)
//        val dialog: DialogFragment= FullScreenImgDialog(img)
//        dialog.show(supportFragmentManager,"tag")
//        val inflater = baseContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        val customView = inflater.inflate(R.layout.layout_dialog_img_fullscreen,null)
//        val myPopUp = PopupWindow(customView,RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT)
//        customView.setOnTouchListener { v, event ->  return@setOnTouchListener false}
    }

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
    val REQUEST_PERMISSION_CAMERA=5
    val REQUEST_PERMISSION_GALLERY=6
    val PICK_IMAGE = 1
    val PICK_CAMERA = 2
    var json:String?=null
    var layoutManager:LinearLayoutManager?=null
    var totalPage = 0
    override fun onStart() {
        super.onStart()
        if(socket.connected()){
            Log.d("kiemtraData","da connect")
        }
        else{
            Log.d("kiemtraData","chua connect")
        }
//
//        if(json!=null)
//        socket.emit("username",json)
    }
    var page_count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_chat_detail)
        socket.connect()
        idChat = intent.getStringExtra("idChat")
        from = intent.getStringExtra("from")
        to = intent.getStringExtra("to")
        imgFriend = intent.getStringExtra("imgFriend")
        active = intent.getBooleanExtra("active",false)
        viewModel = ViewModelProviders.of(this,providerFactory).get(DetailChatViewModel::class.java)
        initRecyclerView()
        if(idChat!=null){

            viewModel.getListDetailChat(idChat!!,page_count)
        }
        if(!active){
            txtActive?.text = "Offline"
        }
        else{
            txtActive?.text = "Đang hoạt động"
        }
        txtUserName?.text = to
        Picasso.get().load(imgFriend).into(story_user)
        loadGif()


        var userModel= UserModel()
        userModel.username=from
        val gson = Gson()
        json= gson.toJson(userModel)
        socket.emit("username",json!!)
        socket.on("message", onNewMessage)



        img_back?.setOnClickListener {
            finish()
        }
        imgCamera?.setOnClickListener{
            permissionCamera()
        }
        imgPicture?.setOnClickListener {
            permissionPicture()
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

        imgPhone?.setOnClickListener {
            startVideoCall()
        }

        imgVideoCam?.setOnClickListener{
            startVideoCall()
        }
        val  handler =  Handler()

        recyclerListChatDetail?.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx,dy)

            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        if(layoutManager?.findFirstVisibleItemPosition() == 0 && page_count<totalPage-1){
                            if(!adapter.getStatus()){
                                page_count++
                                adapter.setStatus(true)
                                viewModel.getListDetailChat(idChat!!, page_count)
                            }

                        }



            }
        })


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

            var newList = it.messages as ArrayList<ChatDetailModel>
            listChatDetailModel.addAll(newList)
            var listSort=listChatDetailModel.sortedWith(compareBy { it.timestamp })
            listChatDetailModel.clear()
            listChatDetailModel.addAll(listSort)
            adapter.setArrayListDetail(listChatDetailModel ,false)
            totalPage=it.pageCount!!
            if(page_count == 0){
                recyclerListChatDetail?.scrollToPosition(adapter.arrayList.size)
            }else{
                val position = 10 * page_count
                recyclerListChatDetail?.scrollToPosition(position)
            }

          //  findViewById<ProgressBar>(R.id.progress_bar).visibility=View.GONE

        })
        viewModel.liveDataChat.observe(this, Observer { it->
            if(it.timestamp==null){
                it.timestamp= System.currentTimeMillis()
            }
           adapter.addItemArralyListDetail(it)
            recyclerListChatDetail?.scrollToPosition(adapter.arrayList.size )
        })
        viewModel.livedataImgUpdaload.observe(this, Observer {it->
            var chatDetailModel = ChatDetailModel()
            if (idChat==null){
                idChat = ""
            }
            chatDetailModel.idChat=idChat
            chatDetailModel.content=it.string()
            chatDetailModel.from=from
            chatDetailModel.to=to
            chatDetailModel.type=3
            val gson = Gson()
            val json = gson.toJson(chatDetailModel)
            Log.d("kiemtraLive2",""+chatDetailModel.content)
            socket.emit("message",json)
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

        viewModel.liveDataChat.value= chatDetailModel
        edtContent?.setText("")
    }
    private fun initRecyclerView(){
        adapter = DetailChatRecyclerAdapter(this,from,imgFriend,this)
        adapter.setArrayListDetail(listChatDetailModel,true)
        layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        layoutManager!!.stackFromEnd=true
        //recyclerListChatDetail.isNestedScrollingEnabled = false
        recyclerListChatDetail?.layoutManager = layoutManager
        recyclerListChatDetail?.adapter = adapter
    }

    fun permissionCamera(){
        val checkPermissionExternalStorage:Int= ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        if(checkPermissionExternalStorage!= PackageManager.PERMISSION_GRANTED ){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_PERMISSION_CAMERA)
        }
        else{
            succesPermissionCamera()
        }
    }
    fun succesPermissionpicture(){
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        //intent.action = Intent.ACTION_PICK
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
        //startActivityForResult(intent,PICK_IMAGE)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && null != data) {
            if (data.clipData != null) {
                val count = data.clipData!!.itemCount
                for (i in 0 until count) {
                    val imageUri = data.clipData!!.getItemAt(i).uri
                    val path = ImageFilePath.getPathFromUri(this,imageUri)
                    val file = File(path)
                    viewModel.upLoadImage(file)

                    var chatDetailModel = ChatDetailModel()
                    if (idChat==null){
                        idChat = ""
                    }
                    chatDetailModel.idChat=idChat
                    chatDetailModel.content=data.data.toString()
                    chatDetailModel.from=from
                    chatDetailModel.to=to
                    chatDetailModel.type=3

                    viewModel.liveDataChat.value= chatDetailModel

                }
            }
            else if (data.data != null) {

                val path = ImageFilePath.getPathFromUri(this,data.data!!)
                val file = File(path)


                viewModel.upLoadImage(file)

                var chatDetailModel = ChatDetailModel()
                if (idChat==null){
                    idChat = ""
                }
                chatDetailModel.idChat=idChat
                chatDetailModel.content=data.data.toString()
                chatDetailModel.from=from
                chatDetailModel.to=to
                chatDetailModel.type=3

                viewModel.liveDataChat.value= chatDetailModel

            }
        }
        else if(requestCode == PICK_CAMERA && resultCode == Activity.RESULT_OK && data != null ){
            var photo : Bitmap = data.extras!!.get("data") as Bitmap
            var file : File = createFile(photo)

            viewModel.upLoadImage(file)

            var chatDetailModel = ChatDetailModel()
            if (idChat==null){
                idChat = ""
            }
            chatDetailModel.idChat=idChat
            chatDetailModel.content= file.path
            chatDetailModel.from=from
            chatDetailModel.to=to
            chatDetailModel.type=3

            viewModel.liveDataChat.value= chatDetailModel
        }
    }

    fun createFile(bitmap : Bitmap) : File{

        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        var imageFile: File =  File(storageDir, timeStamp + ".png")
        var os : OutputStream
        try {
          os = FileOutputStream(imageFile)
          bitmap.compress(Bitmap.CompressFormat.PNG, 100, os)
          os.flush()
          os.close()
        } catch (e : Exception) {
          Log.e("kiemtra", "Error writing bitmap", e)
        }

        return imageFile
    }


    fun permissionPicture(){
        val checkPermissionExternalStorage:Int= ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        if(checkPermissionExternalStorage!= PackageManager.PERMISSION_GRANTED ){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_PERMISSION_GALLERY)
        }
        else{
            succesPermissionpicture()
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode){
            REQUEST_PERMISSION_CAMERA ->{
                if((grantResults.isNotEmpty() &&  grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                    succesPermissionCamera()
                }
                else{

                }
            }


            REQUEST_PERMISSION_GALLERY->{
                if((grantResults.isNotEmpty() &&  grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                    succesPermissionpicture()
                }
                else{

                }
            }
        }
    }
    fun succesPermissionCamera(){
        val intent =  Intent("android.media.action.IMAGE_CAPTURE")
        startActivityForResult(intent,PICK_CAMERA)
    }

    override fun onDestroy() {
        super.onDestroy()
        socket.disconnect()
    }

    private val onNewMessage =
        Emitter.Listener {
            runOnUiThread {
                val data= it[0] as JSONObject
                idChat= data["idChat"].toString()
                if(data["from"].toString() != from){

                    Log.d("kiemtraData",""+data)
                    var chatDetailModel = ChatDetailModel()
                    chatDetailModel.idChat = idChat
                    chatDetailModel.content = data["content"].toString()
                    chatDetailModel.from = data["from"].toString()
                    chatDetailModel.to = data["to"].toString()
                    chatDetailModel.type = data["type"] as Int
                    chatDetailModel.timestamp = data["timestamp"] as Long
                    viewModel.liveDataChat.value = chatDetailModel

                    if(chatDetailModel.type == 4){
                        startVideoCall()
                    }
                }
            }
        }

    fun startVideoCall(){
        val intent = Intent(this,CallActivity::class.java)
        intent.putExtra("to",to)
        intent.putExtra("from",from)
        intent.putExtra("idChat",idChat)
        intent.putExtra("imgFriend",imgFriend)
        Log.d("kiemtrahaha", "start video call")
        startActivity(intent)
    }
}