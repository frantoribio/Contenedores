package core.exporting.residuos

import aliases.Residuos
import dto.ResiduoDto
import kotlinx.serialization.encodeToString
import nl.adaptivity.xmlutil.serialization.XML
import core.formats.IXmlExporter
import java.io.OutputStream

class XmlExporterResiduos(
    private val xml: XML = XML {
        autoPolymorphic = true
        indentString = "  "
    },
) : IXmlExporter<Residuos> {
    override fun export(input: Sequence<ResiduoDto>, outputStream: OutputStream) =
        xml.encodeToString(input.toList()).let { outputStream.write(it.toByteArray()) }
}