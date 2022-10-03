package mappers.residuos

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import models.Residuo
import nl.adaptivity.xmlutil.serialization.XML
import java.io.InputStream
import java.io.OutputStream

class XmlMapper(
    private val xml: XML = XML {
        autoPolymorphic = true
        indentString = "  "
    }
) : Mapper<Residuo> {
    override fun map(input: InputStream): Sequence<Residuo> =
        xml.decodeFromString<List<Residuo>>(input.reader().readText()).asSequence()

    override fun map(input: Sequence<Residuo>, outputStream: OutputStream) =
        xml.encodeToString(input.toList()).let { outputStream.write(it.toByteArray()) }
}