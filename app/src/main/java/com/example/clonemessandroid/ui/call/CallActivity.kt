package com.example.clonemessandroid.ui.call

import android.os.Bundle
import com.example.clonemessandroid.R
import com.example.clonemessandroid.viewmodels.ViewModelProvidersFactory
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.layout_call.*
import javax.inject.Inject
import android.util.Log
import com.opentok.android.Session
import com.opentok.android.Stream
import com.opentok.android.Publisher
import com.opentok.android.PublisherKit
import com.opentok.android.Subscriber
import com.opentok.android.OpentokError
import androidx.annotation.NonNull
import android.Manifest;
import com.opentok.android.Session.SessionListener
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import android.widget.FrameLayout
import android.opengl.GLSurfaceView;
import android.view.View
import com.example.clonemessandroid.data.model.ChatDetailModel
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.google.gson.Gson
import com.opentok.android.PublisherKit.PublisherListener
import org.json.JSONObject

class CallActivity : DaggerAppCompatActivity(), SessionListener, PublisherListener{

    @Inject
    lateinit var providerFactory: ViewModelProvidersFactory
    lateinit var viewModel: CallViewModel
    lateinit var to: String
    lateinit var from: String
    lateinit var imgFriend:String
    lateinit var idChat: String

    private val API_KEY = "46469292"
    private var SESSION_ID = ""
    private var TOKEN = ""
    private val LOG_TAG = CallActivity::class.java!!.getSimpleName()
    private val RC_SETTINGS_SCREEN_PERM = 123
    public final val RC_VIDEO_APP_PERM = 124
    private var mSession: Session? = null
    private var mPublisherViewContainer: FrameLayout? = null
    private var mSubscriberViewContainer: FrameLayout? = null
    private var mPublisher: Publisher? = null
    private var mSubscriber: Subscriber? = null
    private var socket = IO.socket("https://clonemessage.herokuapp.com/")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_call)
        socket.connect()
//        viewModel = ViewModelProviders.of(this,providerFactory).get(CallViewModel::class.java)
        to = intent.getStringExtra("to")
        from = intent.getStringExtra("from")
        idChat = intent.getStringExtra("idChat")
        imgFriend = intent.getStringExtra("imgFriend")

        Picasso.get().load(imgFriend).into(img_background)
        Picasso.get().load(imgFriend).into(imgAvatar)

//
//        Picasso.get().load(imgFriend).into(img_background)
//        Picasso.get().load(imgFriend).into(imgAvatar)
        username.text = to

        socket.emit("username","{\"username\": \"" + from + "\"}")

        var chatDetailModel = ChatDetailModel()

        if (idChat==null){
            idChat = ""
        }
        chatDetailModel.idChat = idChat
        chatDetailModel.content = ""
        chatDetailModel.from = from
        chatDetailModel.to = to
        chatDetailModel.type = 4
        val gson = Gson()
        val json = gson.toJson(chatDetailModel)

        socket.emit("message",json)

        socket.on("message", onNewMessage)
        Log.d("kiemtrahaha", "oncreate call activity")
    }

    private val onNewMessage =
        Emitter.Listener {
            runOnUiThread {
                val data= it[0] as JSONObject
                idChat= data["idChat"].toString()
                var chatDetailModel = ChatDetailModel()
                chatDetailModel.idChat=idChat
                chatDetailModel.content = data["content"].toString()
                chatDetailModel.from = data["from"].toString()
                chatDetailModel.to = data["to"].toString()
                chatDetailModel.type = data["type"] as Int
                chatDetailModel.timestamp = data["timestamp"] as Long
                Log.d("kiemtrahaha", "onNewMessage")
                Log.d("kiemtra",""+data["timestamp"])
                if(chatDetailModel.type == 4){
                    var content : List<String> = chatDetailModel!!.content!!.split(":")

                    SESSION_ID = content[0]
                    TOKEN = content[1]
                    Log.d("kiemtrahaha", "" + chatDetailModel!!.content!!)
                    requestPermissions()
                }
            }
        }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

//    @AfterPermissionGranted(124)
    fun requestPermissions() {
        val perms = arrayOf(
            Manifest.permission.INTERNET,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        )
        if (EasyPermissions.hasPermissions(this, *perms)) {
            // initialize view objects from your layout

            mPublisherViewContainer = findViewById(R.id.publisher_container) as FrameLayout
            mSubscriberViewContainer = findViewById(R.id.subscriber_container) as FrameLayout

            mSession = Session.Builder(this, API_KEY, SESSION_ID).build()
            mSession!!.setSessionListener(this)
            mSession!!.connect(TOKEN)
            // initialize and connect to the session
            Log.d("kiemtrahaha", "Session connecting...")

        } else {
            EasyPermissions.requestPermissions(
                this,
                "This app needs access to your camera and mic to make video calls",
                RC_VIDEO_APP_PERM,
                *perms
            )
        }
    }

    override fun onStreamDropped(p0: Session?, p1: Stream?) {
        Log.i(LOG_TAG, "Stream Dropped");
        Log.d("kiemtrahaha", "Stream Dropped")
        if (mSubscriber != null) {
            mSubscriber = null;
            mSubscriberViewContainer!!.removeAllViews();
        }
    }

    override fun onStreamReceived(session: Session?, stream: Stream?) {
        Log.i(LOG_TAG, "Stream Received");
        Log.d("kiemtrahaha", "Stream Received")
        if (mSubscriber == null) {
            mSubscriber = Subscriber.Builder(this, stream).build();
            mSession!!.subscribe(mSubscriber);
            mSubscriberViewContainer!!.addView(mSubscriber!!.getView());
        }


    }

    override fun onConnected(session: Session?) {
        Log.i(LOG_TAG, "Session Connected")
        Log.d("kiemtrahaha", "Session Connected")
        mPublisher = Publisher.Builder(this).build()
        mPublisher!!.setPublisherListener(this)

        mPublisherViewContainer!!.addView(mPublisher!!.view)

        if (mPublisher!!.getView() is GLSurfaceView) {
            (mPublisher!!.getView() as GLSurfaceView).setZOrderOnTop(true)
        }

        mSession!!.publish(mPublisher)
    }

    override fun onDisconnected(p0: Session?) {
        Log.d("kiemtrahaha", "Session onDisconnected")
    }

    override fun onError(p0: Session?, p1: OpentokError?) {
        Log.d("kiemtrahaha", "Session onError" + p1!!.getMessage())
    }

    override fun onStreamCreated(publisherKit: PublisherKit, stream: Stream) {
        Log.d("kiemtrahaha", "Publisher onStreamCreated")
        displayVideo()
    }

    override fun onStreamDestroyed(publisherKit: PublisherKit, stream: Stream) {
        Log.d("kiemtrahaha", "Publisher onStreamDestroyed")
    }

    override fun onError(publisherKit: PublisherKit, opentokError: OpentokError) {
        Log.d("kiemtrahaha", "Publisher onError")
    }

    fun displayVideo(){
        publisher_container.visibility = View.VISIBLE
        subscriber_container.visibility = View.VISIBLE
        img_background.visibility = View.GONE
        layout_pending.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        socket.disconnect()
    }
}