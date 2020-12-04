package com.eb.instastory.customs

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.animation.doOnEnd
import com.eb.instastory.databinding.StoryProgressViewBinding
import com.eb.instastory.listeners.ProgressFinishedListener
import com.eb.instastory.utils.SEC
import com.eb.instastory.utils.dp
import com.eb.instastory.utils.gone
import com.eb.instastory.utils.visible


/**
 * Created by ebayhan on 12/2/20.
 */
class StoryProgressView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        private var progressFinishedListener: ProgressFinishedListener? = null
) : FrameLayout(context, attrs) {

    private var binding: StoryProgressViewBinding =
            StoryProgressViewBinding.inflate(LayoutInflater.from(context), this, true)

    private var progressAnimationSet = AnimatorSet()
    private var duration: Long = 5L * SEC

    init {
        layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
            weight = 1f
            leftMargin = 7.dp
            rightMargin = 7.dp
        }
    }

    fun setDuration(duration: Long?) {
        duration?.let { this.duration = duration }
    }

    fun startProgressAnimation() {
        binding.frontProgress.visible()
        val valueAnimator = ValueAnimator
                .ofInt(0, binding.backProgress.width)
                .setDuration(duration)

        valueAnimator.addUpdateListener {
            binding.frontProgress.layoutParams.width = it.animatedValue as Int
            binding.frontProgress.requestLayout()
        }

        progressAnimationSet.interpolator = LinearInterpolator()
        progressAnimationSet.play(valueAnimator)
        progressAnimationSet.start()
        progressAnimationSet.doOnEnd {
            progressFinishedListener?.onProgressFinished()
        }
    }

    fun completeProgress() {
        binding.frontProgress.visible()
        binding.frontProgress.layoutParams.width = binding.backProgress.width
        clearProgressAnimation()
    }

    fun clearProgress() {
        binding.frontProgress.gone()
        clearProgressAnimation()
    }

    private fun clearProgressAnimation() {
        progressAnimationSet.removeAllListeners()
        progressAnimationSet.end()
        progressAnimationSet.cancel()
    }

    fun pauseProgressAnimation() {
        if (!progressAnimationSet.isPaused) {
            progressAnimationSet.pause()
        }
    }

    fun resumeProgressAnimation() {
        if (progressAnimationSet.isPaused) {
            progressAnimationSet.resume()
        }
    }
}