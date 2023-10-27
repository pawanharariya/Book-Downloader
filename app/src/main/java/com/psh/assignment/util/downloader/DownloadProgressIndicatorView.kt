package com.psh.assignment.util.downloader

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.psh.assignment.R

class DownloadProgressIndicatorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {
    private var progressBar: ProgressBar
    private var cancelIcon: ImageView
    private var completeIcon: ImageView

    init {
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        layoutInflater.inflate(R.layout.download_progress_indicator_view, this)
        progressBar = findViewById(R.id.progress_bar)
        cancelIcon = findViewById(R.id.cancel_icon)
        completeIcon = findViewById(R.id.complete_icon)
    }

    fun setProgress(progress: Int) {
        startProgress()
        progressBar.progress = progress
    }

    private fun startProgress() {
        progressBar.visibility = View.VISIBLE
        cancelIcon.visibility = View.VISIBLE
    }

    fun cancelProgress() {
        progressBar.visibility = View.INVISIBLE
        cancelIcon.visibility = View.INVISIBLE
    }

    fun progressComplete() {
        progressBar.visibility = View.VISIBLE
        progressBar.progress = 100
        completeIcon.visibility = View.VISIBLE
        cancelIcon.visibility = View.INVISIBLE
    }
}