package com.example.clonemessandroid.ui.home.message

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.clonemessandroid.R
import com.example.clonemessandroid.data.model.ChatModel
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ListChatRecyclerAdapter(var recyclerClickItem: RecyclerClickItem) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var arrayList: ArrayList<ChatModel> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PostViewHolder).bind(arrayList[position],recyclerClickItem)

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

        private var profile_image_story: CircleImageView = itemView.findViewById(R.id.profile_image_story)
        private var txtNameFriend:TextView = itemView.findViewById(R.id.txtNameFriend)
        private var txtChatLast:TextView=itemView.findViewById(R.id.txtChatLast)
        private var txtTimeLast:TextView = itemView.findViewById(R.id.txtTimeLast)
        private var layoutChat:LinearLayout = itemView.findViewById(R.id.layout_chat)
        private var user_active:FrameLayout =itemView.findViewById(R.id.user_active)
        private var img_user_active:CircleImageView = itemView.findViewById(R.id.img_user_active)
        private var layout_active:LinearLayout = itemView.findViewById(R.id.layout_active)
        fun bind(chatModel: ChatModel,recyclerClickItem: RecyclerClickItem) {
            if (chatModel.userFriend!=null){
                txtNameFriend.text=chatModel.userFriend!!.username
                if(chatModel.userFriend!!.stories!!.size !=0){
                    profile_image_story.visibility=View.VISIBLE
                    user_active.visibility=View.GONE
                    Picasso.get().load(chatModel.userFriend!!.avatar).into(profile_image_story)
                }
                else {
                    profile_image_story.visibility=View.GONE
                    user_active.visibility=View.VISIBLE
                    Picasso.get().load(chatModel.userFriend!!.avatar).into(img_user_active)
                    if (chatModel.userFriend!!.active!!){
                        layout_active.visibility=View.VISIBLE
                    }
                    else{
                        layout_active.visibility=View.GONE
                    }
                }


                layoutChat.setOnClickListener {
                    recyclerClickItem.doThis(chatModel.userFriend!!)
                }
                txtTimeLast.setText(getDateTime(chatModel.messages!![0].timestamp!!))
            }
            if(chatModel.messages!![0].type==1)
            txtChatLast.setText(""+ chatModel.messages!![0].content)
            else if (chatModel.messages!![0].type==2){
                txtChatLast.setText("[Sticker]")
            }
            else if (chatModel.messages!![0].type==3){
                txtChatLast.setText("[Image]")
            }
            else{
                txtChatLast.text="video"
            }
        }

        private fun getDateTime(s: Long): String? {
            try {
                val sdf = SimpleDateFormat("HH:mm a")
                val netDate = Date(s)
                return sdf.format(netDate)
            } catch (e: Exception) {
                return e.toString()
            }
        }

    }
}