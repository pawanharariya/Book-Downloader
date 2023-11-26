package com.psh.assignment.data.model

import com.psh.assignment.R
import com.squareup.moshi.Json

enum class BookType(val imageResource: Int) {
    @Json(name = "DOC")
    DOC(R.drawable.ic_doc),

    @Json(name = "IMAGE")
    IMAGE(R.drawable.ic_image),

    @Json(name = "DXF")
    DXF(R.drawable.ic_dxf),
}