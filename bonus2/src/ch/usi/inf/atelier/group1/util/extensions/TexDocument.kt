/*
 * Copyright (c) 2018 Bevilacqua Joey.
 */
package ch.usi.inf.atelier.group1.util.extensions

import ch.usi.inf.atelier.group1.jekyll.HtmlToLatexWriter
import ch.usi.inf.atelier.group1.jekyll.JekyllPage
import ch.usi.inf.atelier.group1.util.Log
import java.io.File
import java.io.FileWriter

/**
 * Insert author and title from Jekyll file
 *
 * @param file File from which the author will be read
 */
fun HtmlToLatexWriter.insertJekyllHeader(file: JekyllPage) {
    val title = file.header["title"] ?: "Unknown title"
    val author = file.header["author"] ?: "Unknown author"

    addTitle(title)
    addAuthor(author)
}