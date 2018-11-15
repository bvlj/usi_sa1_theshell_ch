/*
 * Copyright (c) 2018 Bevilacqua Joey.
 */
package ch.usi.inf.atelier.group1.jekyll

import org.jsoup.Jsoup
import org.jsoup.parser.Parser
import java.util.regex.Pattern

class HtmlToLatexWriter(private var content: String, private val singlePage: Boolean) {
    private val document = StringBuilder()

    /**
     * Prepare the Latex file
     * and insert the header
     */
    fun start() {
        document.clear()

        insert(HEADER, afterLine = true)
    }

    /**
     * Begin the document and insert the title
     * and table of contents automatically
     */
    fun beginDocument() {
        insert("\\begin{document}", afterLine = true)
        insert("\\maketitle", afterLine = true)
        insert("\\tableofcontents\\")
    }

    /**
     * Store the converted html text into
     * the writer content so it can be later exported
     */
    fun commit() {
        insert(content)
    }

    /**
     * End the document
     */
    fun endDocument() {
        insert("\\end{document}", afterLine = true)
    }

    /**
     * Insert a bold text in the document
     */
    fun changeBold() {
        content = content.replaceTag("<b>", "</b>", "\\textbf{", "}")
    }

    /**
     * Replace <br> with a LaTeX newline
     */
    fun changeBr() {
        content = content.replaceTag("<br>", null, "\\\\", null)
                .replaceTag("</br>", null, "\\\\", null)
    }

    /**
     * Replace <code> with LaTeX \texttt
     */
    fun changeCode() {
        content = content.replaceTag("<code>", "</code>", "\\texttt{", "}")
    }

    /**
     * Replace <i> with LaTeX \emph
     */
    fun changeItalics() {
        content = content.replaceTag("<i>", "</i>", "\\emph{", "}")
    }

    /**
     * Replace <a> with LaTeX footNote url
     */
    fun changeLink() {
        content = content.replace("{{ site.baseurl }}/", "www.theshell.ch/") // ""

        val pattern = Pattern.compile("<a href=\"(.*?)\"(.*?)>(.*?)</a>")
        val matcher = pattern.matcher(content)

        while (matcher.find()) {
            val text = matcher.group(3)
            val url = matcher.group(1)

            content = content.replace(matcher.group(0), LINK.format(url, text))
        }
    }

    /**
     * Replace <ul> and <ol> with LaTeX itemize
     */
    fun changeList() {
        content = content.replaceTag("<ul>", "</ul>", "\\begin{itemize}", "\\end{itemize}")
                .replaceTag("<ol>", "</ol>", "\\begin{itemize}", "\\end{itemize}")
    }

    /**
     * Replace <li> with LaTeX itemize item
     */
    fun changeListItem() {
        content = content.replaceTag("<li>", "</li>", "\\item ", "")
    }

    /**
     * Replace <pre> and {% highlight %} with LaTeX verbatim
     */
    fun changeMono() {
        content = content.replaceTag("<pre>", "</pre>", "\\begin{verbatim}", "\\end{verbatim}")
                .replaceTag("{% highlight bash %}", "{% endhighlight %}",
                        "\\begin{verbatim}", "\\end{verbatim}")
    }

    /**
     * Replace <p>, empty <a>, <h4> and <h5> with LaTeX plain text
     */
    fun changeParagraph() {
        content = content.replaceTag("<h4>", "</h4>", "", "")
                .replaceTag("<h5>", "</h5>", "", "")
                .replaceTag("<p>", "</p>", "\n", "")
                .replaceTag("<a>", "</a>", "", "")

        if (singlePage) {
            content = content.replaceTag("<h3>", "</h3>", "", "")
        }
    }

    /**
     * Replace <h1> with LaTeX \section
     */
    fun changeSection() {
        content = content.replaceTag("<h1>", "</h1>",
                if (singlePage) "\\subsection{" else "\\section{", "}\n")
    }

    /**
     * Replace <h2> with LaTeX \subsection
     */
    fun changeSubSection() {
        content = content.replaceTag("<h2>", "</h2>",
                if (singlePage) "\\subsubsection{" else "\\subsection{", "}\n")
    }

    /**
     * Replace <h3> with LaTeX \subsubsection
     */
    fun changeSubSubSection() {
        if (singlePage) {
            return
        }

        content = content.replaceTag("<h3>", "</h3>", "\\subsubsection{", "}\n")
    }

    /**
     * Replace <table> with LaTex tabular
     */
    fun changeTable() {
        val pattern = Pattern.compile("(?:<table>((?:.*?\\r?\\n?)*)</table>)+")
        val matcher = pattern.matcher(content)

        while (matcher.find()) {
            val document = Jsoup.parse(matcher.group(0), "", Parser.xmlParser())
            val table = document.select("table")[0]
            val rows = table.select("tr")

            val columnsDump = ArrayList<String>()
            var colCount = 0

            for (row in rows) {
                var columns = row.select("td")
                if (columns.isEmpty()) {
                    // Maybe this is an header
                    columns = row.select("th")
                }

                if (colCount == 0) {
                    colCount = columns.size
                }

                val dump = StringBuilder()
                columns.map { it.wholeText() }.forEach { dump.append("$it &") }

                // Replace the last & with a LaTeX newLine
                columnsDump.add(dump.removeSuffix("&").append("\\\\\n"))
            }

            // Build the LaTeX table
            val latexTable = StringBuilder().run {
                append("\\begin{table}[h]\n\\begin{tabular}{${"l".repeat(colCount)}}\n")
                columnsDump.forEach { append("    $it") }
                append("\\end{tabular}\n\\end{table}")
                toString()
            }

            content = content.replace(matcher.group(0), latexTable)
        }
    }

    /**
     * Replace <ul> with LaTeX \underline
     */
    fun changeUnderline() {
        content = content.replaceTag("<u>", "</u>", "\\underline{", "}")
    }

    /**
     * Add an author to the document
     *
     * @param author the text being inserted
     */
    fun addAuthor(author: String) {
        insert(if (singlePage) "\\large $author \\normalsize\\\\" else AUTHOR.format(author), true, true)
    }

    /**
     * Add a title to the document
     *
     * @param title the text being inserted
     */
    fun addTitle(title: String) {
        insert(if (singlePage) "\\section{$title}\n" else TITLE.format(title), true, true)
    }

    /**
     * Add an header for the html file in singlePage mode
     */
    fun addSinglePageInfo(title: String, author: String) {
        if (!singlePage) {
            return
        }

        insert(TITLE.format(title), true, true)
        insert(AUTHOR.format(author), false, true)
    }

    /**
     * Remove the html comments
     */
    fun stripComments() {
        content = content.replace(Regex("(?s)<!--.*?-->"), "")
    }

    /**
     * Insert content into the document
     *
     * @param string the content being inserted
     * @param preLine whether a newLine char should be inserted before the content
     * @param afterLine whether a newLine char should be inserted after the content
     */
    private fun insert(string: String, preLine: Boolean = false, afterLine: Boolean = false) {
        document.apply {
            if (preLine) {
                append("\n")
            }

            append(string)

            if (afterLine) {
                append("\n")
            }
        }
    }

    private fun String.replaceTag(oldOpen: String, oldClose: String?,
                                  newOpen: String, newClose: String?): String {
        var replaced = replace(oldOpen, newOpen)

        if (oldClose != null && newClose != null) {
            replaced = replaced.replace(oldClose, newClose)
        }

        return replaced
    }

    private fun CharSequence.append(append: String) = "${toString()}$append"

    override fun toString() = document.toString()

    companion object {
        private const val AUTHOR = "\\author{%1\$s}"
        private const val TITLE = "\\title{%1\$s}"
        private const val LINK = "\\underline{\\href{%1\$s}{%2\$s}}"

        private const val HEADER =
                "\\documentclass[hidelinks,12pt,a4paper,numbers=enddot]{scrartcl}\n\n" +
                        "\\usepackage[margin=2cm]{geometry}\n" +
                        "\\usepackage{hyperref}"
    }
}
