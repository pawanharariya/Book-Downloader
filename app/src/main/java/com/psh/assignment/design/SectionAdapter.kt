package com.psh.assignment.design

import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.psh.assignment.R
import com.psh.assignment.data.model.Section
import com.psh.assignment.databinding.ListItemSectionBinding

class SectionAdapter(
    private val designItemClickListener: DesignAdapter.DesignItemClickListener
) : RecyclerView.Adapter<SectionAdapter.SectionViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Section>() {
        override fun areItemsTheSame(oldItem: Section, newItem: Section): Boolean {
            return oldItem.type == newItem.type
        }

        override fun areContentsTheSame(oldItem: Section, newItem: Section): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    inner class SectionViewHolder(private val binding: ListItemSectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(section: Section) {
            val resources = binding.root.resources
            val designAdapter = DesignAdapter(designItemClickListener)
            val designRecycler = binding.designRecycler
            designRecycler.apply {
                setHasFixedSize(true)
                adapter = designAdapter
                addItemDecoration(DesignItemDecorator(resources.getDimensionPixelSize(R.dimen.design_item_recycler_divider_height)))
                visibility = if (section.isExpanded) View.VISIBLE else View.GONE
            }
            designAdapter.submitList(section.designList)
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

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        val section = differ.currentList[position]
        holder.bind(section)
    }
}

private const val EXPANDED_DROP_DOWN_ANGLE = -90f
private const val COLLAPSED_DROP_DOWN_ANGLE = 0f
private const val ANIMATION_DURATION = 300L