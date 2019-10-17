package com.damiengo.websiterss.article

import android.text.Html
import android.text.Spanned
import androidx.core.text.HtmlCompat
import org.jsoup.nodes.Document
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ArticleUtil @Inject constructor() {

    companion object{

        const val titleCatMaxLength = 30
        const val inputDateFormat = "EEE, dd MMM yyyy HH:mm:ss z"
        const val outputValFormat = "HH:mm"

    }

    fun genTitle(title: String?): String {
        title?.let{
            val lastIdx = categoryLastIndex(it)

            if(lastIdx != -1) {
                return it.substring(lastIdx+3)
            }

            return it
        }

        return ""
    }

    fun genCategories(title: String?): MutableList<String> {
        title?.let {
            val lastIdx = categoryLastIndex(it)

            if(lastIdx != -1) {
                return it.substring(0, lastIdx).split(" - ").toMutableList()
            }
        }

        return arrayListOf()
    }

    fun genPubDate(pubDate: String?): String {
        pubDate?.let {
            if(pubDate == "") {
                return ""
            }
            val inputFormat = SimpleDateFormat(inputDateFormat, Locale.ENGLISH)
            val outputFormat = SimpleDateFormat(outputValFormat, Locale.ENGLISH)
            return outputFormat.format(inputFormat.parse(it))
        }

        return ""
    }

    fun genChapoFromDom(dom: Document): String {
        return dom.select(".Article__chapo").text()
    }

    fun genDescriptionFromDom(dom: Document): Spanned {
        val builder = StringBuilder()
        dom.select(".article__body .Paragraph").forEach { ele ->
            builder.append(ele.html()).append("<br /><br />")
        }
        var htmlDesc = builder.toString()
        // Delete paragraph
        htmlDesc = htmlDesc.replace("<p[^>]*>".toRegex(), "")
        htmlDesc = htmlDesc.replace("</p>", "")
        // Delete links
        htmlDesc = htmlDesc.replace("<a[^>]*>".toRegex(), "")
        htmlDesc = htmlDesc.replace("</a>", "")
        // Replace title
        htmlDesc = htmlDesc.replace("<h3[^>]*>".toRegex(), "<p><b>")
        htmlDesc = htmlDesc.replace("</h3>", "</b></p>")

        return HtmlCompat.fromHtml(htmlDesc, Html.FROM_HTML_MODE_LEGACY)
    }

    /**
     * Last index of dash in title.
     */
    private fun categoryLastIndex(title: String): Int {
        var titleCatLength = titleCatMaxLength
        if(title.length < titleCatMaxLength) {
            titleCatLength = title.length
        }

        return title.substring(0, titleCatLength).lastIndexOf(" - ")
    }

}