package com.example.clonemessandroid.ui.detail_stories.story_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.clonemessandroid.R
import com.example.clonemessandroid.data.model.Stories
import com.example.clonemessandroid.ui.home.story.StoryViewModel
import com.example.clonemessandroid.viewmodels.ViewModelProvidersFactory
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.item_story_pageview.*
import javax.inject.Inject

class StoryDetailFragment : DaggerFragment(){

    @Inject
    lateinit var providerFactory: ViewModelProvidersFactory

    lateinit var storyDetailFragmentViewModel: StoryDetailFragmentViewModel

    var stories : Stories?=null

    fun setModel(stories: Stories){
        this.stories=stories
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storyDetailFragmentViewModel= ViewModelProviders.of(this,providerFactory).get(StoryDetailFragmentViewModel::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.item_story_pageview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Picasso.get().load(stories?.imagePath).into(imgStory)
        subscribeObservers()
    }
    fun subscribeObservers(){

    }

}