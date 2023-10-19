package com.psh.assignment.data.model

import com.psh.assignment.R
import com.squareup.moshi.Json

enum class DesignType(val imageResource: Int) {
    @Json(name = "DOC")
    TYPE_DOC(R.drawable.ic_doc),

    @Json(name = "IMAGE")
    TYPE_IMAGE(R.drawable.ic_image),

    @Json(name = "DXF")
    TYPE_DXF(R.drawable.ic_dxf),
}