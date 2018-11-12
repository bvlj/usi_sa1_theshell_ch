/*
 * Copyright (c) 2018. Bevilacqua Joey
 */
package ch.usi.inf.atelier.group1.tex

class TexDocument {
    private val document = StringBuilder()

    fun start() {
        document.clear()

        insert(HEADER, false, true)
    }

    fun beginDocument() {
        insert("\\begin{document}", false, true)
        insert("\\maketitle", false, true)
        insert("\\tableofcontents", false, true)
        insert("\\newpage", false, true)
    }

    fun endDocument() {
        insert("\\end{document}", false, true)
    }

    fun addParagraph(content: String) {
        if (content.isNotBlank()) {
            insert(content, true, true)
        }
    }

    fun addText(content: String) {
        if (content.isNotBlank()) {
            insert(content, false, true)
        }
    }

    fun addSection(content: String) {
        insert(SECTION.format(content, content.trim().toLowerCase()), false, true)
    }

    fun addSubSection(content: String) {
        insert(SUB_SECTION.format(content), false, true)
    }

    fun addSubSubSection(content: String) {
        insert(SUB_SUB_SECTION.format(content), false, true)
    }

    fun addBold(content: String) {
        insert(BOLD.format(content), false, false)
    }

    fun addItalics(content: String) {
        insert(ITALICS.format(content), false, false)
    }

    fun addCode(content: String) {
        insert(CODE.format(content), false, false)
    }

    fun addPre(content: String) {
        insert(PRE.format(content), false, true)
    }

    fun addLink(content: String, url: String) {
        insert(LINK.format(content, url), false, true)
    }

    fun beginList() {
        insert("\\begin{itemize}", false, true)
    }

    fun endList() {
        insert("\\end{itemize}", false, true)

    }

    fun addListItem(content: String) {
        insert(LIST_ITEM.format(content), false, true)
    }

    fun addBr() {
        insert("", false, false)
    }

    fun setAuthor(content: String) {
        insert(AUTHOR.format(content), false, true)
    }

    fun setTitle(content: String) {
        insert(TITLE.format(content), false, true)
    }

    private fun insert(string: String, newLine: Boolean, endLine: Boolean) {
        document.apply {
            append(string)
            if(newLine) {
                append("\\\\")
            }
            if (endLine) {
                append("\n")
            }
        }
    }

    override fun toString() = document.toString()

    companion object {
        private const val AUTHOR = "\\author{%1\$s}\n"
        private const val BOLD = "\\textbf{%1\$s}"
        private const val CODE = "\\texttt{%1\$s}"
        private const val ITALICS = "\\emph{%1\$s}"
        private const val LINK = "\\emph{%1\$s} \\footnote{\\url{%2\$s}}"
        private const val LIST_ITEM = "\\item %1\$s"
        private const val PRE = "\n\\begin{verbatim}\n%1\$s\n\\end{verbatim}"
        private const val SECTION = "\\section{%1\$s}\\label{%2\$s}"
        private const val SUB_SECTION = "\\subsection{%1\$s}"
        private const val SUB_SUB_SECTION = "\\subsubsection{%1\$s}"
        private const val TITLE = "\\title{%1\$s}\n"

        private const val HEADER =
                "\\documentclass[hidelinks,12pt,a4paper,numbers=enddot]{scrartcl}\n\n" +
                "\\usepackage[margin=2cm]{geometry}\n" +
                "\\usepackage{hyperref}\n"

    }
}