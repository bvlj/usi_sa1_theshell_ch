/*
 * Copyright (c) 2018 Bevilacqua Joey.
 */

package ch.usi.inf.atelier.group1

import ch.usi.inf.atelier.group1.jekyll.HtmlToLatexWriter
import ch.usi.inf.atelier.group1.jekyll.JekyllPage
import ch.usi.inf.atelier.group1.util.Log
import ch.usi.inf.atelier.group1.util.extensions.insertJekyllHeader
import java.io.File
import java.io.FileWriter
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

class HtmlParser(private val singlePage: Boolean) {
    private val content = StringBuilder()
    private var outName = "${SimpleDateFormat("yyyy-MM-dd_hh-mm").format(Date())}.html"

    /**
     * Parse a jekyll html file to a LaTex Document
     *
     * @param path The path of the html file
     */
    fun parse(path: File) {
        val file = JekyllPage(path)
        val output = HtmlToLatexWriter(file.content, singlePage)

        // Make sure this is a jekyll html file
        if (!file.isValid()) {
            Log.e(IllegalArgumentException("This file is not valid"), false)
            return
        }

        output.run {
            if (singlePage) {
                // In singlePage mode, insert the beginning of the document only once
                if (content.isEmpty()) {
                    start()
                    addSinglePageInfo("Documentation", "Group 1")
                    beginDocument()
                }

                insertJekyllHeader(file)
            } else {
                start()
                insertJekyllHeader(file)
                beginDocument()
            }

            // Convert special chars
            changeSpecialChars()

            // Convert html elements
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

            // Strip html comments and images
            stripComments()
            stripImg()

            // Store the converted document
            commit()

            if (!singlePage) {
                // End the document if singlePage is not enabled
                endDocument()
            }
        }

        Log.i("Parsed: $path")

        content.append(output.toString())

        // Set the file name basing on the original html file name if not running in singlePage mode
        if (!singlePage) {
            outName = path.absolutePath
        }
    }

    fun save() {
        val document = if (singlePage) {
            // End the singlePage'd document
            HtmlToLatexWriter(content.toString(), true).run {
                commit()
                endDocument()
                toString()
            }
        } else {
            content.toString()
        }


        // No ned to save an empty document
        if (document.isEmpty()) {
            return
        }


        val outDir = File("out", if (singlePage) "" else File(outName).parent)

        if (!outDir.exists()) {
            outDir.mkdirs()
        }

        // Save the LaTeX document
        val file = File("out", outName.replace(".html", ".tex"))

        val writer = FileWriter(file, false)

        Log.i("${file.path} created")

        writer.write(document)
        writer.flush()
        writer.close()
    }
}