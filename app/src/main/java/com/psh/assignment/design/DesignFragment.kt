package com.psh.assignment.design

import android.app.DownloadManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.psh.assignment.R
import com.psh.assignment.databinding.FragmentDesignsBinding
import com.psh.assignment.util.downloader.DownloadProgressLiveData
import com.psh.assignment.util.downloader.FileDownloader
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DesignFragment : Fragment() {
    private lateinit var viewModel: DesignViewModel
    private lateinit var binding: FragmentDesignsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_designs, container, false)
        viewModel = ViewModelProvider(this)[DesignViewModel::class.java]
        viewModel.sections.observe(viewLifecycleOwner) {
//            binding.designSectionsRecycler.adapter =
//                SectionAdapter(it, DesignAdapter.OnClickListener {})
        }
        val progressIndicatorView = binding.progressIndicator
        binding.titleLayout.setOnClickListener {
            val downloader = context?.let { cntxt ->
//                val url =
//                    "https://ik.imagekit.io/91sqft/dev/design-file-version/a10a84b51d5d4b5aa35e66067a732930.jpeg"
                val url =
                    "https://www.thebookcollector.co.uk/sites/default/files/the-book-collector-example-2018-04.pdf"
                val fileName = url.substring(url.lastIndexOf('/') + 1)
                val mimeType = getMimeFromFileName(fileName)
                mimeType?.let {
                    val requestId = FileDownloader(cntxt).downloadFile(url, mimeType, fileName)
                    val progress = DownloadProgressLiveData(requireContext(), requestId)
                    progress.observe(viewLifecycleOwner) { downloadItem ->
                        val progressPercentage =
                            ((downloadItem.bytesDownloadedSoFar * 100L) / downloadItem.totalSizeBytes).toInt()
                        Log.e("DownloadProgress", progressPercentage.toString())
                        progressIndicatorView.setProgress(progressPercentage)
                        if (downloadItem.status == DownloadManager.STATUS_SUCCESSFUL) {
                            progressIndicatorView.onProgressComplete()
                        }

                    }

                    progressIndicatorView.setOnClickListener {
                        progress.observe(viewLifecycleOwner) {
                            if (it.status != DownloadManager.STATUS_SUCCESSFUL) {
                                
                                progressIndicatorView.cancelProgress()
                            }
                        }
                    }
                }
            }
        }

        return binding.root
    }

    private fun getMimeFromFileName(fileName: String): String? {
        val map = MimeTypeMap.getSingleton()
        val ext = MimeTypeMap.getFileExtensionFromUrl(fileName)
        return map.getMimeTypeFromExtension(ext)
    }
}