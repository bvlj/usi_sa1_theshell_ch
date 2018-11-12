/*
 * Copyright (c) 2018. Bevilacqua Joey
 */
package ch.usi.inf.atelier.group1.html

import ch.usi.inf.atelier.group1.util.extensions.getContent
import java.io.BufferedReader
import java.io.File
import java.io.StringReader

class JekyllHtmlFile(file: File) {
    val header = HashMap<String, String>()
    val content: String

    init {
        val fileContent = file.getContent()
        val contentBuilder = StringBuilder()

        var isIteratingInHeader = false
        var line: String? = ""

        val reader = BufferedReader(StringReader(fileContent))

        while (line != null) {
            if (!isIteratingInHeader && HEADER_START == line) {
                isIteratingInHeader = true
            } else if (isIteratingInHeader) {
                isIteratingInHeader = HEADER_END != line

                if (isIteratingInHeader) {
                    val values = line.split(":")
                    if (values.size == 2) {
                        header[values[0]] = values[1].trim()
                    }
                }
            } else {
                contentBuilder.append(line).append('\n')
            }

            line = reader.readLine()
        }

        content = contentBuilder.toString()
    }

    fun isValid() = header.isNotEmpty() && content.isNotBlank()

    override fun toString() =
            "Header:\n$header\nContent:\n$content"

    companion object {
        private const val HEADER_START = "---"
        private const val HEADER_END = "---"
    }
}