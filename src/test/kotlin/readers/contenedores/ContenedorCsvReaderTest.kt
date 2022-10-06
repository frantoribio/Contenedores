package readers.contenedores

import exceptions.CsvException
import extensions.toContenedor
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import parsers.contenedores.CsvParserContenedores
import readers.FileReader
import java.io.FileNotFoundException


internal class ContenedorCsvReaderTest {

    @Test
    fun shouldRead() {
        val reader = FileReader("src/test/resources/contenedores.csv", CsvParserContenedores())
        val data = reader.read().toContenedor()
        val expected = data.firstOrNull()
        //TODO ACABAR
    }

    @Test
    fun shouldNotRead() {
        val reader = FileReader("src/test/resources/badContenedores.csv", CsvParserContenedores())
        assertThrows<CsvException> { reader.read().toList() }
    }

    @Test
    fun shouldNotReadNonExistingFile() {
        val reader = FileReader("src/test/resources/asdcontenedores.csv", CsvParserContenedores())
        assertThrows<FileNotFoundException> { reader.read().toList() }
    }
}