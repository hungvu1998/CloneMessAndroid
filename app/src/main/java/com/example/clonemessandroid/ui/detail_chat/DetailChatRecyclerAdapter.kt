package com.example.clonemessandroid.ui.detail_chat

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.clonemessandroid.R
import com.example.clonemessandroid.data.model.ChatDetailModel
import com.example.clonemessandroid.ui.detail_chat.full_screen_img.FullScreenImgDialog
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.w3c.dom.Text
import java.net.HttpURLConnection
import java.net.URL
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DetailChatRecyclerAdapter(var context: Context, var usernameCurrent:String, var imgFriend :String,val recyclerImgFullScreen: RecyclerImgFullScreen) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var arrayList: ArrayList<ChatDetailModel> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_detail, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PostViewHolder).bind(context,arrayList[position],position,usernameCurrent,imgFriend,recyclerImgFullScreen)

    }

    override fun getItemCount(): Int {
        return  arrayList.size
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
        private var txtTime:TextView = itemView.findViewById(R.id.txtTime)
        private var img_story:ImageView =  itemView.findViewById(R.id.img_story)
        private var layout_chat:LinearLayout = itemView.findViewById(R.id.layout_chat)
        private var layout_img:FrameLayout = itemView.findViewById(R.id.layout_img)
        private var preview_image:ImageView =  itemView.findViewById(R.id.preview_image)


        @SuppressLint("CheckResult")
        fun bind(context: Context, chatDetailModel: ChatDetailModel, position:Int, usernameCurrent:String, imgFriend :String, recyclerImgFullScreen: RecyclerImgFullScreen) {

            txtTime.visibility=View.GONE
            if(chatDetailModel.from == usernameCurrent){
                containerLayoutDetailChat.gravity=Gravity.END
                img_profile.visibility=View.GONE
            }
            else{
                containerLayoutDetailChat.gravity=Gravity.START
                img_profile.visibility=View.VISIBLE
                Picasso.get().load(imgFriend).into(img_profile)
            }

            if(chatDetailModel.type == 1){
                //text
                txtChat.visibility=View.VISIBLE
                layout_chat.visibility=View.VISIBLE
                img_story.visibility=View.GONE
                layout_img.visibility=View.GONE
                txtChat.text = chatDetailModel.content

            }
            else if (chatDetailModel.type == 2){
                //Stiker
                txtChat.visibility=View.GONE
                layout_chat.visibility=View.GONE
                img_story.visibility=View.VISIBLE
                layout_img.visibility=View.GONE
                //Picasso.get().load(context.resources.getIdentifier(chatDetailModel.content,"drawable",context.packageName)).into(img_chatdetail)
                Glide.with(context).load(context.resources.getIdentifier(chatDetailModel.content,"drawable",context.packageName)).into(img_story)
            }
            else{
                Log.d("kiemta",""+chatDetailModel.content)
                //image
                txtChat.visibility=View.GONE
                layout_chat.visibility=View.GONE
                img_story.visibility=View.GONE
                layout_img.visibility=View.VISIBLE
                //Picasso.get().load(chatDetailModel.content).into(preview_image)
                Glide.with(context).load(chatDetailModel.content).into(preview_image)
                preview_image.setOnClickListener {
                    recyclerImgFullScreen.loadImg(chatDetailModel.content!!)
                }
            }



            if(position== 0){
                txtTime.visibility=View.VISIBLE
                txtTime.setText(getDateTime(chatDetailModel.timestamp!!))
            }
            else{
                if(chatDetailModel.timestamp!! - arrayList[position-1].timestamp!! > 3600000){
                    txtTime.visibility=View.VISIBLE
                    txtTime.setText(getDateTime(chatDetailModel.timestamp!!))
                }
            }
        }


        private fun getDateTime(s: Long): String? {
            val currentTimestamp = System.currentTimeMillis()
            var sdf: SimpleDateFormat ? =null

            if(currentTimestamp-s in 0..86400000)
            {
                //in day
                sdf = SimpleDateFormat("HH:mm a")
            }
            else if(currentTimestamp-s in 86400001..604800000){
                //in week
                sdf = SimpleDateFormat("E,HH:mm a")
            }
            else if(currentTimestamp-s in 604800001..31536000000){
                sdf = SimpleDateFormat("E,dd-M,HH:mm a")
            }
            else{
                sdf= SimpleDateFormat("dd-M-yyyy hh:mm")
            }

            try {

                val netDate = Date(s)
                return sdf.format(netDate)
            } catch (e: Exception) {
                return e.toString()
            }
        }


    }
}