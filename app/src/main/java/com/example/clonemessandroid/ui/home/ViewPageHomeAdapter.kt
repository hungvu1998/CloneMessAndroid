package com.example.clonemessandroid.ui.home

import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.clonemessandroid.ui.home.local.LocalFragment
import com.example.clonemessandroid.ui.home.message.MessageFragment
import com.example.clonemessandroid.ui.home.story.StoryFragment

class ViewPageHomeAdapter (fm: FragmentManager) : FragmentStatePagerAdapter(fm)  {

    var messageFragment = MessageFragment()
    var storyFragment = StoryFragment()
    var locaFragment = LocalFragment()


    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return messageFragment
            1 -> return storyFragment
            2 -> return locaFragment
            else -> return locaFragment
        }
    }

    override fun getCount(): Int {
        return 3
    }





}
