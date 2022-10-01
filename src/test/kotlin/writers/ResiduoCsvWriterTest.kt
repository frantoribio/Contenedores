package writers

import models.Residuo
import org.junit.jupiter.api.Test
import java.io.File
import java.time.LocalDate
import java.time.Month

internal class ResiduoCsvWriterTest {

    @Test
    fun shouldWrite() {
        val writer = ResiduosCsvWriter("src/test/resources/written.csv")
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

        writer.write(content)

        val file = File("src/test/resources/written.csv")
        assert(file.exists())
        val lines = file.readLines()
        assert(lines.isNotEmpty())
        assert(lines[0] == "Año;Mes;Lote;Residuo;Distrito;Nombre Distrito;Toneladas")
        assert(lines[1] == "1;enero;2;caca;1;madrid;5,2")
    }
}