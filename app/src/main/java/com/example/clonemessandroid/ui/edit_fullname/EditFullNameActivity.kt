package com.example.clonemessandroid.ui.edit_fullname

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.clonemessandroid.R
import com.example.clonemessandroid.data.model.UserModel
import com.example.clonemessandroid.viewmodels.ViewModelProvidersFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.layout_edit_fullname.*
import kotlinx.android.synthetic.main.layout_edit_fullname.progress_bar
import kotlinx.android.synthetic.main.layout_login.*
import javax.inject.Inject

class EditFullNameActivity : DaggerAppCompatActivity(){

    @Inject
    lateinit var providerFactory: ViewModelProvidersFactory

    lateinit var viewModel: EditFullNameViewModel

    var typeEdit:Int = -1
    lateinit var userModel:UserModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_edit_fullname)
        viewModel= ViewModelProviders.of(this,providerFactory).get(EditFullNameViewModel::class.java)
        userModel=intent.getParcelableExtra("userModel")
        typeEdit=intent.getIntExtra("typeEdit",-1)
        if (typeEdit==1){
            titleEdit?.text = "Full Name"
            edtEdit?.setText(userModel.fullname)

            edtEdit?.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {

                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {



                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if(s.toString() == userModel.fullname){
                        txtLuu?.isEnabled=false
                        txtLuu?.setTextColor(Color.parseColor("#A39696"))
                    }
                    else{
                        txtLuu?.isEnabled=true
                        txtLuu?.setTextColor(Color.parseColor("#000000"))
                        txtLuu?.setOnClickListener {
                            viewModel.editProfileUser(userModel.username!!,edtEdit?.text.toString(),null,null)
                        }
                    }
                }

            }
            )
        }else if (typeEdit==2){
            titleEdit?.text = "Change Email"
            edtEdit?.setText(userModel.email)

            edtEdit?.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {

                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if(s.toString() == userModel.email ){
                        txtLuu?.isEnabled=false
                        txtLuu?.setTextColor(Color.parseColor("#A39696"))
                    }
                    else{
                        txtLuu?.isEnabled=true
                        txtLuu?.setTextColor(Color.parseColor("#000000"))
                        txtLuu?.setOnClickListener {
                            if(TextUtils.isEmpty(s.toString().trim()) || !Patterns.EMAIL_ADDRESS.matcher(s.toString().trim()).matches()){
                                edtEdit?.error = "invalid email"
                            }else{
                                viewModel.editProfileUser(userModel.username!!,null,null,edtEdit?.text.toString())
                            }

                        }
                    }
                }
            }
            )
        }




        img_back?.setOnClickListener {
            val returnIntent  = Intent()
            setResult(Activity.RESULT_CANCELED,returnIntent)
            finish()
        }

        viewModel.liveDataResult.observe(this, Observer {it->
            if(it==0){
                showProgressBar(true)
            }
            else if(it==1){
                showProgressBar(false)
                val returnIntent  = Intent()
                setResult(Activity.RESULT_OK,returnIntent)
                finish()
            }
            else if(it==2){
                showProgressBar(false)
               Toast.makeText(this,"Edit Profile User fail",Toast.LENGTH_SHORT).show()

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