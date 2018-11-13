/*
 * Copyright (c) 2018 Bevilacqua Joey.
 */

package ch.usi.inf.atelier.group1

import ch.usi.inf.atelier.group1.jekyll.HtmlToLatexWriter
import ch.usi.inf.atelier.group1.jekyll.JekyllPage
import ch.usi.inf.atelier.group1.util.Log
import ch.usi.inf.atelier.group1.util.extensions.insertJekyllHeader
import ch.usi.inf.atelier.group1.util.extensions.writeTo
import java.io.File

class HtmlParser(private val input: File) {
    private val file = JekyllPage(input)
    private val output = HtmlToLatexWriter(file.content)

    fun parse(): String {
        if (!file.isValid()) {
            Log.e(IllegalArgumentException("This file is not valid"))
        }

        /*
        if (file.header["author"] == "Marwan Announ") {
            throw IllegalStateException("Invalid fuckery. Please don\'t attemp to parse this shit. Kthxbye")
        }
        */

        output.run {
            start()
            insertJekyllHeader(file)
            beginDocument()

            changeBold()
            changeBr()
            changeCode()
            changeItalics()
            changeLink()
            changeList()
            changeListItem()
            changeMono()
            changeParagraph()
            changeSection()
            changeSubSection()
            changeSubSubSection()
            changeTable()
            changeUnderline()

            stripComments()

            endDocument()

            writeTo(input.absolutePath.replace(".html", ".tex"))
        }

        return output.toString()
    }

}