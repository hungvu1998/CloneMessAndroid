package com.example.clonemessandroid.ui.home.message

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.clonemessandroid.R
import com.example.clonemessandroid.data.model.ChatModel
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class ListChatRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var arrayList: ArrayList<ChatModel> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PostViewHolder).bind(arrayList[position])

    }

    override fun getItemCount(): Int {

        return arrayList.size
    }


    fun setArrayListChat(arrayList: ArrayList<ChatModel>){
        this.arrayList = arrayList
        notifyDataSetChanged()
    }


    fun addItemArrayListChat(chatModel: ChatModel){
        this.arrayList.add(chatModel)
        this.arrayList.sortByDescending {
            it.messages!![0].timestamp
        }
        notifyDataSetChanged()
    }

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var profile_image: CircleImageView = itemView.findViewById(R.id.profile_image)
        private var txtNameFriend:TextView = itemView.findViewById(R.id.txtNameFriend)
        private var txtChatLast:TextView=itemView.findViewById(R.id.txtChatLast)
        private var txtTimeLast:TextView = itemView.findViewById(R.id.txtTimeLast)

        fun bind(chatModel: ChatModel) {
            if (chatModel.userFriend!=null){
                txtNameFriend.text=chatModel.userFriend!!.username
                Picasso.get().load(chatModel.userFriend!!.avatar).into(profile_image)
            }
            txtChatLast.setText(""+ chatModel.messages!![0].content)


        }



    }
}