package com.psh.assignment.design

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.psh.assignment.R
import com.psh.assignment.data.model.Section
import com.psh.assignment.databinding.ListItemSectionBinding

class SectionAdapter(
    private val sectionList: List<Section>,
    private val onClickListener: DesignAdapter.OnClickListener
) : RecyclerView.Adapter<SectionAdapter.SectionViewHolder>() {

    inner class SectionViewHolder(private val binding: ListItemSectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(section: Section) {
            val resources = binding.root.resources
            val designAdapter = DesignAdapter(onClickListener)
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
//                fadeChildRecyclerView(designRecycler, section.isExpanded)
//                notifyItemChanged(adapterPosition)
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

    private fun fadeChildRecyclerView(recycler: RecyclerView, expanded: Boolean) {
        val toValue = if (expanded) 10f else -10f
        val translateY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, toValue)
//        val fade = PropertyValuesHolder.ofFloat(View.ALPHA, 0f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(
            recycler,
            translateY,
        )
        animator.duration = ANIMATION_DURATION
        animator.start()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        return SectionViewHolder(
            ListItemSectionBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun getItemCount(): Int {
        return sectionList.size
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        val section = sectionList[position]
        holder.bind(section)
    }
}

private const val EXPANDED_DROP_DOWN_ANGLE = -90f
private const val COLLAPSED_DROP_DOWN_ANGLE = 0f
private const val ANIMATION_DURATION = 300L