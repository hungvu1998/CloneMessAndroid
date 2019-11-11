package com.example.clonemessandroid.ui.home.message

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.clonemessandroid.R
import com.example.clonemessandroid.data.model.UserModel
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class FriendRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var arrayList: ArrayList<UserModel> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_friend, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PostViewHolder).bind(arrayList[position ])

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }


    fun setFriendList(arrayList: ArrayList<UserModel>){
        this.arrayList = arrayList
        notifyDataSetChanged()
    }

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var txtFriendName:TextView = itemView.findViewById(R.id.txtFriendName)
        private var profile_image:CircleImageView = itemView.findViewById(R.id.profile_image)

        fun bind(userModel: UserModel) {
            Picasso.get().load(userModel.avatar).into(profile_image)
            txtFriendName.text=userModel.username
        }



    }
}