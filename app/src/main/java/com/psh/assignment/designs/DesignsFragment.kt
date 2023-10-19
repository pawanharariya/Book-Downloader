package com.psh.assignment.designs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.psh.assignment.R
import com.psh.assignment.data.model.Design
import com.psh.assignment.data.model.DesignType
import com.psh.assignment.data.model.Section
import com.psh.assignment.data.model.SectionType
import com.psh.assignment.databinding.FragmentDesignsBinding

class DesignsFragment : Fragment() {
    private lateinit var binding: FragmentDesignsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_designs, container, false)
        val sections = getData()
        binding.designSectionsRecycler.adapter =
            SectionAdapter(sections, DesignAdapter.OnClickListener {
            })
        return binding.root
    }

    private fun getData(): List<Section> {
        val design1 =
            Design("1", "Design Name", DesignType.TYPE_DOC, SectionType.TYPE_2D, "file", 2, "2023-04-24T09:04:20.503933+05:30")
        val design2 =
            Design("2", "Layout Name", DesignType.TYPE_IMAGE, SectionType.TYPE_2D, "file", 4, "2023-04-24T09:04:20.503933+05:30")
        val design3 =
            Design("3", "2D Name", DesignType.TYPE_DOC, SectionType.TYPE_2D, "file", 12, "2023-04-24T09:04:20.503933+05:30")
        val design4 =
            Design("4", "3D Name", DesignType.TYPE_DXF, SectionType.TYPE_2D, "file", 12, "")
        val design5 =
            Design("5", "Design Name", DesignType.TYPE_DOC, SectionType.TYPE_2D, "file", 20, "")
        val sectionA =
            Section(SectionType.TYPE_2D, listOf(design1, design2, design3, design4, design5), false)

        val sectionB =
            Section(SectionType.TYPE_3D, listOf(design1, design2), false)

        val sectionC =
            Section(SectionType.TYPE_PROD, listOf(design3, design4, design5), false)

        return listOf(sectionA, sectionB, sectionC)
    }
}