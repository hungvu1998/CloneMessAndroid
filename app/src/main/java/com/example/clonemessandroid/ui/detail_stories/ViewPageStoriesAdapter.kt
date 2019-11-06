package com.example.clonemessandroid.ui.detail_stories

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.clonemessandroid.data.model.Stories
import com.example.clonemessandroid.ui.home.story.StoryFragment


class ViewPageStoriesAdapter (fm: FragmentManager) : FragmentStatePagerAdapter(fm)  {

    var arrayListStories: ArrayList<Stories> = ArrayList()



    override fun getItem(position: Int): Fragment {
        var storyFragment=StoryFragment()
        storyFragment.setModel(arrayListStories[position])
        return storyFragment
    }

    override fun getCount(): Int {

        return arrayListStories.size


    }
    fun setListStories(arrayListStories: ArrayList<Stories>) {
        this.arrayListStories = arrayListStories
        notifyDataSetChanged()
    }


}


