package com.psh.assignment.design

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.psh.assignment.R
import com.psh.assignment.data.model.Design
import com.psh.assignment.databinding.FragmentDesignsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DesignFragment : Fragment(), DesignAdapter.DesignItemClickListener {
    private lateinit var viewModel: DesignViewModel
    private lateinit var binding: FragmentDesignsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_designs, container, false)
        viewModel = ViewModelProvider(this)[DesignViewModel::class.java]
        val sectionAdapter = SectionAdapter(this)
        binding.designSectionsRecycler.adapter = sectionAdapter
        viewModel.sections.observe(viewLifecycleOwner) {
            sectionAdapter.differ.submitList(it)
        }

        viewModel.snackbarText.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    override fun onDesignItemClick(designItem: Design) {
        viewModel.downloadFileAttached(designItem)
    }
}