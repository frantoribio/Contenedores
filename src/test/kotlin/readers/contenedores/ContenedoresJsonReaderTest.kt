package readers.contenedores

import extensions.toContenedor
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import core.importing.contenedores.JsonImporterContenedores
import readers.FileReader
import java.io.FileNotFoundException

internal class ContenedoresJsonReaderTest {

    @Test
    fun shouldRead() {
        val reader = FileReader("src/test/resources/contenedores.json", JsonImporterContenedores())

        val data = reader.read().toContenedor()
        val contenedor = data.firstOrNull()
        //TODO ACABAR
        //assert(data.count() == 1)

    }

    @Test
    fun shouldNotReadNonExistingFile() {
        val reader = FileReader("src/test/resources/asdcontenedores.json", JsonImporterContenedores())

        assertThrows<FileNotFoundException> { reader.read().toList() }
    }
}