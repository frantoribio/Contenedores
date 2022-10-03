package readers

import exceptions.CsvException
import mappers.residuos.CsvMapperResiduos
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.FileNotFoundException
import java.time.LocalDate

internal class ResiduoCsvReaderTest {

    @Test
    fun shouldRead() {
        val reader = FileReader("src/test/resources/residuos.csv", CsvMapperResiduos())

        val data = reader.read()
        val expected = data.firstOrNull()

        assert(data.count() == 2138)
        assert(expected?.lote == 1)
        assert(expected?.residuo == "RESTO")
        assert(expected?.distrito == 1)
        assert(expected?.nombreDistrito == "Centro")
        assert(expected?.toneladas == 3477.92)
        assert(expected?.fecha == LocalDate.of(2021, 1, 1))
    }

    @Test
    fun shouldNotRead() {
        val reader = FileReader("src/test/resources/badResiduos.csv", CsvMapperResiduos())

        assertThrows<CsvException> { reader.read().toList() }
    }

    @Test
    fun shouldNotReadNonExistingFile() {
        val reader = FileReader("src/test/resources/asdresiduos.csv", CsvMapperResiduos())

        assertThrows<FileNotFoundException> { reader.read().toList() }
    }
}