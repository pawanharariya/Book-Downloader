package com.psh.assignment.ui

import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.psh.assignment.R
import com.psh.assignment.data.model.Section
import com.psh.assignment.databinding.ListItemSectionBinding

class SectionAdapter(
    private val designItemListener: BookAdapter.DesignItemListener
) : ListAdapter<Section, SectionAdapter.SectionViewHolder>(DiffCallBack) {

    companion object DiffCallBack : DiffUtil.ItemCallback<Section>() {
        override fun areItemsTheSame(oldItem: Section, newItem: Section): Boolean {
            return oldItem.type == newItem.type
        }

        override fun areContentsTheSame(oldItem: Section, newItem: Section): Boolean {
            return oldItem == newItem
        }
    }

    inner class SectionViewHolder(private val binding: ListItemSectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(section: Section) {
            val resources = binding.root.resources
            val bookAdapter = BookAdapter(designItemListener)
            val designRecycler = binding.designRecycler
            designRecycler.apply {
                setHasFixedSize(true)
                adapter = bookAdapter
                addItemDecoration(BookItemDecorator(resources.getDimensionPixelSize(R.dimen.design_item_recycler_divider_height)))
                visibility = if (section.isExpanded) View.VISIBLE else View.GONE
            }
            bookAdapter.submitList(section.bookList)
            val expandSectionButton = binding.expandSectionButton
            rotateDropDown(expandSectionButton, section.isExpanded)
            binding.root.setOnClickListener {
                section.isExpanded = !section.isExpanded
                designRecycler.visibility = if (section.isExpanded) View.VISIBLE else View.GONE
                rotateDropDown(expandSectionButton, section.isExpanded)
            }
            binding.section = section
            binding.executePendingBindings()
        }
    }

    private fun rotateDropDown(button: ImageButton, expanded: Boolean) {
        val toAngle = if (expanded) COLLAPSED_DROP_DOWN_ANGLE else EXPANDED_DROP_DOWN_ANGLE
        val animator = ObjectAnimator.ofFloat(button, View.ROTATION, toAngle)
        animator.duration = ANIMATION_DURATION
        animator.start()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        return SectionViewHolder(
            ListItemSectionBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        val section = getItem(position)
        holder.bind(section)
    }
}

private const val EXPANDED_DROP_DOWN_ANGLE = -90f
private const val COLLAPSED_DROP_DOWN_ANGLE = 0f
private const val ANIMATION_DURATION = 300L