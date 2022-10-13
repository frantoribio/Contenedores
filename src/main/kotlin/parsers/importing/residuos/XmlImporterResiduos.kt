package parsers.importing.residuos

import aliases.Residuos
import dto.ResiduoDto
import kotlinx.serialization.decodeFromString
import nl.adaptivity.xmlutil.serialization.XML
import parsers.formats.IXmlImporter
import java.io.InputStream

class XmlImporterResiduos(
    private val xml: XML = XML {
        autoPolymorphic = true
        indentString = "  "
    },
) : IXmlImporter<Residuos> {
    override fun import(input: InputStream): Sequence<ResiduoDto> =
        xml.decodeFromString<List<ResiduoDto>>(input.reader().readText()).asSequence()
}