/*
 * Copyright (c) 2018. Bevilacqua Joey
 */
package ch.usi.inf.atelier.group1.html

import ch.usi.inf.atelier.group1.tex.TexDocument
import ch.usi.inf.atelier.group1.util.Log
import ch.usi.inf.atelier.group1.util.extensions.writeJekyllHeader
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.io.File

class HtmlParser(path: String) {
    private val file = JekyllHtmlFile(File(path))
    private val document = Jsoup.parse(file.content)
    private val texDocument = TexDocument()

    fun parse(): String {
        if (!file.isValid()) {
            return ""
        }

        texDocument.start()
        texDocument.writeJekyllHeader(file)
        texDocument.beginDocument()

        document.body().children().forEach { element -> parseToTex(element) }

        texDocument.endDocument()

        return texDocument.toString()
    }

    private fun parseToTex(element: Element) {
        val text = element.ownText()

        // TODO: fix span elements inside the paragraph senteces (tag-less items)

        texDocument.apply {
            when (element.tagName()) {
                TAG_BOLD -> addBold(text)
                TAG_BR -> addBr()
                TAG_CODE -> addCode(text)
                TAG_H1 -> addSection(text)
                TAG_H2 -> addSubSection(text)
                TAG_H3, TAG_H4 -> addSubSubSection(text)
                TAG_ITALICS -> addItalics(text)
                TAG_LINK -> addLink(text, element.attr("href"))
                TAG_P -> addParagraph(text)
                TAG_PRE -> addPre(text)
                TAG_UL -> element.parseList()
                "", null -> addText(element.text())
            }
        }
    }

    private fun Element.parseList() {
        texDocument.apply {
            beginList()

            children().filter { TAG_LI == it.tagName() }
                    .forEach { e -> addListItem(e.text()) }

            endList()
        }
    }

    companion object {
        private const val TAG_BOLD = "b"
        private const val TAG_BR = "br"
        private const val TAG_CODE = "code"
        private const val TAG_H1 = "h1"
        private const val TAG_H2 = "h2"
        private const val TAG_H3 = "h3"
        private const val TAG_H4 = "h4"
        private const val TAG_ITALICS = "i"
        private const val TAG_LI = "li"
        private const val TAG_LINK = "a"
        private const val TAG_P = "p"
        private const val TAG_PRE = "pre"
        private const val TAG_UL = "ul"
    }
}