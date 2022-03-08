package id.belajar.controller

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.ProgressBar
import java.text.SimpleDateFormat
import java.util.*
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

    //konversi timestamp ke date
    fun getDateTime(s: String?): String? {
        return try {
            val sdf = SimpleDateFormat("dd/MM - HH:mm:ss")
            val netDate = s?.toLong()?.let { Date(it) }
//            val netDate = s?.toLong()?.times(1000)?.let { Date(it) }
            sdf.format(netDate)
        } catch (e: Exception) {
            e.toString()
        }
    }

    //cek koneksi internet
    fun checkConnection(context: Context): Boolean {
        val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                        return true
                    }
                }
            }
        }
        return false
    }

}