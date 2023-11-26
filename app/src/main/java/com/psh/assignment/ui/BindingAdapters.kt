package com.psh.assignment.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.psh.assignment.data.model.BookType
import com.psh.assignment.data.model.SectionType
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@BindingAdapter("fileCount")
fun bindFileCount(fileCountTextView: TextView, fileCount: Int) {
    when (fileCount) {
        0 -> fileCountTextView.visibility = View.INVISIBLE
        1 -> {
            fileCountTextView.visibility = View.VISIBLE
            fileCountTextView.text = "$fileCount File"
        }

        else -> {
            fileCountTextView.visibility = View.VISIBLE
            fileCountTextView.text = "$fileCount Files"
        }
    }
}

@BindingAdapter("sectionTitle")
fun bindSectionTitle(titleTextView: TextView, sectionType: SectionType) {
    titleTextView.text = titleTextView.resources.getString(sectionType.titleResourceId)
}

@BindingAdapter("designImage")
fun bindDesignImage(designImage: ImageView, bookType: BookType) {
    designImage.setImageResource(bookType.imageResource)
}

@BindingAdapter("designVersion")
fun bindDesignVersion(versionTextView: TextView, versionNumber: Int) {
    versionTextView.text = "V$versionNumber"
}

@BindingAdapter("uploadedDate")
fun bindUploadedDate(uploadedTextView: TextView, uploadedDate: String) {
    val formattedDate = formatDate(uploadedDate)
    if (formattedDate.isEmpty()) {
        uploadedTextView.visibility = View.INVISIBLE
    } else {
        uploadedTextView.visibility = View.VISIBLE
        uploadedTextView.text = "Uploaded On: $formattedDate"
    }
}

/**
 * Formats the date to show in readable form
 * @param dateString unformatted date
 */
fun formatDate(dateString: String): String {
    val parserFormat =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault())
    val convertFormat =
        SimpleDateFormat("dd MMM, yy", Locale.getDefault())
    val date: Date?
    try {
        date = parserFormat.parse(dateString)
        if (date != null) {
            return convertFormat.format(date)
        }
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return ""
}