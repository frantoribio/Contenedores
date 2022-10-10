package parsers

import java.io.InputStream

interface Parser<T> {
    val extension: String

    /**
     * Read from an input stream and return a sequence of objects
     * *NOTE* It's the caller responsibility to close the input stream
     */
    fun parse(input: InputStream): Sequence<T>


}