package com.psh.assignment.design

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.psh.assignment.data.model.Design
import com.psh.assignment.databinding.ListItemDesignBinding

class DesignAdapter(
    private val onClickListener: DesignItemClickListener
) :
    ListAdapter<Design, DesignAdapter.DesignViewHolder>(DiffCallBack) {

    companion object DiffCallBack : DiffUtil.ItemCallback<Design>() {
        override fun areItemsTheSame(oldItem: Design, newItem: Design): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Design, newItem: Design): Boolean {
            return oldItem == newItem
        }
    }

    inner class DesignViewHolder(private val binding: ListItemDesignBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(design: Design) {
            binding.design = design
            binding.root.setOnClickListener {
                onClickListener.onDesignItemClick(design)
            }
            binding.executePendingBindings()
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

    interface DesignItemClickListener {
        fun onDesignItemClick(designItem: Design)
    }
}