package com.psh.assignment.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.psh.assignment.data.model.Book
import com.psh.assignment.databinding.ListItemBookBinding
import com.psh.assignment.util.downloader.DownloadProgressListener

class BookAdapter(
    private val itemListener: DesignItemListener
) : ListAdapter<Book, BookAdapter.DesignViewHolder>(DiffCallBack) {

    companion object DiffCallBack : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }
    }

    inner class DesignViewHolder(private val binding: ListItemBookBinding) :
        RecyclerView.ViewHolder(binding.root), DownloadProgressListener {
        fun bind(book: Book) {
            binding.design = book
            binding.root.setOnClickListener {
                itemListener.onDesignItemClick(book)
            }
            itemListener.onDesignDownloadObserve(this, book.id)
            binding.downloadProgressView.setOnClickListener {
                itemListener.onDesignDownloadCancel(book.id)
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
            ListItemBookBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: DesignViewHolder, position: Int) {
        val design = getItem(position)
        holder.bind(design)
    }

    interface DesignItemListener {
        fun onDesignItemClick(bookItem: Book)
        fun onDesignDownloadObserve(
            downloadProgressListener: DownloadProgressListener,
            designId: String
        )

        fun onDesignDownloadCancel(designId: String)

    }
}