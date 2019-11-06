package com.example.clonemessandroid.ui.home.message

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clonemessandroid.R
import com.example.clonemessandroid.data.model.Stories
import com.example.clonemessandroid.data.model.UserModel
import com.example.clonemessandroid.ui.home.HomeViewModel
import com.example.clonemessandroid.ui.home.local.LocalViewModel
import com.example.clonemessandroid.viewmodels.ViewModelProvidersFactory
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_message.*
import kotlinx.android.synthetic.main.progress_bar.*

import javax.inject.Inject

class MessageFragment : DaggerFragment(){

    @Inject
    lateinit var providerFactory: ViewModelProvidersFactory

    lateinit var messageViewModel: MessageViewModel

    lateinit var homeViewModel: HomeViewModel

    var listFriend: ArrayList<UserModel> = ArrayList()
    lateinit var userCurrent : UserModel
    lateinit var adapter: StoryRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        messageViewModel= ViewModelProviders.of(this,providerFactory).get(MessageViewModel::class.java)
        homeViewModel=ViewModelProviders.of(activity!!,providerFactory).get(HomeViewModel::class.java)

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_message, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        homeViewModel.liveDataUserModel.observe(this, Observer {it->
            Picasso.get().load(it.img).into(profile_image)
            userCurrent=it

            it.listFriend?.let { it1 -> messageViewModel.getProfileFriend(it1) }

        })
        messageViewModel.liveDataFriend.observe(this, Observer {it->
            listFriend.add(it)
            if(listFriend.size == userCurrent.listFriend!!.size){
                adapter.setFriendList(listFriend)
            }

        })

        initRecyclerView()
        subscribeObservers()
    }
    fun subscribeObservers(){

    }
    private fun initRecyclerView() {
        adapter= StoryRecyclerAdapter()
        listFriend.add(UserModel())
        adapter.setFriendList(listFriend)
        val layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL, false)
        recyclerListStory?.layoutManager = layoutManager
        recyclerListStory?.adapter = adapter
    }

}