package parsers

import java.io.OutputStream

interface UnParser<T> {
    val extension: String

    /**
     * Read from a sequence of objects and write to an output stream
     * *NOTE* It's the caller responsibility to close the output stream
     */
    fun unParse(input: Sequence<T>, outputStream: OutputStream)
}