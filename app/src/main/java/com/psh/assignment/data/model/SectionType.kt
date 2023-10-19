package com.psh.assignment.data.model

import com.psh.assignment.R
import com.squareup.moshi.Json

enum class SectionType(val titleResourceId: Int) {
    @Json(name = "2D")
    TYPE_2D(R.string.section_title_2d),

    @Json(name = "3D")
    TYPE_3D(R.string.section_title_3d),

    @Json(name = "PROD")
    TYPE_PROD(R.string.section_title_prod);
}