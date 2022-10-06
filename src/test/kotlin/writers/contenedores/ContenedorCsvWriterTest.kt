package writers.contenedores

import extensions.toContenedorDto
import models.Contenedor
import models.TipoContenedor
import org.junit.jupiter.api.Test
import parsers.contenedores.CsvParserContenedores
import writers.FileWriter
import java.io.File

internal class ContenedorCsvWriterTest {

    @Test
    fun shouldWrite() {
        val writer = FileWriter("src/test/resources/writtenContenedores.csv", CsvParserContenedores())
        val content = sequenceOf(
            Contenedor(
                codIntSitu = "1",
                tipoContenedor = TipoContenedor.ORGANICA,
                modelo = "caca",
                descripModelo = "caca",
                cantidadContenedores = 1,
                lote = 1,
                distrito = "madrid",
                barrio = "barrio",
                tipoVia = "calle",
                nombreVia = "calle",
                numVia = 1,
                coordenadaX = 1.0f,
                coordenadaY = 1.0f,
                longitud = "1",
                latitud = "1",
                direccion = "calle 1"


            )
        )

        writer.write(content.toContenedorDto())

        val file = File("src/test/resources/writtenContenedores.csv")
        assert(file.exists())
        val lines = file.readLines()
        //assert(lines.isNotEmpty())
        //assert(lines[0] == "")
        //assert(lines[1] == "")
        //TODO ACABAR
    }
}