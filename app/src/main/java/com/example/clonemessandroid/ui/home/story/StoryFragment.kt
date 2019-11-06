package com.example.clonemessandroid.ui.home.story

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.clonemessandroid.R
import com.example.clonemessandroid.ui.home.message.MessageViewModel
import com.example.clonemessandroid.viewmodels.ViewModelProvidersFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class StoryFragment : DaggerFragment(){

    @Inject
    lateinit var providerFactory: ViewModelProvidersFactory

    lateinit var storyViewModel: StoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storyViewModel= ViewModelProviders.of(this,providerFactory).get(StoryViewModel::class.java)
        Log.d("kiemtra","oncreateStory")
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_story, container, false)
        Log.d("kiemtra","oncreateViewStory")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeObservers()
    }
    fun subscribeObservers(){

    }

}