package com.psh.assignment.data.model

data class Section(
    /**
     * [SectionType] - Type of the section
     */
    val type: SectionType,

    /**
     * List of design layouts that belong to the section
     */
    var designList: List<Design>,

    /**
     * State of section - Expanded or Collapsed
     */
    var isExpanded: Boolean = false
)
