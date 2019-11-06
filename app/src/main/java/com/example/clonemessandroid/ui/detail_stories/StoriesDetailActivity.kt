package com.example.clonemessandroid.ui.detail_stories

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.clonemessandroid.R
import com.example.clonemessandroid.data.model.Stories

import com.example.clonemessandroid.viewmodels.ViewModelProvidersFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.detail_stories.*
import javax.inject.Inject

class StoriesDetailActivity : DaggerAppCompatActivity(){
    @Inject
    lateinit var providerFactory: ViewModelProvidersFactory
    lateinit var detailStoriesViewModel: DetailStoriesViewModel
    lateinit var adapter:ViewPageStoriesAdapter
    lateinit var arrayList: ArrayList<Stories>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_stories)
        arrayList=intent.getParcelableArrayListExtra("listStories")
        detailStoriesViewModel= ViewModelProviders.of(this,providerFactory).get(DetailStoriesViewModel::class.java)
        initViewPage()
        subscribeObervers()
    }
    private fun initViewPage() {
        adapter=ViewPageStoriesAdapter(supportFragmentManager)
        adapter.setListStories(arrayList)
        viewpage_story?.adapter=adapter
    }
    private fun subscribeObervers(){

    }
}