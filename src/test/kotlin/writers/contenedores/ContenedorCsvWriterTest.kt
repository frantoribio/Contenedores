package writers.contenedores

import extensions.toContenedorDto
import extensions.toResiduoDto
import models.Contenedor
import models.Residuo
import models.TipoContenedor
import org.junit.jupiter.api.Test
import parsers.contenedores.CsvParserContenedores
import parsers.residuos.CsvParserResiduos
import writers.FileWriter
import java.io.File
import java.time.LocalDate
import java.time.Month

internal class ContenedorCsvWriterTest {

    @Test
    fun shouldWrite() {
        val writer = FileWriter("src/test/resources/written.csv", CsvParserContenedores())
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

        val file = File("src/test/resources/written.csv")
        assert(file.exists())
        val lines = file.readLines()
        //assert(lines.isNotEmpty())
        //assert(lines[0] == "")
        //assert(lines[1] == "")
    }
}