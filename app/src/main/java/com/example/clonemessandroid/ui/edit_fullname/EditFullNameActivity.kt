package com.example.clonemessandroid.ui.edit_fullname

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.clonemessandroid.R
import com.example.clonemessandroid.viewmodels.ViewModelProvidersFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.layout_edit_fullname.*
import javax.inject.Inject

class EditFullNameActivity : DaggerAppCompatActivity(){

    @Inject
    lateinit var providerFactory: ViewModelProvidersFactory

    lateinit var viewModel: EditFullNameViewModel

    lateinit var fullname:String
    lateinit var userName:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_edit_fullname)
        viewModel= ViewModelProviders.of(this,providerFactory).get(EditFullNameViewModel::class.java)
        userName=intent.getStringExtra("userName")
        fullname=intent.getStringExtra("fullname")
        edtFullName?.setText(fullname)

        edtFullName?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {



            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString() == fullname){
                    txtLuu?.isEnabled=false
                    txtLuu?.setTextColor(Color.parseColor("#A39696"))
                }
                else{
                    txtLuu?.isEnabled=true
                    txtLuu?.setTextColor(Color.parseColor("#000000"))
                    txtLuu?.setOnClickListener {
                        viewModel.editFullName(userName,edtFullName?.text.toString())
                    }
                }
            }

        }

        )


        img_back?.setOnClickListener {
            val returnIntent  = Intent()
            setResult(Activity.RESULT_CANCELED,returnIntent)
            finish()
        }

        viewModel.liveDataResult.observe(this, Observer {it->
            if(!it){
                showProgressBar(true)
            }
            else{
                showProgressBar(false)
                val returnIntent  = Intent()
                setResult(Activity.RESULT_OK,returnIntent)
                finish()
            }

        })

        subcribeObservers()
    }

    fun showProgressBar(isShowing:Boolean){
        if(isShowing){
            progress_bar?.visibility= View.VISIBLE
        }
        else{
            progress_bar?.visibility= View.GONE
        }
    }

    private fun subcribeObservers(){

    }


}