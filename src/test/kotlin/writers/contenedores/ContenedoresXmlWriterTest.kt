package writers.contenedores

import dto.ContenedorDto
import dto.ResiduoDto
import extensions.toContenedorDto
import extensions.toResiduoDto
import kotlinx.serialization.decodeFromString
import models.Contenedor
import models.TipoContenedor
import nl.adaptivity.xmlutil.serialization.XML
import org.junit.jupiter.api.Test
import parsers.contenedores.XmlParserContenedores
import writers.FileWriter
import java.io.File

internal class ContenedoresXmlWriterTest {

    @Test
    fun shouldWrite() {
        val content = sequenceOf(
            Contenedor(
                codIntSitu = "1",
                tipoContenedor = TipoContenedor.ORGANICA,
                modelo = "modelo",
                descripModelo = "descripModelo",
                cantidadContenedores = 1,
                lote = 1,
                distrito = "distrito",
                barrio = "barrio",
                tipoVia = "tipoVia",
                nombreVia = "nombreVia",
                numVia = 1,
                coordenadaX = 1f,
                coordenadaY = 1f,
                longitud = "longitud",
                latitud = "latitud",
                direccion = "direccion"
            )
        )

        val writer = FileWriter("src/test/resources/writtenContenedores.xml", XmlParserContenedores())

        writer.write(content.toContenedorDto())

        val file = File("src/test/resources/writtenContenedores.xml")
        assert(file.exists())

        val xml = XML {
            autoPolymorphic = true
        }

        val contenedor = xml.decodeFromString<List<ContenedorDto>>(file.inputStream().bufferedReader().readText())

    }
}