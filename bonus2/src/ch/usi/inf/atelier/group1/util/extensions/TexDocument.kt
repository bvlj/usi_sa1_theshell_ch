/*
 * Copyright (c) 2018. Bevilacqua Joey
 */
package ch.usi.inf.atelier.group1.util.extensions

import ch.usi.inf.atelier.group1.html.JekyllHtmlFile
import ch.usi.inf.atelier.group1.tex.TexDocument

fun TexDocument.writeJekyllHeader(file: JekyllHtmlFile) {
    val title = file.header["title"] ?: "Unknown title"
    val author = file.header["author"] ?: "Unknown author"

    setTitle(title)
    setAuthor(author)
}