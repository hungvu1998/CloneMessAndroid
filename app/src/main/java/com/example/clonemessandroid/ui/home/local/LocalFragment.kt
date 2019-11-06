package com.example.clonemessandroid.ui.home.local

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.clonemessandroid.R
import com.example.clonemessandroid.viewmodels.ViewModelProvidersFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class LocalFragment : DaggerFragment(){

    @Inject
    lateinit var providerFactory: ViewModelProvidersFactory

    lateinit var localViewModel: LocalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        localViewModel= ViewModelProviders.of(this,providerFactory).get(LocalViewModel::class.java)
        Log.d("kiemtra","oncreateLocal")
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_local, container, false)
        Log.d("kiemtra","oncreateViewLocal")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeObservers()
    }
    fun subscribeObservers(){

    }

}