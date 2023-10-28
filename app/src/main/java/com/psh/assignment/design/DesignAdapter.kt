package com.psh.assignment.design

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.psh.assignment.data.model.Design
import com.psh.assignment.databinding.ListItemDesignBinding
import com.psh.assignment.util.downloader.DownloadProgressListener

class DesignAdapter(
    private val itemListener: DesignItemListener
) : ListAdapter<Design, DesignAdapter.DesignViewHolder>(DiffCallBack) {

    companion object DiffCallBack : DiffUtil.ItemCallback<Design>() {
        override fun areItemsTheSame(oldItem: Design, newItem: Design): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Design, newItem: Design): Boolean {
            return oldItem == newItem
        }
    }

    inner class DesignViewHolder(private val binding: ListItemDesignBinding) :
        RecyclerView.ViewHolder(binding.root), DownloadProgressListener {
        fun bind(design: Design) {
            binding.design = design
            binding.root.setOnClickListener {
                itemListener.onDesignItemClick(design)
            }
            itemListener.onDesignDownloadObserve(this, design.id)
            binding.downloadProgressView.setOnClickListener {
                itemListener.onDesignDownloadCancel(design.id)
            }
            binding.executePendingBindings()
        }

        override fun onProgressUpdate(progress: Int) {
            binding.downloadProgressView.setProgress(progress)
        }

        override fun onProgressCancel() {
            binding.downloadProgressView.cancelProgress()
        }

        override fun onProgressComplete() {
            binding.downloadProgressView.progressComplete()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DesignViewHolder {
        return DesignViewHolder(
            ListItemDesignBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: DesignViewHolder, position: Int) {
        val design = getItem(position)
        holder.bind(design)
    }

    interface DesignItemListener {
        fun onDesignItemClick(designItem: Design)
        fun onDesignDownloadObserve(
            downloadProgressListener: DownloadProgressListener,
            designId: String
        )

        fun onDesignDownloadCancel(designId: String)

    }
}