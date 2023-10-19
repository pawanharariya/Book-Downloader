package com.psh.assignment.designs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            binding.designsRecycler.apply {
                setHasFixedSize(true)
                adapter = designAdapter
                addItemDecoration(
                    DesignItemDecorator(
                        resources.getDimensionPixelSize(R.dimen.design_item_recycler_divider_height)
                    )
                )
                visibility = if (section.isExpanded) View.VISIBLE else View.GONE
            }
            designAdapter.submitList(section.designList)
            binding.expandSectionButton.setOnClickListener {
                section.isExpanded = !section.isExpanded
                binding.designsRecycler.visibility =
                    if (section.isExpanded) View.VISIBLE else View.GONE
//                notifyItemChanged(adapterPosition)
            }
            binding.section = section
            binding.executePendingBindings()
        }
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