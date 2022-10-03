package readers

import mappers.residuos.XmlMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.FileNotFoundException

internal class ResiduosXmlReaderTest {

    @Test
    fun shouldRead() {
        val reader = FileReader("src/test/resources/residuos.xml", XmlMapper())

        val data = reader.read()
        val residuo = data.firstOrNull()
        
        assert(data.count() == 1)
        assert(residuo?.lote == 2)
        assert(residuo?.residuo == "caca")
        assert(residuo?.distrito == 1)
        assert(residuo?.nombreDistrito == "madrid")
        assert(residuo?.toneladas == 5.2)
    }

    @Test
    fun shouldNotReadNonExistingFile() {
        val reader = FileReader("src/test/resources/asdresiduos.xml", XmlMapper())

        assertThrows<FileNotFoundException> { reader.read().toList() }
    }
}