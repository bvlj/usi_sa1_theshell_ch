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
 */
fun HtmlToLatexWriter.insertJekyllHeader(file: JekyllPage) {
    val title = file.header["title"] ?: "Unknown title"
    val author = file.header["author"] ?: "Unknown author"

    addTitle(title)
    addAuthor(author)
}

fun HtmlToLatexWriter.writeTo(path: String) {
    val outDir = File("out", File(path).parent)

    if (!outDir.exists()) {
        outDir.mkdirs()
    }

    val file = File("out", path)

    val writer = FileWriter(file, false)

    Log.i("${file.path} created")

    writer.write(toString())
    writer.flush()
    writer.close()
}