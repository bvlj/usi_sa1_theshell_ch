# Bonus 2

Author: Bevilacqua Joey

Language: Kotlin

## Build

To compile this exercise `gradle` and `java-1.8` are needed.

To create a working jar executable, run

```
./gradlew shadowJar
```

The output jar will be available at this path: `build/libs/bonus2.jar`

## Execute

```
java -jar bonus2.jar [-s, --singlepage] [file1.html] [file2.html] [directory]
```

Both files and directories can be passed as parameter, even at the same.

If a directory is passed as parameter, the program will attemp to 
recursively search for Jekyll html files.

The converted LaTeX files will be created in the `out` directory with
subdirectories matching the absolute path of the files passed as argument.

Note that the program will not work with plain html files,
it requires Jekyll html files instead.

The `-s` or `--singlepage` flag creates one single output LaTeX file
instead of many. It will be output'ed in the "out" directory.

Test files is available for reference under the directory `res`.

## Supported tags

### HTML tags

* b -> textbf
* br -> \\
* code -> texttt
* i -> emph
* a ->  emph footnote url
* ul -> itemize
* ol -> itemize
* li -> item
* pre -> verbatim
* h1 -> section
* h2 -> subsection
* h3 -> subsubsection
* h4 -> plainText
* h5 -> plainText
* p -> plainText
* table -> table tabular
* u -> underline

### Extra

* comments are removed
* jekyll author -> LaTeX author
* jekyll title -> LaTeX title
* jekyll highlight -> verbatim

