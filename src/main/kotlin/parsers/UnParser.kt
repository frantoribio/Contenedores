package parsers

import java.io.OutputStream

interface UnParser<in T> {
    val extension: String

    /**
     * Read from object and write to an output stream
     * *NOTE* It's the caller responsibility to close the output stream
     */
    fun unParse(input: T, outputStream: OutputStream)
}