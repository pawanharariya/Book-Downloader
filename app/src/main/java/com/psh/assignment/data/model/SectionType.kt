package com.psh.assignment.data.model

import com.psh.assignment.R
import com.squareup.moshi.Json

enum class SectionType(val titleResourceId: Int) {
    @Json(name = "SCIFI")
    SCIFI(R.string.section_title_scifi),

    @Json(name = "TECH")
    TECH(R.string.section_title_space),

    @Json(name = "SPACE")
    SPACE(R.string.section_title_tech);
}