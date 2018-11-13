/*
 * Copyright (c) 2018 Bevilacqua Joey.
 */
package ch.usi.inf.atelier.group1.jekyll

import ch.usi.inf.atelier.group1.util.extensions.getContent
import java.io.BufferedReader
import java.io.File
import java.io.StringReader

class JekyllPage(file: File) {
    /**
     * The jekyll header variables
     */
    val header = HashMap<String, String>()

    /**
     * The jekyll html code
     */
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
                    // Import a jekyll variable
                    val values = line.split(":")
                    if (values.size == 2) {
                        header[values[0]] = values[1].trim()
                    }
                }
            } else {
                // Import html code
                contentBuilder.append(line).append('\n')
            }

            line = reader.readLine()
        }

        content = contentBuilder.toString()
    }

    /**
     * Check whether the imported file actually had valid content
     *
     * @return true if the header and the content aren't empty
     */
    fun isValid() = header.isNotEmpty() && content.isNotBlank()

    override fun toString() = "Header:\n$header\nContent:\n$content"

    companion object {
        private const val HEADER_START = "---"
        private const val HEADER_END = "---"
    }
}