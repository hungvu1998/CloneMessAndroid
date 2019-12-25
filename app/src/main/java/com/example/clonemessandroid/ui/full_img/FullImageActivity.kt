package com.example.clonemessandroid.ui.full_img

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.clonemessandroid.R
import com.example.clonemessandroid.ui.edit_fullname.EditFullNameViewModel
import com.example.clonemessandroid.viewmodels.ViewModelProvidersFactory
import com.r0adkll.slidr.Slidr
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.layout_dialog_img_fullscreen.*
import javax.inject.Inject

class FullImageActivity :DaggerAppCompatActivity(){
    @Inject
    lateinit var providerFactory: ViewModelProvidersFactory
    lateinit var viewModel: FullImageViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_dialog_img_fullscreen)
        val img=intent.getStringExtra("url")
        viewModel= ViewModelProviders.of(this,providerFactory).get(FullImageViewModel::class.java)
        Picasso.get().load(img).into(img_full)
        ic_cancel?.setOnClickListener {
            finish()
        }
        Slidr.attach(this)
    }
}