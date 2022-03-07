package id.belajar.controller

import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.ProgressBar
import kotlin.math.abs

class Helper {

    //membuat animasi transisi data
    class ProgressBarAnimation(private val mProgressBar: ProgressBar, fullDuration: Long) : Animation() {
        private var mTo = 0
        private var mFrom = 0
        private val mStepDuration: Long = fullDuration / mProgressBar.max
        fun setProgress(progress: Int) {
            var mProgress = progress
            if (mProgress < 0) {
                mProgress = 0
            }
            if (mProgress > mProgressBar.max) {
                mProgress = mProgressBar.max
            }
            mTo = mProgress
            mFrom = mProgressBar.progress
            duration = abs(mTo - mFrom) * mStepDuration
            mProgressBar.startAnimation(this)
        }

        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            val value = mFrom + (mTo - mFrom) * interpolatedTime
            mProgressBar.progress = value.toInt()
        }

    }

}