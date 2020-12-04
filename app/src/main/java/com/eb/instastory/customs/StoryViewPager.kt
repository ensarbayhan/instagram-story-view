package com.eb.instastory.customs

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.eb.instastory.pojo.User
import com.eb.instastory.listeners.PageLoader
import com.eb.instastory.utils.CubeTransformer


/**
 * Created by ebayhan on 12/1/20.
 */
@SuppressLint("ClickableViewAccessibility")
class StoryViewPager @JvmOverloads constructor(context: Context,
                                               attrs: AttributeSet? = null) : ViewPager(context, attrs) {

    init {
        setPageTransformer(true, CubeTransformer())
    }

    private var disable = false

    private val pageLoader = object : PageLoader {
        override fun loadPreviousPage() {
            currentItem -= 1
        }

        override fun loadNextPage() {
            currentItem += 1
        }
    }

    fun setUsers(fragmentManager: FragmentManager, users: ArrayList<User>) {
        users.sortByDescending {
            it.stories.sortBy { story ->
                story.date
            }
            it.stories.last().date
        }
        adapter = StoryViewPagerAdapter(fragmentManager, users, pageLoader)
    }

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        return !disable && super.onInterceptTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return !disable && super.onTouchEvent(event)
    }

    fun disableScroll(disable: Boolean) {
        this.disable = disable
    }
}