package com.example.clonemessandroid.ui.home.message

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.clonemessandroid.R
import com.example.clonemessandroid.data.model.Stories
import com.example.clonemessandroid.data.model.UserModel
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.layout_login.*
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.nio.ByteBuffer

class StoryRecyclerAdapter(var recyclerClickItem: RecyclerClickItem) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var arrayList: ArrayList<UserModel> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_story_circle_add, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PostViewHolder).bind(arrayList[position ],position,recyclerClickItem)

    }

    override fun getItemCount(): Int {

        return arrayList.size
    }


    fun setFriendList(arrayList: ArrayList<UserModel>){
        this.arrayList = arrayList
        notifyDataSetChanged()
    }

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var layout_add:LinearLayout = itemView.findViewById(R.id.layout_add)
        private var story_user:CircleImageView = itemView.findViewById(R.id.story_user)
        private var user_active:FrameLayout = itemView.findViewById(R.id.user_active)
        private var img_user_active:CircleImageView=itemView.findViewById(R.id.img_user_active)
        private var layout_all:FrameLayout = itemView.findViewById(R.id.layout_all)

        fun bind(userModel: UserModel,position: Int,recyclerClickItem : RecyclerClickItem) {
            if(position!=0){
                layout_add.visibility=View.GONE
                if(userModel.active!=null){
                    if ( !userModel.active!!){
                        story_user.visibility=View.GONE
                        user_active.visibility=View.GONE
                        layout_all.visibility=View.GONE
                        layout_all.layoutParams = FrameLayout.LayoutParams(0, 0)
                    }
                    if (userModel.active!!){
                        story_user.visibility=View.VISIBLE
                        user_active.visibility=View.GONE
                        Picasso.get().load(userModel.avatar!!).into(story_user)
                    }
                }



//                if (userModel.stories!=null){
//                    story_user.visibility=View.VISIBLE
//                    user_active.visibility=View.GONE
//                    Picasso.get().load(userModel.listStories[userModel.listStories.size-1].img).into(story_user)
//
//                    story_user.setOnClickListener {
//                        recyclerClickItem.doThis(userModel.listStories)
//                    }
//                }



            }
            else{
                layout_add.visibility=View.VISIBLE
                story_user.visibility=View.GONE
                user_active.visibility=View.GONE
            }


        }



    }
}