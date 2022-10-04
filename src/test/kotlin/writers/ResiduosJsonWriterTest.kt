package writers

import dto.ResiduoDto
import extensions.toResiduoDto
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import models.Residuo
import org.junit.jupiter.api.Test
import parsers.residuos.JsonParserResiduos
import java.io.File
import java.time.LocalDate
import java.time.Month

internal class ResiduosJsonWriterTest {

    @Test
    fun shouldWrite() {
        val content = sequenceOf(
            Residuo(
                LocalDate.of(1, Month.JANUARY.value, 1),
                2,
                "caca",
                1,
                "madrid",
                5.2

            )
        )

        val writer = FileWriter("src/test/resources/written.json", JsonParserResiduos())

        writer.write(content.toResiduoDto())

        val file = File("src/test/resources/written.json")
        assert(file.exists())
        val residuo: List<ResiduoDto> = Json.decodeFromString(file.readText())
        assert(residuo.count() == 1)
        assert(residuo[0].lote == 2)
        assert(residuo[0].residuo == "caca")
        assert(residuo[0].distrito == 1)
        assert(residuo[0].nombreDistrito == "madrid")
        assert(residuo[0].toneladas == 5.2)
    }
}