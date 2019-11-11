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


class MessageFragment : DaggerFragment(),RecyclerClickItem,OnBack{


    override fun doThis(array: ArrayList<Stories>) {
        val intent = Intent(activity,StoriesDetailActivity::class.java)
        intent.putParcelableArrayListExtra("listStories",array)
        startActivity(intent)
    }

    @Inject
    lateinit var providerFactory: ViewModelProvidersFactory

    lateinit var messageViewModel: MessageViewModel

    lateinit var homeViewModel: HomeViewModel

    var listFriend: ArrayList<UserModel> = ArrayList()

    var listFriend2: ArrayList<UserModel> = ArrayList()
    lateinit var userCurrent : UserModel
    lateinit var adapter: StoryRecyclerAdapter
    lateinit var adapterFriend: FriendRecyclerAdapter
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
        homeViewModel.liveDataUserModel.observe(this, Observer {it->
            userCurrent=it
            Picasso.get().load(it.avatar).into(profile_image)
            it.friends?.let { it1 -> messageViewModel.getProfileFriend(it1) }

        })
        messageViewModel.liveDataFriend.observe(this, Observer {it->
            listFriend.add(it)
            listFriend2.add(it)
            if(listFriend.size == userCurrent.friends!!.size +1){
                adapter.setFriendList(listFriend)
            }
            if(listFriend2.size == userCurrent.friends!!.size){
                adapterFriend.setFriendList(listFriend2)
            }

        })

        imgCamera?.setOnClickListener {
            permission()
        }


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
        initRecyclerViewFriend()
        subscribeObservers()
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
    override fun onBack() {
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