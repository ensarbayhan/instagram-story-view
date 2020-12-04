package com.eb.instastory.customs

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.eb.instastory.pojo.User
import com.eb.instastory.listeners.PageLoader

/**
 * Created by ebayhan on 12/1/20.
 */
class StoryViewPagerAdapter(fragmentManager: FragmentManager,
                            private val users: ArrayList<User>, private
                            val pageLoader: PageLoader)
    : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return StoryFragment.newInstance(users[position]).apply {
            setPageLoader(pageLoader)
        }
    }

    override fun getCount(): Int {
        return users.size
    }
}