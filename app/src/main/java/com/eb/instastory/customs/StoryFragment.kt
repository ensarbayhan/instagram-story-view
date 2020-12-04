package com.eb.instastory.customs

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.eb.instastory.databinding.FragmentStoryBinding
import com.eb.instastory.listeners.PageLoader
import com.eb.instastory.listeners.ProgressFinishedListener
import com.eb.instastory.pojo.User
import com.eb.instastory.utils.*
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_story.*


/**
 * Created by ebayhan on 12/1/20.
 */
@SuppressLint("ClickableViewAccessibility")
class StoryFragment : Fragment(), View.OnTouchListener {

    private lateinit var binding: FragmentStoryBinding
    private lateinit var user: User
    private lateinit var pageLoader: PageLoader
    private var viewPager: StoryViewPager? = null
    private var currentStoryPosition = 0
    private var storyProgressViews = arrayListOf<StoryProgressView>()
    private var isHolding = false
    private var isViewPagerChanging = false

    companion object {
        private const val USER_KEY = "USER_KEY"

        fun newInstance(user: User): StoryFragment {
            return StoryFragment().apply {
                arguments = bundleOf(USER_KEY to user)
            }
        }
    }

    private val playerEventListener = object : Player.EventListener {
        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            if (playbackState == Player.STATE_READY) {
                binding.storyVideo.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                startProgressViewAnimation()
            }
        }
    }

    fun setPageLoader(pageLoader: PageLoader) {
        this.pageLoader = pageLoader
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentStoryBinding.inflate(inflater)
        viewPager = (container as? StoryViewPager)
        viewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {}

            override fun onPageScrollStateChanged(state: Int) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    isViewPagerChanging = true
                    pauseStory(hideViews = false)
                } else if (state == ViewPager.SCROLL_STATE_IDLE) {
                    isViewPagerChanging = false
                    resumeStory(hideViews = false)
                }
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments == null) {
            activity?.finish()
            return
        }
        user = arguments!!.getParcelable(USER_KEY)!!
        initView()
    }

    override fun onResume() {
        super.onResume()
        loadStory()
        resumeStory()
    }

    override fun onPause() {
        super.onPause()
        if (currentStoryPosition == -1) {
            currentStoryPosition = 0
        }
        if (currentStoryPosition > user.stories.size - 1) {
            currentStoryPosition = user.stories.size - 1
        }
        clearOtherProgressViewAnimations()
    }

    private fun initView() {
        createProgressBars()
        binding.storyImageCard.setOnTouchListener(this@StoryFragment)
        binding.profilePicture.loadUrl(user.profilePictureUrl, true)
        binding.userName.text = user.userName

        binding.sendButton.setOnClickListener {
            toast("Message sent!")
            binding.message.text.clear()
            binding.message.clearFocus()
            binding.message.hideKeyboard()
        }
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (isViewPagerChanging) return true

        val displayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        when (event?.action) {
            MotionEvent.ACTION_UP -> {
                if (!isHolding) {
                    if (event.x < screenWidth / 2) {
                        currentStoryPosition--
                    } else {
                        currentStoryPosition++
                    }
                    loadStory()
                } else {
                    resumeStory(isSlow = true)
                }
            }
            MotionEvent.ACTION_CANCEL -> {
                if (isHolding) {
                    resumeStory(isSlow = true)
                }
            }
            MotionEvent.ACTION_MOVE -> {
                pauseStory()
            }
        }
        return true
    }

    private fun loadStory() {
        if (!isActivityAlive()) {
            return
        }
        if (currentStoryPosition < 0) {
            pageLoader.loadPreviousPage()
            currentStoryPosition = 0
            return
        }
        if (currentStoryPosition > user.stories.size - 1) {
            pageLoader.loadNextPage()
            currentStoryPosition = user.stories.size - 1
            return
        }
        val story = user.stories[currentStoryPosition]
        binding.time.text = story.date.getTimeDiff()

        clearOtherProgressViewAnimations()
        if (story.isVideo()) {
            binding.storyImage.gone()
            binding.storyVideo.apply {
                loadUrl(story.url)
                player?.addListener(playerEventListener)
                player?.playWhenReady = true
                visible()
            }
        } else {
            binding.storyImage.loadUrl(story.url)
            binding.storyImage.visible()
            binding.storyVideo.gone()
            startProgressViewAnimation()
        }
    }

    private fun createProgressBars() {
        user.stories.forEach { _ ->
            val storyProgressView =
                    StoryProgressView(requireContext(), null, object : ProgressFinishedListener {
                        override fun onProgressFinished() {
                            currentStoryPosition++
                            isHolding = false
                            loadStory()
                        }
                    })
            storyProgressViews.add(storyProgressView)
            binding.progressBarLayout.addView(storyProgressView)
        }
    }

    private fun startProgressViewAnimation() {
        val progressView = storyProgressViews[currentStoryPosition]
        if (user.stories[currentStoryPosition].isVideo()) {
            progressView.setDuration(binding.storyVideo.player?.duration)
        } else {
            binding.storyVideo.player = null
        }
        progressView.post {
            progressView.startProgressAnimation()
        }
    }

    private fun clearOtherProgressViewAnimations() {
        storyProgressViews.forEachIndexed { index, it ->
            when {
                index < currentStoryPosition -> {
                    it.completeProgress()
                }
                else -> {
                    it.clearProgress()
                }
            }
        }
    }

    private fun pauseStory(hideViews: Boolean = true) {
        if (isHolding) return
        isHolding = true
        if (user.stories[currentStoryPosition].isVideo()) {
            activity?.runOnUiThread {
                binding.storyVideo.player?.playWhenReady = false
            }
        }
        storyProgressViews[currentStoryPosition].pauseProgressAnimation()
        if (hideViews) {
            binding.storyOverlayLayout.postDelayed({
                if (isHolding && !isViewPagerChanging) {
                    binding.storyOverlayLayout.slowlyGone()
                    viewPager?.disableScroll(true)
                }
            }, 200)
        }
    }

    private fun resumeStory(isSlow: Boolean = false, hideViews: Boolean = true) {
        if (!isHolding) return
        isHolding = false
        storyProgressViews[currentStoryPosition].resumeProgressAnimation()
        if (user.stories[currentStoryPosition].isVideo()) {
            activity?.runOnUiThread {
                binding.storyVideo.player?.playWhenReady = true
            }
        }

        if (hideViews) {
            if (isSlow) {
                binding.storyOverlayLayout.slowlyVisible()
            } else {
                binding.storyOverlayLayout.alpha = 1.0f
            }
        }
        viewPager?.disableScroll(false)
    }
}