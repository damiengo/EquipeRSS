package com.damiengo.websiterss.ui.articledetail.model

import com.damiengo.websiterss.article.json.Paragraph
import org.jsoup.Jsoup

class ModelFactory {

    companion object {
        val LAYOUT_CHAPO: String = "chapo"
        val LAYOUT_PARAGRAPH: String = "text"
        val LAYOUT_EMBED: String = "embed"
        val LAYOUT_DIGIT: String = "digit"
        val LAYOUT_CITATION: String = "citation"
        val LAYOUT_NOTE: String = "note"
        val LAYOUT_FOCUS: String = "focus"
    }

    fun buildFromParagraph(p: Paragraph): Model {
        return when(p.layout) {
            LAYOUT_CHAPO     -> ChapoModel(p.getContentText())
            LAYOUT_PARAGRAPH -> ParagraphModel(p.getContentText())
            LAYOUT_EMBED     -> buildEmbedFromParagraph(p)
            LAYOUT_DIGIT     -> DigitModel(p.getTitleText(), p.getContentText())
            LAYOUT_CITATION  -> CitationModel(p.getContentText(), p.getCaptionText())
            LAYOUT_NOTE      -> NoteModel(p.getContentText(), p.getNoteLabelText(), p.getNoteRatingText())
            LAYOUT_FOCUS     -> FocusModel(p.getContentText())
            else             -> EmptyModel()
        }
    }

    private fun buildEmbedFromParagraph(p: Paragraph): Model {
        if(p.content.contains("twitter-tweet")) {
            return buildTwitterModelFromParagraph(p)
        }

        return EmptyModel()
    }

    private fun buildTwitterModelFromParagraph(p: Paragraph): TwitterModel {
        val htmlTweet = Jsoup.parse(p.getContentText())
        val content = htmlTweet.select("blockquote > p").html().replace("<a[^>]*>(.*)<\\/a>".toRegex(), "$1").trim()
        val picture = ""
        val date = htmlTweet.select("blockquote > a").text().trim()
        val link = htmlTweet.select("blockquote > a").attr("href")
        val author = htmlTweet.select("blockquote").html().replace("<p[^>]*>.*<\\/p>".toRegex(), "").replace("<a[^>]*>.*<\\/a>".toRegex(), "").trim()

        val model = TwitterModel()

        model.content = content
        model.picture = picture
        model.date = date
        model.link = link
        model.author = author

        return model
    }

}