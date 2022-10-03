package mappers.residuos

import java.io.InputStream
import java.io.OutputStream

interface Mapper<T> {
    /**
     * Read from an input stream and return a sequence of objects
     */
    fun map(input: InputStream): Sequence<T>

    /**
     * Read from a sequence of objects and write to an output stream
     */
    fun map(input: Sequence<T>, outputStream: OutputStream)
}