package com.example.clonemessandroid.ui.home

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.RequestManager
import com.example.clonemessandroid.R
import com.example.clonemessandroid.viewmodels.ViewModelProvidersFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.layout_home.*
import javax.inject.Inject

class HomeActivity : DaggerAppCompatActivity(){
    @Inject
    lateinit var providerFactory: ViewModelProvidersFactory



    lateinit var homeViewModel: HomeViewModel

    lateinit var adapter: ViewPageHomeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_home)
        homeViewModel= ViewModelProviders.of(this,providerFactory).get(HomeViewModel::class.java)

        initViewPage()
        subscribeObervers()
    }

    private fun initViewPage() {
        adapter=ViewPageHomeAdapter(supportFragmentManager)
        viewpager?.adapter=adapter
    }
    private fun subscribeObervers(){

    }

}