package com.example.clonemessandroid.ui.detail_chat.full_screen_img

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.blue
import androidx.fragment.app.DialogFragment
import com.example.clonemessandroid.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_dialog_img_fullscreen.*

class FullScreenImgDialog(val img:String) : DialogFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.layout_dialog_img_fullscreen,container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Picasso.get().load(img).into(img_full)
        ic_cancel?.setOnClickListener {
            dismiss()
        }

    }
}