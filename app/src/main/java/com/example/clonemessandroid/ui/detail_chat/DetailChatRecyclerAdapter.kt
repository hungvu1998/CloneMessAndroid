package com.example.clonemessandroid.ui.detail_chat

import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.clonemessandroid.R
import com.example.clonemessandroid.data.model.ChatDetailModel
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import org.w3c.dom.Text

class DetailChatRecyclerAdapter(var usernameCurrent:String,var imgFriend :String) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var arrayList: ArrayList<ChatDetailModel> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_detail, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PostViewHolder).bind(arrayList[position ],usernameCurrent,imgFriend)

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }


    fun setArrayListDetail(arrayList: ArrayList<ChatDetailModel>){
        this.arrayList = arrayList
        notifyDataSetChanged()
    }

    fun addItemArralyListDetail(chatDetailModel: ChatDetailModel){
        this.arrayList.add(chatDetailModel)
        notifyDataSetChanged()
    }

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var containerLayoutDetailChat:LinearLayout = itemView.findViewById(R.id.containerLayoutDetailChat)
        private var img_profile:CircleImageView = itemView.findViewById(R.id.img_profile)
        private var txtChat:TextView = itemView.findViewById(R.id.txtChat)


        fun bind(chatDetailModel: ChatDetailModel,usernameCurrent:String, imgFriend :String) {
            if(chatDetailModel.from == usernameCurrent){
                //
                containerLayoutDetailChat.gravity=Gravity.END
                img_profile.visibility=View.GONE
                txtChat.text = chatDetailModel.content
            }
            else{
                containerLayoutDetailChat.gravity=Gravity.START
                img_profile.visibility=View.VISIBLE
                Picasso.get().load(imgFriend).into(img_profile)
                txtChat.text = chatDetailModel.content
            }


        }



    }
}