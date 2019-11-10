package com.example.clonemessandroid.ui.home

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.RequestManager
import com.example.clonemessandroid.R
import com.example.clonemessandroid.data.model.UserModel
import com.example.clonemessandroid.viewmodels.ViewModelProvidersFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.layout_home.*
import javax.inject.Inject

class HomeActivity : DaggerAppCompatActivity(),BottomNavigationView.OnNavigationItemSelectedListener{


    @Inject
    lateinit var providerFactory: ViewModelProvidersFactory



    lateinit var homeViewModel: HomeViewModel

    lateinit var adapter: ViewPageHomeAdapter
    lateinit var userModel:UserModel
    var listUser:ArrayList<UserModel> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_home)
        userModel= intent.getParcelableExtra("userModel")

        homeViewModel= ViewModelProviders.of(this,providerFactory).get(HomeViewModel::class.java)

        initViewPage()
        subscribeObervers()
        bottom_navigation.setOnNavigationItemSelectedListener (this)
    }



    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
      when(p0.itemId){
          R.id.navigation_comment ->{
              viewpager.currentItem=0
          }
          R.id.navigation_story ->{
              viewpager.currentItem=1
          }
          R.id.navigation_local -> {
              viewpager.currentItem=2
          }
      }
        return false
    }
    private fun initViewPage() {
        adapter=ViewPageHomeAdapter(supportFragmentManager)
        viewpager?.adapter=adapter
        viewpager?.offscreenPageLimit=3
    }
    private fun subscribeObervers(){
       // homeViewModel.getUserInfo(uid)
    }

}