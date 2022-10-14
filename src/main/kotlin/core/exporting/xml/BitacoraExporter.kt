package core.exporting.xml

import models.Bitacora
import nl.adaptivity.xmlutil.serialization.XML
import core.formats.IXmlExporter
import java.io.OutputStream


class BitacoraExporter : IXmlExporter<Bitacora> {

    override fun export(input: Bitacora, outputStream: OutputStream) {
        XML.encodeToString(input).let { outputStream.write(it.toByteArray()) }
    }
}