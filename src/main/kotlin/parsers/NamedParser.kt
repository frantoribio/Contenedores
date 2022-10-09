package parsers

import java.io.InputStream
import java.io.OutputStream

class NamedParser<T>(private val parser: Parser<T>, internal val name: String) : Parser<T> {
    override fun parse(input: InputStream): Sequence<T> = parser.parse(input)
    override fun unParse(input: Sequence<T>, outputStream: OutputStream) = parser.unParse(input, outputStream)
}