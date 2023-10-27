package com.psh.assignment.design

import android.app.DownloadManager
import android.os.Bundle
import android.util.Log
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
import com.psh.assignment.util.downloader.DownloadProgressListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DesignFragment : Fragment(), DesignAdapter.DesignItemListener {
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
            sectionAdapter.submitList(it)
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

    override fun onDesignDownloadObserve(
        downloadProgressListener: DownloadProgressListener,
        designId: String
    ) {
        Log.e("Progress", "Inside Design Download Observe")
        viewModel.progressLiveDataMap.observe(viewLifecycleOwner) {
            it?.let {
                it[designId]?.observe(viewLifecycleOwner) { downloadItem ->
                    Log.e("Progress", "Status = ${downloadItem.status.toString()}")
                    when (downloadItem.status) {
                        DownloadManager.STATUS_RUNNING ->
                            downloadProgressListener.onProgressUpdate(downloadItem.getProgress())

                        DownloadManager.STATUS_SUCCESSFUL -> downloadProgressListener.onProgressComplete()
                        else -> downloadProgressListener.onProgressCancel()
                    }
                }
            }
        }
    }

    override fun onDesignDownloadCancel(designId: String) {
        viewModel.cancelDownload(designId)
    }
}