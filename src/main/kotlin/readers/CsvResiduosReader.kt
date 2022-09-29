package readers

import dto.Residuos
import java.io.File
import java.io.FileNotFoundException

class CsvResiduosReader : Reader<Residuos> {

    override fun read(path: String): Sequence<Residuos> = sequence {
        File(path)
        .apply { if (!exists()) throw FileNotFoundException() }
        .useLines {
            it.forEach { _ ->
                yield(Residuos(//TODO parse line))
            }
        }
    }

}