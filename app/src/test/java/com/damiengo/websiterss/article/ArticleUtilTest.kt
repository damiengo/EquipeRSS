package com.damiengo.websiterss.article

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

class ArticleUtilTest {

    private lateinit var u: ArticleUtil

    @Before
    fun setUp() {
        u = ArticleUtil()
    }

    @Test
    fun genTitleWithDashLessThanMaxSize() {
        assertEquals("dashes", u.genTitle("Title with - dashes"))
    }

    @Test
    fun genTitleWithDashesLessThanMaxSize() {
        assertEquals("dash 3", u.genTitle("Title with - dash1 - dash 2 - dash 3"))
    }

    @Test
    fun genTitleWithDashesLessAndMoreThanMaxSize() {
        assertEquals("dash 3 - dash 4", u.genTitle("Title with - dash1 - dash 2 - dash 3 - dash 4"))
    }

    @Test
    fun genTitleWithDashMoreThanMaxSize() {
        assertEquals("Title with dashes and a size of title larger than - 30 characters", u.genTitle("Title with dashes and a size of title larger than - 30 characters"))
    }

    @Test
    fun genTitleWithoutDashes() {
        assertEquals("Title without dashes", u.genTitle("Title without dashes"))
    }

    @Test
    fun genTitleEmpty() {
        assertEquals("", u.genTitle(""))
    }

    @Test
    fun genTitleNull() {
        assertEquals("", u.genTitle(null))
    }

    @Test
    fun genCategoriesOneCategory() {
        assertEquals(mutableListOf("Category 1"), u.genCategories("Category 1 - A title with only one category"))
    }

    @Test
    fun genCategoriesMoreThanOneCategory() {
        assertEquals(mutableListOf("Category 1", "Category 2"), u.genCategories("Category 1 - Category 2 - A title with several categories"))
    }

    @Test
    fun genCategoriesNoCategory() {
        assertEquals(mutableListOf<String>(), u.genCategories("A title with no category detected because to long title - Title"))
    }

    @Test
    fun genCategoriesNullCategory() {
        assertEquals(mutableListOf<String>(), u.genCategories(null))
    }

    @Test
    fun getPubDateFormatOk() {
        assertEquals("20:51", u.genPubDate("Wed, 09 Oct 2019 20:51:02 +0200"))
    }

    @Test
    fun genPubDateNoDate() {
        assertEquals("", u.genPubDate(""))
    }

    @Test
    fun genPubDateNullDate() {
        assertEquals("", u.genPubDate(null))
    }

    @Test
    fun genChapoFromDomOK() {
        assertEquals("Le nouvel entraîneur de la Sampdoria veut éviter au club de Gênes de descendre en Serie B.", u.genChapoFromDom(getDom()))
    }

    @Test
    fun genDescriptionFromDomOK() {
        assertEquals("zzzz", u.genDescriptionFromDom(getDom()).toString())
    }

    private fun getDom(): Document {
        val res = javaClass.classLoader.getResourceAsStream("lequipe_article.html")
        return Jsoup.parse(res, "UTF-8", "www.lequipe.fr")
    }

}